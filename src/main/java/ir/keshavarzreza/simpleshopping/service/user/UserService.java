package ir.keshavarzreza.simpleshopping.service.user;

import ir.keshavarzreza.simpleshopping.domain.dto.ApiPage;
import ir.keshavarzreza.simpleshopping.domain.dto.ApiPagination;
import ir.keshavarzreza.simpleshopping.domain.dto.user.ChangePasswordRequest;
import ir.keshavarzreza.simpleshopping.domain.dto.user.UserDetail;
import ir.keshavarzreza.simpleshopping.domain.dto.user.UserRegistrationRequest;
import ir.keshavarzreza.simpleshopping.domain.entity.security.User;
import ir.keshavarzreza.simpleshopping.exceptions.*;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	User show(String username) throws UserNotFoundException;

	User register(UserRegistrationRequest request) throws DuplicateUsernameException, AuthorityNotFoundException;

	boolean delete(String username) throws UserNotFoundException;

	void changePassword(String username, ChangePasswordRequest request) throws NoCorrectPasswordException, UserNotFoundException;

	ApiPage<UserDetail> findList(String username, ApiPagination pagination) throws SortParameterHasProblomException;

	void enable(String username) throws UserNotFoundException, UserAlreadyEnabledException;

	void disable(String username) throws UserNotFoundException, UserAlreadyDisabledException;
}
