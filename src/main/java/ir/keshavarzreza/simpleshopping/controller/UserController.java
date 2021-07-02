package ir.keshavarzreza.simpleshopping.controller;

import ir.keshavarzreza.simpleshopping.domain.dto.user.UserDetail;
import ir.keshavarzreza.simpleshopping.domain.dto.user.UserRegistrationRequest;
import ir.keshavarzreza.simpleshopping.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("")
	public ResponseEntity<String> register(
			@Valid UserRegistrationRequest request
			){
		UserDetail response = userService.register(request);
		return null;
	}
}
