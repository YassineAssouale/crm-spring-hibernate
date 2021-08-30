package crm.hibernate.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import crm.hibernate.exception.DaoException;
import crm.hibernate.model.Customer;
import crm.hibernate.repository.MyCusotmerRepository;

@Repository
public class MyCustomerRepositoryImpl implements MyCusotmerRepository{

	Logger logger = LoggerFactory.getLogger(MyCustomerRepositoryImpl.class);
	
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Customer getCustomerByLastname(String lastname) throws DaoException {
		try {
			TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.lastname = ?1", Customer.class);
			query.setParameter(1, lastname);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public Customer getCustomerById(Integer id) throws DaoException {
		try {
			return em.find(Customer.class, id);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<Customer> getAllCustomers() throws DaoException {
		logger.info("Customers Retreiving");
		try {
			TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c ORDER BY c.id", Customer.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<Customer> getCustomersByActive(Boolean active) throws DaoException {
		logger.debug("Retreiving customers by active = {}", active);
		try {
			Query namedQuery = em.createNamedQuery("Customer.findByActive");
			namedQuery.setParameter("active", active);
			return namedQuery.getResultList();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<Customer> getCustomersWithMobile() throws DaoException {
		try {
			Query nativeQuery = em.createNativeQuery("SELECT * FROM customers WHERE mobile IS NOT NULL", Customer.class);
			return nativeQuery.getResultList();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public Customer getCustomerByIdWithCriteriaQuery(Integer id) throws DaoException {
		try {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
			
			Root<Customer> customerRoot = criteriaQuery.from(Customer.class);
			
			criteriaQuery.select(customerRoot).where(criteriaBuilder.equal(customerRoot.get("id"), id));
			
			TypedQuery<Customer> query = em.createQuery(criteriaQuery);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<Customer> getCustomersWithMobileWithCriteriaQuery() throws DaoException {
		try {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			
			CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
			
			Root<Customer> customerRoot = criteriaQuery.from(Customer.class);
			
			criteriaQuery.select(customerRoot).where(criteriaBuilder.isNotNull(customerRoot.get("mobile")));
			
			TypedQuery<Customer> query = em.createQuery(criteriaQuery);
			return query.getResultList();
		}catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public Customer createCustomer(Customer customer) throws DaoException {
		try {
			em.persist(customer);
			return customer;
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws DaoException {
		try {
			em.merge(customer);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		
	}

	@Override
	public void deleteCustomer(Customer customer) throws DaoException {
		try {
			em.remove(em.merge(customer));
		} catch (Exception e) {
			throw new DaoException(e);
		}
		
	}

	@Override
	public void deleteCustomerWithoutOrder() throws DaoException {
		try {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			
			CriteriaDelete<Customer> criteriaDelete = criteriaBuilder.createCriteriaDelete(Customer.class);
			
			Root<Customer> customerRoot = criteriaDelete.from(Customer.class);
			
			criteriaDelete.where(criteriaBuilder.isEmpty(customerRoot.get("orders")));
			
			int result = em.createQuery(criteriaDelete).executeUpdate();
			logger.debug("Number of customers deleted = {}", result);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		
	}

}
