package com.excilys.cdb.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.excilys.cdb.service.UserService;
import com.excilys.cdb.webapp.auth.JwtAuthenticationEntryPoint;
import com.excilys.cdb.webapp.auth.JwtAuthenticationFilter;

@Configuration
@Order(1)
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService userService;
	private PasswordEncoder encoder;
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	JwtAuthenticationFilter jwtFilter;

	public SecurityWebConfig(UserService userService, PasswordEncoder encoder, JwtAuthenticationFilter filter,
			JwtAuthenticationEntryPoint unauthorizedHandler) {
		this.jwtFilter = filter;
		this.userService = userService;
		this.encoder = encoder;
		this.unauthorizedHandler = unauthorizedHandler;
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(encoder);
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.mvcMatcher("/").authorizeRequests()
				.mvcMatchers("/login", "/resources/**", "/css/**", "/js/**", "/img/**").permitAll()
				.anyRequest().authenticated().and().exceptionHandling().accessDeniedPage("/401")
				.and().formLogin().loginPage("/login")
				.failureUrl("/login?error")
				.defaultSuccessUrl("/computer/").permitAll()
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout")
				.invalidateHttpSession(true).permitAll();

		http.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
	}
}
