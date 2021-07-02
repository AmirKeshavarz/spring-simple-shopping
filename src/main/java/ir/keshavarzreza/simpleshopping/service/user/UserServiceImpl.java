package ir.keshavarzreza.simpleshopping.service.user;

import ir.keshavarzreza.simpleshopping.domain.dto.ApiPage;
import ir.keshavarzreza.simpleshopping.domain.dto.ApiPagination;
import ir.keshavarzreza.simpleshopping.domain.dto.category.CategoryMaster;
import ir.keshavarzreza.simpleshopping.domain.dto.user.ChangePasswordRequest;
import ir.keshavarzreza.simpleshopping.domain.dto.user.UserDetail;
import ir.keshavarzreza.simpleshopping.domain.dto.user.UserRegistrationRequest;
import ir.keshavarzreza.simpleshopping.domain.entity.Category;
import ir.keshavarzreza.simpleshopping.domain.entity.security.User;
import ir.keshavarzreza.simpleshopping.exceptions.*;
import ir.keshavarzreza.simpleshopping.repository.security.UserRepository;
import ir.keshavarzreza.simpleshopping.service.PageUtil;
import ir.keshavarzreza.simpleshopping.service.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PaginationService paginationService;
	private final PageUtil pageUtil;
	private final AuthorityService authorityService;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, PaginationService paginationService, PageUtil pageUtil, AuthorityService authorityService) {
		this.userRepository = userRepository;
		this.paginationService = paginationService;
		this.pageUtil = pageUtil;
		this.authorityService = authorityService;
	}

	@Override
	public User show(String username) throws UserNotFoundException {
		return userRepository.findByUsername(username).orElseThrow(() -> {
			return new UserNotFoundException();
		});
	}

	@Override
	public User register(UserRegistrationRequest request) throws DuplicateUsernameException, AuthorityNotFoundException {
		if (userRepository.existsByUsername(request.getUsername()))
			throw new DuplicateUsernameException();
		User userToPersist = new User();
		userToPersist.setUsername(request.getUsername());
		userToPersist.setPassword((new BCryptPasswordEncoder()).encode(request.getPassword()));
		userToPersist.setAuthorities(new HashSet<>(Arrays.asList(authorityService.getByRole("user"))));
		User persistedUser = userRepository.save(userToPersist);
		return persistedUser;
	}

	@Override
	public boolean delete(String username) throws UserNotFoundException {
		User userToBeDeleted = userRepository.findByUsername(username).orElseThrow(() -> {
			return new UserNotFoundException();
		});
		userRepository.delete(userToBeDeleted);
		return true;
	}

	@Override
	public void changePassword(String username, ChangePasswordRequest request) throws NoCorrectPasswordException, UserNotFoundException {
		User user = userRepository.findByUsername(username).orElseThrow(() -> {
			return new UserNotFoundException();
		});
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String oldPassEnc = encoder.encode(request.getOldPassword());
		if (encoder.matches(user.getPassword(), oldPassEnc)) { // TODO: 2021-07-02 check if it's working or not
			user.setPassword(encoder.encode(request.getNewPassword()));
			userRepository.save(user);
		} else throw new NoCorrectPasswordException();
	}

	@Override
	public ApiPage<UserDetail> findList(String username, ApiPagination pagination) throws SortParameterHasProblomException {
		Pageable pageable = paginationService.toPageable(pagination);
		boolean filterByName = (username == null || username.isEmpty()) ? false : true;
		Page<User> all = null;

		if (!filterByName)
			all = userRepository.findAll(pageable);
		else
			all = userRepository.findAllByUsernameContaining(username, pageable);

		List<UserDetail> collect = all.stream()
				.map(user -> new UserDetail(
						user.getUsername(),
						user.isAccountNonExpired(),
						user.isAccountNonLocked(),
						user.isCredentialsNonExpired(),
						user.isEnabled()))
				.collect(Collectors.toList());

		ApiPage<UserDetail> apiPage = pageUtil.generateCorePage(all);
		apiPage.setContent(collect);
		return apiPage;
	}

	@Override
	public void enable(String username) throws UserNotFoundException, UserAlreadyEnabledException {
		User user = userRepository.findByUsername(username).orElseThrow(() -> {
			return new UserNotFoundException();
		});
		if (user.isEnabled()) throw new UserAlreadyEnabledException();
		else {
			user.setEnabled(true);
			userRepository.save(user);
		}
	}

	@Override
	public void disable(String username) throws UserNotFoundException, UserAlreadyDisabledException {
		User user = userRepository.findByUsername(username).orElseThrow(() -> {
			return new UserNotFoundException();
		});
		if (!user.isEnabled()) throw new UserAlreadyDisabledException();
		else {
			user.setEnabled(false);
			userRepository.save(user);
		}
	}
}
