package ir.keshavarzreza.simpleshopping.domain.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChangePasswordRequest {

	@NotBlank
	@Size(min = 1, max = 100)
	private String oldPassword;

	@NotBlank
	@Size(min = 0, max = 100)
	private String newPassword;

	public ChangePasswordRequest() {
	}

	public ChangePasswordRequest(String username, String password) {
		this.oldPassword = username;
		this.newPassword = password;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
