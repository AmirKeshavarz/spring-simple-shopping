package ir.keshavarzreza.simpleshopping.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Profile({"!h2"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		Arrays.asList(HttpMethod.GET, HttpMethod.POST);
		http
				.authorizeRequests((request) -> {
					request.antMatchers("/swagger-ui.html", "/swagger-ui/**", "/api-docs/**").permitAll();
					request.antMatchers(HttpMethod.GET, "/categories", "/categories/**").permitAll();
					request.antMatchers(HttpMethod.GET, "/products", "/products/**").permitAll();
					request.antMatchers(HttpMethod.POST, "/users").permitAll();
				});
		http.authorizeRequests((requests) -> {
			((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) requests.anyRequest()).authenticated();
		});
		http.formLogin();
		http.httpBasic();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
