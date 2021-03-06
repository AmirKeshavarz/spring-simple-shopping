package ir.keshavarzreza.simpleshopping.repository.security;

import ir.keshavarzreza.simpleshopping.domain.entity.security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);

	Page<User> findAllByUsernameContaining(String username, Pageable pageable);
}
