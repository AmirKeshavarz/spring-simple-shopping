package ir.keshavarzreza.simpleshopping.repository.security;

import ir.keshavarzreza.simpleshopping.domain.entity.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
	Optional<Authority> findAuthorityByRole(String role);
}
