package ir.keshavarzreza.simpleshopping.service.user;

import ir.keshavarzreza.simpleshopping.domain.entity.security.Authority;
import ir.keshavarzreza.simpleshopping.exceptions.AuthorityNotFoundException;
import ir.keshavarzreza.simpleshopping.repository.security.AuthorityRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {
	private final AuthorityRepository authorityRepository;

	public AuthorityService(AuthorityRepository authorityRepository) {
		this.authorityRepository = authorityRepository;
	}

	public Authority getByRole(String role) throws AuthorityNotFoundException {
		return authorityRepository.findAuthorityByRole(role).orElseThrow(()->{
			return new AuthorityNotFoundException();
		});
	}
}
