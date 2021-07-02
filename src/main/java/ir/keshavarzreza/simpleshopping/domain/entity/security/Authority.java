package ir.keshavarzreza.simpleshopping.domain.entity.security;

import ir.keshavarzreza.simpleshopping.domain.entity.BaseDomainEntity;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * This Authority class is mirroring class of {@link org.springframework.security.core.authority.SimpleGrantedAuthority}
 * But we use JPA here for persisting
 */
@Entity
@Builder
public class Authority extends BaseDomainEntity {

	private String role;

	@ManyToMany(mappedBy = "authorities")
	private Set<User> users;

	public Authority() {
	}

	public Authority(String role, Set<User> users) {
		this.role = role;
		this.users = users;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
