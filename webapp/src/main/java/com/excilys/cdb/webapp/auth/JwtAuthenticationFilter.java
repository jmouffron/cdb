package com.excilys.cdb.webapp.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.excilys.cdb.core.model.User;
import com.excilys.cdb.service.AuthService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private AuthService authService;

	public JwtAuthenticationFilter(AuthService jwtService) {
		this.authService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		getJwtFromRequest(request);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			User user = (User)auth.getPrincipal();
			if (user != null) {
				if (user.getToken().isEmpty() || !authService.validateToken(user.getToken())) {
					SecurityContextHolder.getContext().setAuthentication(null);
				}
			}
		}
		filterChain.doFilter(request, response);
	}
	private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}

