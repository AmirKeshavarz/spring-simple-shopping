package ir.keshavarzreza.simpleshopping.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.keshavarzreza.simpleshopping.domain.dto.ApiPage;
import ir.keshavarzreza.simpleshopping.domain.dto.ApiPagination;
import ir.keshavarzreza.simpleshopping.domain.dto.category.CategoryDetail;
import ir.keshavarzreza.simpleshopping.domain.dto.category.CategoryMaster;
import ir.keshavarzreza.simpleshopping.domain.dto.category.CreateCategoryRequest;
import ir.keshavarzreza.simpleshopping.domain.dto.category.UpdateCategoryRequest;
import ir.keshavarzreza.simpleshopping.domain.entity.Category;
import ir.keshavarzreza.simpleshopping.exceptions.CategoryAlreadyExistsException;
import ir.keshavarzreza.simpleshopping.exceptions.CategoryAlreadyInUseException;
import ir.keshavarzreza.simpleshopping.exceptions.CategoryNotFoundException;
import ir.keshavarzreza.simpleshopping.exceptions.SortParameterHasProblomException;
import ir.keshavarzreza.simpleshopping.service.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoryController {
	private final CategoryService categoryService;

	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/{categoryId}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
	})
	public ResponseEntity<CategoryDetail> show(
			@PathVariable String categoryId
	) {
		CategoryDetail response = new CategoryDetail();
		Category category = null;
		try {
			category = categoryService.show(categoryId);
		} catch (CategoryNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
		}
		BeanUtils.copyProperties(category, response);
		response.setParentId(category.getParent() != null ? category.getParent().getId() : null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{categoryId}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
			// 400 would be added by default by springdoc-openapi-ui
	})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity delete(
			@PathVariable String categoryId
	) {
		try {
			categoryService.delete(categoryId);
		} catch (CategoryNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
		} catch (CategoryAlreadyInUseException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already assigned to products");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "409", description = "Duplicate", content = @Content),
			@ApiResponse(responseCode = "404", description = "Parent category not found", content = @Content)
			// 400 would be added by default by springdoc-openapi-ui
	})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<CategoryDetail> create(
			@RequestBody @Valid CreateCategoryRequest createCategoryRequest
	) {
		Category category = null;
		try {
			category = categoryService.create(createCategoryRequest);
		} catch (CategoryAlreadyExistsException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists");
		} catch (CategoryNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent Category not found");
		}
		CategoryDetail categoryDetail = new CategoryDetail();
		BeanUtils.copyProperties(category, categoryDetail);
		categoryDetail.setParentId(category.getParent() != null ? category.getParent().getId() : null);
		return new ResponseEntity<CategoryDetail>(categoryDetail, HttpStatus.CREATED);
	}

	@PutMapping("/{categoryId}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "409", description = "Duplicate", content = @Content),
			@ApiResponse(responseCode = "404", description = "Category or Parent-category not found", content = @Content)
			// 400 would be added by default by springdoc-openapi-ui
	})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<CategoryDetail> update(
			@PathVariable String categoryId,
			@Valid
			@RequestBody UpdateCategoryRequest updateCategoryRequest
	) {
		Category category = null;
		try {
			category = categoryService.update(categoryId, updateCategoryRequest);
		} catch (CategoryAlreadyExistsException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists");
		} catch (CategoryNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Category or parent not found");
		}
		CategoryDetail categoryDetail = new CategoryDetail();
		BeanUtils.copyProperties(category, categoryDetail);
		categoryDetail.setParentId(category.getParent() != null ? category.getParent().getId() : null);
		return new ResponseEntity<CategoryDetail>(categoryDetail, HttpStatus.CREATED);
	}

	@GetMapping("")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
	})
	public ResponseEntity<ApiPage<CategoryMaster>> findList(
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "parentId", required = false) String parentId,
			@RequestParam(name="page", required = false, defaultValue = "0") int page,
			@RequestParam(name="size", required = false, defaultValue = "10") int size,
			@RequestParam(name="sort", required = false) String sort
	) {
		ApiPagination pagination = new ApiPagination(page, size, sort);
		
		ApiPage<CategoryMaster> response;
		try {
			response = categoryService.findList(name, parentId, pagination);
		} catch (SortParameterHasProblomException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sort problem");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
