package com.excilys.cdb.persistence;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.binding.exception.DaoException;
import com.excilys.cdb.core.model.Computer;

@Repository
public class DaoComputer {

	private static final Logger log = LoggerFactory.getLogger(DaoComputer.class);
	private SessionFactory sessionFactory;

	public DaoComputer(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return sessionFactory.openSession();
	}

	@Transactional(readOnly = true)
	public Optional<List<Computer>> getAll() throws DaoException {
		return criteriaBuild(Computer.class);
	}

	@Transactional(readOnly = true)
	public Optional<List<Computer>> getAllOrderedBy(String orderBy, boolean desc) throws DaoException {
		return criteriaOrder(Computer.class, orderBy, desc);
	}

	@Transactional(readOnly = true)
	public Optional<Computer> getOneById(Long id) {
		Computer criteriaComputer = criteriaGet(Computer.class, id);
		return Optional.ofNullable(criteriaComputer);
	}

	@Transactional(readOnly = true)
	public Optional<Computer> getOneByName(String name) {
		Computer criteriaComputer = criteriaGet(Computer.class, name);
		return Optional.ofNullable(criteriaComputer);
	}

	@Transactional
	public void create(Computer newEntity) throws DaoException {
		try (Session session = getSession();) {
			session.persist(newEntity);
		}
	}

	@Transactional
	public void updateById(Computer newEntity) throws DaoException {
		try (Session session = getSession();) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaUpdate<Computer> updater = builder.createCriteriaUpdate(Computer.class);
			Root<Computer> root = updater.from(Computer.class);

			updater.set("name", newEntity.getName());

			if (newEntity.getIntroduced() != null) {
				updater.set("introduced", newEntity.getIntroduced());
			}
			if (newEntity.getDiscontinued() != null) {
				updater.set("discontinued", newEntity.getDiscontinued());
			}
			if (newEntity.getCompany() != null) {
				updater.set("company", newEntity.getCompany());
			}

			updater.where(builder.equal(root.get("id"), newEntity.getId()));

			session.createQuery(updater).executeUpdate();

		}
	}

	@Transactional
	public void deleteById(Long id) throws DaoException {
		try (Session session = getSession();) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaDelete<Computer> deleter = builder.createCriteriaDelete(Computer.class);
			Root<Computer> root = deleter.from(Computer.class);

			deleter.where(builder.equal(root.get("id"), id));

			session.createQuery(deleter).executeUpdate();
		}
	}

	@Transactional
	public void deleteByName(String name) throws DaoException {
		try (Session session = getSession();) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaDelete<Computer> deleter = builder.createCriteriaDelete(Computer.class);
			Root<Computer> root = deleter.from(Computer.class);

			deleter.where(builder.equal(root.get("name"), name));

			session.createQuery(deleter).executeUpdate();
		}
	}

	private <T> Optional<List<T>> criteriaBuild(Class<T> clazz, Predicate... predicates) throws DaoException {
		Optional<List<T>> computerFetched = Optional.empty();
		try (Session session = getSession();) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> query = builder.createQuery(clazz);
			Root<T> root = query.from(clazz);
			query.select(root);
			Arrays.asList(predicates).forEach(predicate -> query.select(root).where(predicate));
			TypedQuery<T> typedQuery = session.createQuery(query);
			computerFetched = Optional.ofNullable(typedQuery.getResultList());
		}

		return computerFetched;
	}

	private <T> Optional<List<T>> criteriaOrder(Class<T> clazz, String orderBy, boolean desc) throws DaoException {
		Optional<List<T>> criteriaComputers;
		try (Session session = getSession();) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> query = builder.createQuery(clazz);
			Root<T> root = query.from(clazz);
			query.select(root);
			if( desc ) {
				query.orderBy(builder.desc(root.get(orderBy)));
			} else {
				query.orderBy(builder.asc(root.get(orderBy)));
			}
			TypedQuery<T> typedQuery = session.createQuery(query);
			criteriaComputers = Optional.ofNullable(typedQuery.getResultList());
		}

		return criteriaComputers;
	}

	private <T> T criteriaGet(Class<T> clazz, long id) {
		Optional<TypedQuery<T>> typedQuery = Optional.empty();

		try (Session session = getSession();) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> query = builder.createQuery(clazz);
			Root<T> root = query.from(clazz);

			query.select(root).where(builder.equal(root.get("id"), id));

			typedQuery = Optional.ofNullable(session.createQuery(query));

		}

		return typedQuery.get().getSingleResult();
	}

	private <T> T criteriaGet(Class<T> clazz, String name) {
		Optional<TypedQuery<T>> typedQuery = Optional.empty();

		try (Session session = getSession();) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> query = builder.createQuery(clazz);
			Root<T> root = query.from(clazz);

			query.select(root).where(builder.equal(root.get("name"), name));

			typedQuery = Optional.ofNullable(session.createQuery(query));

		}

		return typedQuery.get().getSingleResult();
	}

	private <T> Optional<List<T>> criteriaSearch(Class<T> clazz, String pattern) {

		Optional<List<T>> computerFetched;
		try (Session session = getSession();) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> query = builder.createQuery(clazz);
			Root<T> root = query.from(clazz);

			String patternBuilt = new StringBuilder(pattern).insert(0, "%").append("%").toString();
			query.select(root).where(builder.like(root.get("name"), patternBuilt));

			TypedQuery<T> typedQuery = session.createQuery(query);
			computerFetched = Optional.ofNullable(typedQuery.getResultList());
		}

		return computerFetched;
	}

	public Optional<List<Computer>> searchBy(String name) {
		return criteriaSearch(Computer.class,name);
	}

}
