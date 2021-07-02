package ir.keshavarzreza.simpleshopping.domain.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRegistrationRequest {
	@NotBlank
	@Size(min = 1 , max = 50)
	private String name;
	@Size(min = 0 , max = 100)
	private String family;
	@Email
	private String email;
	@NotBlank
	private String password;

	public UserRegistrationRequest() {
	}

	public UserRegistrationRequest(String name, String description, String parentId) {
		this.name = name;
		this.family = description;
		this.email = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
