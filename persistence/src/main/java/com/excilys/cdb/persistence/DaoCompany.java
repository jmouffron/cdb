package com.excilys.cdb.persistence;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.binding.exception.DaoException;
import com.excilys.cdb.core.model.Company;

/**
 * Dao for company related entities fetching
 * 
 * @author excilys
 *
 */

@Repository
public class DaoCompany {

	private final Logger log = LoggerFactory.getLogger(DaoCompany.class);

	private SessionFactory sessionFactory;

	public DaoCompany(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return this.sessionFactory.openSession();
	}

	@Transactional(readOnly = true)
	public Optional<List<Company>> getAll() throws DaoException {
		Optional<List<Company>> companies;
		try (Session session = getSession();) {
			Transaction tx = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Company> query = builder.createQuery(Company.class);
			Root<Company> root = query.from(Company.class);
			query.select(root);
			TypedQuery<Company> typedQuery = session.createQuery(query);
			companies = Optional.ofNullable(typedQuery.getResultList());
			tx.commit();

		} catch (HibernateException e) {
			log.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}

		return companies;
	}

	@Transactional(readOnly = true)
	public Optional<List<Company>> getAllOrderedBy(String orderBy, boolean desc) throws DaoException {
		Optional<List<Company>> companies;
		try (Session session = getSession();) {
			Transaction tx = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Company> query = builder.createQuery(Company.class);
			Root<Company> root = query.from(Company.class);
			if (desc) {
				query.select(root).where().orderBy(builder.desc(root.<Set<String>>get(orderBy)));
			} else {
				query.select(root).where().orderBy(builder.asc(root.<Set<String>>get(orderBy)));
			}
			TypedQuery<Company> typedQuery = session.createQuery(query);
			companies = Optional.ofNullable(typedQuery.getResultList());
			tx.commit();

		} catch (HibernateException e) {
			log.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}

		return companies;
	}

	@Transactional(readOnly = true)
	public Optional<Company> getOneById(Long id) throws DaoException {
		Optional<Company> company;
		try (Session session = getSession();) {
			Transaction tx = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Company> query = builder.createQuery(Company.class);
			Root<Company> root = query.from(Company.class);

			query.select(root).where(builder.equal(root.get("id"), id));

			TypedQuery<Company> typedQuery = session.createQuery(query);
			company = Optional.ofNullable(typedQuery.getSingleResult());
			tx.commit();

		} catch (HibernateException e) {
			log.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}

		return company;
	}

	@Transactional(readOnly = true)
	public Optional<Company> getOneByName(String name) throws DaoException {
		Optional<Company> company;
		try (Session session = getSession();) {
			Transaction tx = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Company> query = builder.createQuery(Company.class);
			Root<Company> root = query.from(Company.class);

			query.select(root).where(builder.equal(root.get("name"), name));

			TypedQuery<Company> typedQuery = session.createQuery(query);
			company = Optional.ofNullable(typedQuery.getSingleResult());
			tx.commit();

		} catch (HibernateException e) {
			log.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}

		return company;
	}

	@Transactional
	public void create(Company newEntity) throws DaoException {
		try (Session session = getSession();) {
			session.save(newEntity);
		} catch (HibernateException e) {
			log.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}

	@Transactional
	public void updateById(Company newEntity) throws DaoException {
		try (Session session = getSession();) {
			Transaction tx = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaUpdate<Company> updater = builder.createCriteriaUpdate(Company.class);
			Root<Company> root = updater.from(Company.class);

			updater.set("name", newEntity.getName());

			updater.where(builder.equal(root.get("id"), newEntity.getId()));

			session.createQuery(updater).executeUpdate();
			tx.commit();

		} catch (HibernateException e) {
			log.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}

	@Transactional
	public void deleteById(Long id) throws DaoException {
		try (Session session = getSession();) {
			Transaction tx = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaDelete<Company> deleter = builder.createCriteriaDelete(Company.class);
			Root<Company> root = deleter.from(Company.class);

			deleter.where(builder.equal(root.get("id"), id));

			session.createQuery(deleter).executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			log.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}

	@Transactional
	public void deleteByName(String name) throws DaoException {
		try (Session session = getSession();) {
			Transaction tx = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaDelete<Company> deleter = builder.createCriteriaDelete(Company.class);
			Root<Company> root = deleter.from(Company.class);

			deleter.where(builder.equal(root.get("name"), name));

			session.createQuery(deleter).executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			log.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}

}
