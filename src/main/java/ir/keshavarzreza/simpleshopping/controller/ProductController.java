package ir.keshavarzreza.simpleshopping.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.keshavarzreza.simpleshopping.domain.dto.ApiPage;
import ir.keshavarzreza.simpleshopping.domain.dto.ApiPagination;
import ir.keshavarzreza.simpleshopping.domain.dto.product.CreateProductRequest;
import ir.keshavarzreza.simpleshopping.domain.dto.product.ProductDetail;
import ir.keshavarzreza.simpleshopping.domain.dto.product.ProductMaster;
import ir.keshavarzreza.simpleshopping.domain.dto.product.UpdateProductRequest;
import ir.keshavarzreza.simpleshopping.domain.entity.Product;
import ir.keshavarzreza.simpleshopping.exceptions.CategoryNotFoundException;
import ir.keshavarzreza.simpleshopping.exceptions.ProductAlreadyExistsException;
import ir.keshavarzreza.simpleshopping.exceptions.ProductNotFoundException;
import ir.keshavarzreza.simpleshopping.exceptions.SortParameterHasProblomException;
import ir.keshavarzreza.simpleshopping.service.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/{productId}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
	})
	public ResponseEntity<ProductDetail> show(
			@PathVariable String productId
	) {
		ProductDetail response = new ProductDetail();
		Product product;
		try {
			product = productService.show(productId);
		} catch (ProductNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
		}
		BeanUtils.copyProperties(product, response);
		response.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{productId}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
			// 400 would be added by default by springdoc-openapi-ui
	})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity delete(
			@PathVariable String productId
	) {
		try {
			productService.delete(productId);
		} catch (ProductNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "409", description = "Duplicate", content = @Content),
			@ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
			// 400 would be added by default by springdoc-openapi-ui
	})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<ProductDetail> create(
			@RequestBody @Valid CreateProductRequest createProductRequest
	) {
		Product product;
		try {
			product = productService.create(createProductRequest);
		} catch (ProductAlreadyExistsException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate");
		} catch (CategoryNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
		}
		ProductDetail productDetail = new ProductDetail();
		BeanUtils.copyProperties(product, productDetail);
		productDetail.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
		return new ResponseEntity<>(productDetail, HttpStatus.CREATED);
	}

	@PutMapping("/{productId}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "409", description = "Duplicate", content = @Content),
			@ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
			// 400 would be added by default by springdoc-openapi-ui
	})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<ProductDetail> update(
			@PathVariable String productId,
			@Valid
			@RequestBody UpdateProductRequest request
	) {
		Product product;
		try {
			product = productService.update(productId, request);
		} catch (ProductAlreadyExistsException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Product already exists");
		} catch (ProductNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Product not found");
		} catch (CategoryNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Category not found");
		}
		ProductDetail categoryDetail = new ProductDetail();
		BeanUtils.copyProperties(product, categoryDetail);
		categoryDetail.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
		return new ResponseEntity<>(categoryDetail, HttpStatus.CREATED);
	}

	@GetMapping("")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
	})
	public ResponseEntity<ApiPage<ProductMaster>> findList(
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "categoryId", required = false) String categoryId,
			@RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
			@RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false) String sort
	) {
		ApiPagination pagination = new ApiPagination(page, size, sort);

		ApiPage<ProductMaster> response;
		try {
			response = productService.findList(name, categoryId, minPrice, maxPrice, pagination);
		} catch (SortParameterHasProblomException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sort problem");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
