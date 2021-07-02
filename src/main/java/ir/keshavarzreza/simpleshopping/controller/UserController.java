package ir.keshavarzreza.simpleshopping.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.keshavarzreza.simpleshopping.domain.dto.ApiPage;
import ir.keshavarzreza.simpleshopping.domain.dto.ApiPagination;
import ir.keshavarzreza.simpleshopping.domain.dto.user.ChangePasswordRequest;
import ir.keshavarzreza.simpleshopping.domain.dto.user.UserDetail;
import ir.keshavarzreza.simpleshopping.domain.dto.user.UserRegistrationRequest;
import ir.keshavarzreza.simpleshopping.domain.entity.security.User;
import ir.keshavarzreza.simpleshopping.exceptions.*;
import ir.keshavarzreza.simpleshopping.service.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{username}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content)
	})
	public ResponseEntity<UserDetail> show(
			@PathVariable String username
	) {
		UserDetail response = new UserDetail();
		User user = null;
		try {
			user = userService.show(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
		BeanUtils.copyProperties(user, response);
		return new ResponseEntity<UserDetail>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{username}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content)
			// 400 would be added by default by springdoc-openapi-ui
	})
	public ResponseEntity delete(
			@PathVariable String username
	) {
		try {
			userService.delete(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "409", description = "Duplicate", content = @Content)
			// 400 would be added by default by springdoc-openapi-ui
	})
	public ResponseEntity<UserDetail> register(
			@RequestBody @Valid UserRegistrationRequest request
	) {
		User user = null;
		try {
			user = userService.register(request);
		} catch (AuthorityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Internal server error");
		} catch (DuplicateUsernameException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Duplicate username");
		}
		UserDetail userDetail = new UserDetail();
		BeanUtils.copyProperties(user, userDetail);
		return new ResponseEntity<UserDetail>(userDetail, HttpStatus.CREATED);
	}

	@PutMapping("/{username}/password")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
			// 400 would be added by default by springdoc-openapi-ui
	})
	public ResponseEntity changePassword(
			@PathVariable String username,
			@Valid
			@RequestBody ChangePasswordRequest changePasswordRequest
	) {
		try {
			userService.changePassword(username, changePasswordRequest);
		} catch (NoCorrectPasswordException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Old_Password not corrected");
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Username not found");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
	})
	public ResponseEntity<ApiPage<UserDetail>> findList(
			@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "sort", required = false) String sort
	) {
		ApiPagination pagination = new ApiPagination(page, size, sort);

		ApiPage<UserDetail> response;
		try {
			response = userService.findList(username, pagination);
		} catch (SortParameterHasProblomException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sort problem");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{username}/enabled")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
	})
	public ResponseEntity enable(
			@PathVariable(name = "username", required = true) String username
	) {
		try {
			userService.enable(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found");
		} catch (UserAlreadyEnabledException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "User already enabled");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{username}/enabled")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Success"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
	})
	public ResponseEntity disable(
			@PathVariable(name = "username", required = true) String username
	) {
		try {
			userService.disable(username);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found");
		} catch (UserAlreadyDisabledException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "User already disabled");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
