package ir.keshavarzreza.simpleshopping.domain.dto.product;

import java.math.BigDecimal;

public class ProductMaster {
	private String id;
	private String name;
	private String categoryId;
	private BigDecimal price;

	public ProductMaster() {
	}

	public ProductMaster(String id, String name, String categoryId, BigDecimal price) {
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
