package ir.keshavarzreza.simpleshopping.domain.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product extends BaseDomainEntity {
	public Product() {
	}

	public Product(String name, String description, BigDecimal price, Category category) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
	}

	private String name;

	@Column(length = 500)
	private String description;
	private BigDecimal price;

	@ManyToOne(fetch = FetchType.LAZY)
	private Category category;

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
