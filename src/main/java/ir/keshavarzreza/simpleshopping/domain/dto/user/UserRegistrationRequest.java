package ir.keshavarzreza.simpleshopping.domain.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRegistrationRequest {

	@NotBlank
	@Size(min = 1, max = 50)
	private String username;

	@NotBlank
	@Size(min = 0, max = 100)
	private String password;

	public UserRegistrationRequest() {
	}

	public UserRegistrationRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
