package com.excilys.cdb.persistence;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
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

	public Optional<List<Computer>> getAll() throws DaoException {
		return criteriaBuild(Computer.class);
	}

	public Optional<List<Computer>> getAllOrderedBy(String orderBy, boolean desc) throws DaoException {
		return criteriaOrder(orderBy, desc);
	}

	public Optional<Computer> getOneById(Long id) {
		Computer criteriaComputer = criteriaGet(id).get();
		return Optional.ofNullable(criteriaComputer);
	}

	public Optional<Computer> getOneByName(String name) {
		Computer criteriaComputer = criteriaGet(name).get();
		return Optional.ofNullable(criteriaComputer);
	}

	public void create(Computer newEntity) throws DaoException {
		try (Session session = getSession();) {
			session.save(newEntity);
		} catch (HibernateException e) {
			log.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}
	
	public void updateById(Computer newEntity) throws DaoException {

		try (Session session = getSession();) {
			Transaction tx = session.beginTransaction();
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
			tx.commit();

		} catch (HibernateException e) {
			log.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}

	}

	public void deleteById(Long id) throws DaoException {
		try (Session session = getSession();) {
			Transaction tx = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaDelete<Computer> deleter = builder.createCriteriaDelete(Computer.class);
			Root<Computer> root = deleter.from(Computer.class);

			deleter.where(builder.equal(root.get("id"), id));

			session.createQuery(deleter).executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			log.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}

	public void deleteByName(String name) throws DaoException {
		try (Session session = getSession();) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaDelete<Computer> deleter = builder.createCriteriaDelete(Computer.class);
			Root<Computer> root = deleter.from(Computer.class);

			deleter.where(builder.equal(root.get("name"), name));

			session.createQuery(deleter).executeUpdate();
		} catch (HibernateException e) {
			log.error(e.getMessage());
			throw new DaoException(e.getMessage());
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
		} catch (HibernateException e) {
			log.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}

		return computerFetched;
	}

	private Optional<List<Computer>> criteriaOrder(String orderBy, boolean desc) throws DaoException {
		Optional<List<Computer>> criteriaComputers;
		try (Session session = getSession();) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Computer> query = builder.createQuery(Computer.class);
			Root<Computer> root = query.from(Computer.class);

			if (desc) {
				query.select(root).where().orderBy(builder.desc(root.<Set<String>>get(orderBy)));
			} else {
				query.select(root).where().orderBy(builder.asc(root.<Set<String>>get(orderBy)));
			}

			query = query.select(root);
			TypedQuery<Computer> typedQuery = session.createQuery(query);
			criteriaComputers = Optional.ofNullable(typedQuery.getResultList());
		}

		return criteriaComputers;
	}

	private Optional<Computer> criteriaGet(long id) {
		Optional<Computer> computer = Optional.empty();
		try (Session session = getSession();) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Computer> query = builder.createQuery(Computer.class);
			Root<Computer> root = query.from(Computer.class);

			query.select(root);
			query.where(builder.equal(root.get("id"), id));

			TypedQuery<Computer> typedQuery = session.createQuery(query);
			log.error("Computer : " + typedQuery.getSingleResult());
			computer = Optional.ofNullable(typedQuery.getSingleResult());
		}

		return computer;
	}

	private Optional<Computer> criteriaGet(String name) {
		Optional<Computer> computer = Optional.empty();
		try (Session session = getSession();) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Computer> query = builder.createQuery(Computer.class);
			Root<Computer> root = query.from(Computer.class);

			query.select(root).where(builder.equal(root.get("name"), name));

			TypedQuery<Computer> typedQuery = session.createQuery(query);
			log.error("Computer : " + typedQuery.getSingleResult());
			computer = Optional.ofNullable(typedQuery.getSingleResult());
		}

		return computer;
	}

	private <T> Optional<List<T>> criteriaSearch(Class<T> clazz, String pattern) {

		Optional<List<T>> computerFetched;
		try (Session session = getSession();) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> query = builder.createQuery(clazz);
			Root<T> root = query.from(clazz);

			query.select(root);
			query.where(builder.like(root.get("name"), "%" + pattern + "%"));

			TypedQuery<T> typedQuery = session.createQuery(query);
			computerFetched = Optional.ofNullable(typedQuery.getResultList());
		}

		return computerFetched;
	}

	public Optional<List<Computer>> searchBy(String name) {
		return criteriaSearch(Computer.class, name);
	}

}
