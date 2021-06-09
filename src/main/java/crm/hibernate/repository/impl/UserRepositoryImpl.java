package crm.hibernate.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import crm.hibernate.exception.DaoException;
import crm.hibernate.model.User;
import crm.hibernate.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {
	
	Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getById(Integer id) throws DaoException {
		logger.debug("Paramètre id = {}", id);
		try {
            return em.find(User.class, id);
        } catch (Exception e) {
            throw new DaoException(e);
        }
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getByUsername(String username) throws DaoException {
		logger.debug("Paramètre username = {}", username);
		try {
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = ?1",
					User.class);
			query.setParameter(1, username);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getByUsernameAndPassword(String username, String password) throws DaoException {
		logger.debug("Paramètres username = {}, password = {}", username, password);
		try {
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
			query.setParameter("username", username);
			query.setParameter("password", password);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> getAll() throws DaoException {
		try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u ORDER BY u.id", User.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createUser(User user) throws DaoException {
		logger.debug("User à créer = {}", user);
		try {
            em.persist(user);
        } catch (Exception e) {
            throw new DaoException(e);
        }
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateUser(User user) throws DaoException {
		logger.debug("User modifié = {}", user);
		try {
            em.merge(user);
        } catch (Exception e) {
            throw new DaoException(e);
        }
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteUser(User user) throws DaoException {
		logger.debug("User à supprimer = {}", user);
		try {
            em.remove(em.merge(user));
        } catch (Exception e) {
            throw new DaoException(e);
        }
	}

}
