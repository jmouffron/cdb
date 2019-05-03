package com.excilys.cdb.persistence;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.binding.exception.DaoException;
import com.excilys.cdb.core.model.Role;
import com.excilys.cdb.core.model.User;

@Repository
public class DaoRole {
	private static final Logger log = LoggerFactory.getLogger(DaoRole.class);
	private SessionFactory sessionFactory;
	
	public DaoRole(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return sessionFactory.openSession();
	}
	
	public Role findByName(String name) throws DaoException {
		try(Session session = getSession();) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Role> query = builder.createQuery(Role.class);
			Root<Role> root = query.from(Role.class);
			query.select(root);
			query.where(builder.equal(root.get("name"), name));
			TypedQuery<Role> typedQuery = session.createQuery(query);
			return typedQuery.getSingleResult();
		} catch ( HibernateException e) {
			log.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}

	
}
