package crm.hibernate.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import crm.hibernate.exception.DaoException;
import crm.hibernate.model.Order;
import crm.hibernate.repository.OrderRepository;



@Repository
public class OrderRepositoryImpl implements OrderRepository {

	Logger logger = LoggerFactory.getLogger(OrderRepositoryImpl.class);

	@PersistenceContext
	private EntityManager em;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Order getById(Integer id) throws DaoException {
		try {
			return em.find(Order.class, id);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Order> getAllOrders() throws DaoException {
		try {
			TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o ORDER BY o.id", Order.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Utilisation d'une NamedQuery (cf. classe Order)
	 */
	public List<Order> getOrdersByTypeAndStatus(String type, String status) throws DaoException {
		try {
			Query namedQuery = em.createNamedQuery("Order.findByTypeAndStatus");
			namedQuery.setParameter("type", type);
			namedQuery.setParameter("status", status);
			return (List<Order>) namedQuery.getResultList();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Utilisation d'une NativeQuery
	 */
	public List<Order> getOrdersForNumberOfDays(Double numberOfDays) throws DaoException {
		try {
			Query nativeQuery = em.createNativeQuery("SELECT * FROM orders WHERE number_of_days > :numberOfDays",
					Order.class);
			nativeQuery.setParameter("numberOfDays", numberOfDays);
			return (List<Order>) nativeQuery.getResultList();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Requête avec Criteria
	 */
	@Override
	public List<Order> getOrdersByCustomer(Integer customerId) throws DaoException {
		try {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);

			Root<Order> orderRoot = criteriaQuery.from(Order.class);

			criteriaQuery.select(orderRoot).where(criteriaBuilder.equal(orderRoot.get("customer"), customerId));

			TypedQuery<Order> query = em.createQuery(criteriaQuery);
			return query.getResultList();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createOrder(Order order) throws DaoException {
		try {
			em.persist(order);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateOrder(Order order) throws DaoException {
		try {
			em.merge(order);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * With criteria update
	 */
	public void updateOrderStatus(String newStatus, String oldStatus, String type) throws DaoException {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaUpdate<Order> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Order.class);
		Root<Order> root = criteriaUpdate.from(Order.class);
		criteriaUpdate.set("status", newStatus);

		Predicate oldStatusPredicate = criteriaBuilder.equal(root.get("status"), oldStatus);
		Predicate typePredicate = criteriaBuilder.equal(root.get("type"), type);

		criteriaUpdate.where(criteriaBuilder.and(oldStatusPredicate, typePredicate));
		em.createQuery(criteriaUpdate).executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteOrder(Order order) throws DaoException {
		try {
			em.remove(em.merge(order));
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

}
