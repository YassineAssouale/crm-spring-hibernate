package crm.hibernate.repository;

import java.util.List;

import crm.hibernate.exception.DaoException;
import crm.hibernate.model.Customer;

public interface MyCusotmerRepository {

	/**
	 * Get a customer by his lastname
	 * @param lastname
	 * @return the customer
	 * @throws DaoException
	 */
	Customer getCustomerByLastname(String lastname) throws DaoException;
	
	/**
	 * Get a customer by his id
	 * @param id
	 * @return the customer
	 * @throws DaoException
	 */
	Customer getCustomerById(Integer id) throws DaoException;
	
	/**
	 * Get all customers
	 * @return a list fo customers
	 * @throws DaoException
	 */
	List<Customer> getAllCustomers() throws DaoException;
	
	/**
	 * Get customers by active status
	 * @param active
	 * @return a list of active customers
	 * @throws DaoException
	 */
	List<Customer> getCustomersByActive(Boolean active) throws DaoException;
	
	/**
	 * Get customers with mobile
	 * @return a list of customers with a mobile
	 * @throws DaoException
	 */
	List<Customer> getCustomersWithMobile() throws DaoException;
	
	/**
	 * Get a customer by ID with a Criteria Query
	 * @param id
	 * @return the customer
	 * @throws DaoException
	 */
	Customer getCustomerByIdWithCriteriaQuery(Integer id) throws DaoException;
	
	/**
	 * Get Customers with a Mobile (Criteria Query)
	 * @returna list of customers with a Mobile
	 * @throws DaoException
	 */
	List<Customer> getCustomersWithMobileWithCriteriaQuery() throws DaoException;
	
	/**
	 * Create a customer
	 * @param customer the customer to create
	 * @return the customer
	 * @throws DaoException
	 */
	Customer createCustomer(Customer customer) throws DaoException;
	
	/**
	 * Update a customer
	 * @param customer the customer to update
	 * @throws DaoException
	 */
	void updateCustomer(Customer customer) throws DaoException;
	
	/**
	 * Delete a customer
	 * @param customer the customer to delete
	 * @throws DaoException
	 */
	void deleteCustomer(Customer customer) throws DaoException;
	
	/**
	 * Delete all customers without order
	 * @throws DaoException
	 */
	void deleteCustomerWithoutOrder() throws DaoException;
	
	
}
