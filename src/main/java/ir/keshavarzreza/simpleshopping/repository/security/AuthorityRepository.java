package ir.keshavarzreza.simpleshopping.repository.security;

import ir.keshavarzreza.simpleshopping.domain.entity.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
