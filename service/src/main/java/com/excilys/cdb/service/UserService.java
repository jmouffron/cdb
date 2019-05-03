package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.cdb.binding.exception.DaoException;
import com.excilys.cdb.core.model.Privilege;
import com.excilys.cdb.core.model.Role;
import com.excilys.cdb.core.model.User;
import com.excilys.cdb.persistence.DaoRole;
import com.excilys.cdb.persistence.DaoUser;

@Service
public class UserService implements UserDetailsService {

	private DaoUser dao;
	private DaoRole roleDao;

	public UserService(DaoUser dao, DaoRole role) {
		this.dao = dao;
		this.roleDao = role;
	}

	public boolean isUsernameTaken(String username) {
		return false;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			return this.dao.getOneByUsername(username);
		} catch (DaoException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
		return getGrantedAuthorities(getPrivileges(roles));
	}
	
	private List<String> getPrivileges(Collection<Role> roles) {
		  
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }
 
    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        privileges.forEach( privilege -> {
            authorities.add(new SimpleGrantedAuthority(privilege));
        });
        
        return authorities;
    }
   
    
    @Override
    public User registerNewUserAccount(UserDto dto) throws EmailExistsException {
      
        if (emailExist(dto.getEmail())) {
            throw new EmailExistException("There is already an account with that username: " + dto.getUsername());
        }
        User user = new User();
     
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
     
        user.setRoles(Arrays.asList(roleDao.findByName("ROLE_USER")));
        return dao.create(user);
    }
}
