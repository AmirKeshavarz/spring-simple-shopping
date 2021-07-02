package ir.keshavarzreza.simpleshopping.domain.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Category extends BaseDomainEntity {
	public Category() {
	}

	public Category(String name, String description, Category parent) {
		this.name = name;
		this.description = description;
		this.parent = parent;
	}

	private String name;
	private String description;
	@OneToOne
	private Category parent;

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

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}
}
