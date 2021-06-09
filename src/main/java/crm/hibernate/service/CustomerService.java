package crm.hibernate.service;

import java.util.List;

import crm.hibernate.model.Customer;

public interface CustomerService {
	
	/**
	 * Get a customer by id
	 * @param id the id
	 * @return the Customer
	 */
	Customer getCustomerById(Integer id);
	
	/**
	 * Get all customers
	 * @return a list of customers
	 */
	List<Customer> getAllCustomers();
	
	/**
	 * Get customers by active status
	 * @param active true or false
	 * @return a list of customers by active status
	 */
	List<Customer> getCustomersByActive(Boolean active);
	
	
	/**
	 * Create a customer
	 * @param customer the customer to create
	 */
	Customer createCustomer(Customer customer);
	
	/**
	 * Update a customer
	 * @param customer the customer to update
	 */
	void updateCustomer(Customer customer);
	
	/**
	 * Delete a customer
	 * @param customer the customer to delete
	 */
	void deleteCustomer(Customer customer);

}
