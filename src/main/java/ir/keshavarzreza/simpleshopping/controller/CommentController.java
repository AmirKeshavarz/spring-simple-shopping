package ir.keshavarzreza.simpleshopping.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.keshavarzreza.simpleshopping.domain.dto.ApiPage;
import ir.keshavarzreza.simpleshopping.domain.dto.ApiPagination;
import ir.keshavarzreza.simpleshopping.domain.dto.comment.CommentDetail;
import ir.keshavarzreza.simpleshopping.domain.dto.comment.CreateCommentRequest;
import ir.keshavarzreza.simpleshopping.domain.dto.product.CreateProductRequest;
import ir.keshavarzreza.simpleshopping.domain.dto.product.ProductDetail;
import ir.keshavarzreza.simpleshopping.domain.dto.product.ProductMaster;
import ir.keshavarzreza.simpleshopping.domain.dto.product.UpdateProductRequest;
import ir.keshavarzreza.simpleshopping.domain.entity.Comment;
import ir.keshavarzreza.simpleshopping.domain.entity.Product;
import ir.keshavarzreza.simpleshopping.exceptions.*;
import ir.keshavarzreza.simpleshopping.service.comment.CommentService;
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
import java.security.Principal;

@RestController
@RequestMapping("/comments")
@Slf4j
public class CommentController {

	private final CommentService commentService;

	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@DeleteMapping("/{commentId}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
			// 400 would be added by default by springdoc-openapi-ui
	})
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity delete(
			@PathVariable String commentId
	) {
		try {
			commentService.delete(commentId);
		} catch (CommentNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
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
	public ResponseEntity<CommentDetail> create(
			@RequestBody @Valid CreateCommentRequest createCommentRequest,
			Principal principal
	) {
		Comment comment;
		try {
			comment = commentService.create(principal.getName(), createCommentRequest);
		} catch (ProductNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
		}
		CommentDetail commentDetail = new CommentDetail();
		BeanUtils.copyProperties(comment, commentDetail);
		return new ResponseEntity<>(commentDetail, HttpStatus.CREATED);
	}

	@PutMapping("/{commentId}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
			// 400 would be added by default by springdoc-openapi-ui
	})
	public ResponseEntity<String> update(
			@PathVariable String commentId,
			@Valid
			@RequestBody UpdateProductRequest request,
			Principal principal
	) {
		// TODO: 2021-07-04 Implement it..
		return new ResponseEntity<>(
				"Sorry, I got tired. And hope I can complete it in another time",
				HttpStatus.OK);
	}

	@GetMapping("")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
	})
	public ResponseEntity<String> findList(
			@RequestParam(name = "productId", required = false) String name,
			@RequestParam(name = "username", required = false) String categoryId,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false) String sort
	) {
		// TODO: 2021-07-04 Implement it..
		return new ResponseEntity<>(
				"Sorry, I got tired. And hope I can complete it in another time.",
				HttpStatus.OK);
	}

}
