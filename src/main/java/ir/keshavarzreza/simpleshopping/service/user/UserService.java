package ir.keshavarzreza.simpleshopping.service.user;

import ir.keshavarzreza.simpleshopping.domain.dto.user.UserDetail;
import ir.keshavarzreza.simpleshopping.domain.dto.user.UserRegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	UserDetail register(UserRegistrationRequest request);
}
