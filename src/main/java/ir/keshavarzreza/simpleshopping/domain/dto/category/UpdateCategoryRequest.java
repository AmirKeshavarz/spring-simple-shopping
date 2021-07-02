package ir.keshavarzreza.simpleshopping.domain.dto.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdateCategoryRequest {
	@Size(min = 1 , max = 50)
	private String name;
	@Size(min = 0 , max = 500)
	private String description;
	private String parentId;

	public UpdateCategoryRequest() {
	}

	public UpdateCategoryRequest(String name, String description, String parentId) {
		this.name = name;
		this.description = description;
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
