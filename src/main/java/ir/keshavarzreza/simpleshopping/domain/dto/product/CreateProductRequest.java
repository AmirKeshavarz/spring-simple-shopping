package ir.keshavarzreza.simpleshopping.domain.dto.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateProductRequest {
	@NotBlank
	@Size(min = 1 , max = 50)
	private String name;
	@Size(min = 0 , max = 500)
	private String description;
	private String categoryId;

	public CreateProductRequest() {
	}

	public CreateProductRequest(String name, String description, String parentId) {
		this.name = name;
		this.description = description;
		this.categoryId = parentId;
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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}
