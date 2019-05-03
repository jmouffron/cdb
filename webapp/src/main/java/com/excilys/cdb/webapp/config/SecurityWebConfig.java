package com.excilys.cdb.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.excilys.cdb.service.UserService;
import com.excilys.cdb.webapp.auth.JwtAuthenticationEntryPoint;
import com.excilys.cdb.webapp.auth.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {

	private UserService userService;
	private PasswordEncoder encoder;
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(encoder).build();
	}

	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors()
			.and()
				.csrf()
			.and()
				.sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
				.mvcMatcher("/")
					.authorizeRequests()
					.mvcMatchers("/login", "/resources/**", "/css/**", "/js/**", "/img/**")
					.permitAll()
					.mvcMatchers("/computer/**")
					.access("hasRole('ADMIN') and hasRole('USER')")
				.anyRequest()
                .authenticated()
                .mvcMatchers("/computer/add").hasAnyRole("ADMIN", "USER")
                .mvcMatchers("/computer/edit", "/computer/delete").hasAnyRole("ADMIN")
			.and()
				.formLogin()
				.loginPage("/login").permitAll()
			.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.invalidateHttpSession(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll();
		
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
