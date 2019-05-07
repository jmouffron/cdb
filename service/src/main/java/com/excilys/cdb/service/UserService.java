package com.excilys.cdb.service;

import java.util.Arrays;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

	private DaoUser dao;

	public UserService(DaoUser dao) {
		this.dao = dao;
	}

	public boolean isUsernameTaken(String username) {
		return false;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			return this.dao.getOneByUsername(username);
		} catch( DaoException e ) {
			throw new UsernameNotFoundException(e.getMessage());
		}
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
    
    public boolean emailExist(String email) {
    	Optional<UserDetails> foundUser = Optional.empty();
    	try {
			foundUser = Optional.ofNullable(dao.getOneByUsername(email));
		} catch (DaoException e) {
			foundUser = Optional.empty();
		}
    	return foundUser.isPresent() ? true : false;
    }

}
