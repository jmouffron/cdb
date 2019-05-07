package com.excilys.cdb.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.excilys.cdb.core.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
@PropertySource("classpath:jwt.properties")
public class AuthService {

	@Bean
	public Properties jwtProps() throws IOException {
		Properties prop = new Properties();
		InputStream propsFile = ClassLoader
									.getSystemClassLoader()
									.getResourceAsStream("jwt.properties");
		prop.load(propsFile);
		return prop;
	}
	
	public static final String SECRET_KEY = "secret.key";
	public static final String EXPIRED_TIME = "expired.time";
	
	@Value("${app.jwt.secret}")
	private String secret;
	
	@Value("${app.jwt.expire}")
	private int expireMs;

	private Logger logger = LoggerFactory.getLogger(getClass().getName());

	public String generateToken(Authentication auth) {
		User user = (User) auth.getPrincipal();

		Date now = new Date();
		Date expiryDate = Date.from(Instant.now().plus(expireMs, ChronoUnit.MICROS));

		return Jwts.builder().setSubject(Long.toString(user.getId())).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parse(token);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		
		return false;
	}
}
