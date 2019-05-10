package com.excilys.cdb.service;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.binding.dto.UserDTO;
import com.excilys.cdb.binding.exception.DaoException;
import com.excilys.cdb.binding.exception.EmailExistException;
import com.excilys.cdb.core.model.Role;
import com.excilys.cdb.core.model.User;
import com.excilys.cdb.persistence.DaoUser;

@Service
@Transactional
public class UserService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private DaoUser dao;

	public UserService(DaoUser dao) {
		this.dao = dao;
	}

	public boolean isUsernameTaken(String username) throws DaoException {
		return Objects.isNull(this.dao.getOneByUsername(username));
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		try {
			return this.dao.getOneByUsername(username);
		} catch (DaoException e) {
			logger.error(e.getMessage());
		}
		return null;
	}
   
    public User create(UserDTO dto) throws EmailExistException, DaoException {
    	
        if (emailExist(dto.getEmail())) {
            throw new EmailExistException("There is already an account with that username: " + dto.getUsername());
        }
        
        User user = new User.UserBuilder()
        		.setUsername(dto.getUsername())
		        .setPassword(dto.getPassword())
		        .setEmail(dto.getEmail())
		        .setRoles(Arrays.asList(new Role("ROLE_USER")))
		        .build();
        
        return dao.create(user);
    }
    
    public boolean emailExist(String email) throws DaoException {
    	Optional<UserDetails> foundUser = Optional.ofNullable(dao.getOneByUsername(email));
    	return foundUser.isPresent() ? Boolean.TRUE: Boolean.FALSE;
    }
    
    public Byte[] getHS256SecretBytes() {
    	return new Byte[] {};
    }

}
