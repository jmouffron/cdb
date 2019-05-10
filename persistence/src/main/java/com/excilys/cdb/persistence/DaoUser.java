package com.excilys.cdb.persistence;

import java.util.Optional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.binding.exception.DaoException;
import com.excilys.cdb.core.model.Role;
import com.excilys.cdb.core.model.User;

@Repository
public class DaoUser {
	
	private static final Logger log = LoggerFactory.getLogger(DaoUser.class);
	private SessionFactory sessionFactory;
	private BCryptPasswordEncoder encoder;
	
	public DaoUser(SessionFactory sesssionFactory, BCryptPasswordEncoder encoder) {
		this.sessionFactory = sesssionFactory;
		this.encoder = encoder;
	}
	
	private Session getSession() {
		return sessionFactory.openSession();
	}
	
	public UserDetails getOneByUsername(String username) throws DaoException {
		Optional<User> user ;
		try (Session session = getSession();) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> query = builder.createQuery(User.class);
			Root<User> root = query.from(User.class);
			query.select(root);
			Predicate preds = builder.or(builder.equal(root.get("username"), username), builder.equal(root.get("email"), username) );
			query.where(preds);
			
			TypedQuery<User> typedQuery = session.createQuery(query);
			user = Optional.ofNullable(typedQuery.getSingleResult());
		} catch (HibernateException e) {
			log.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		
		UserBuilder builder;
	    if (user.isPresent()) {
	      String [] role = (String[]) user.get().getRoles().stream().map(Role::getName).toArray();
	      builder = org.springframework.security.core.userdetails.User.withUsername(username);
	      builder.password(encoder.encode(user.get().getPassword()));
	      builder.roles(role);
	    } else {
	      throw new UsernameNotFoundException("User not found.");
	    }

	    return builder.build();
	}
	
	public User create(User user) throws DaoException {
		try(Session session = getSession();){
			String result = encoder.encode(user.getPassword());
			user.setPassword(result);
			return (User) session.save(user);			
		} catch( HibernateException e) {
			log.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}
	
}
