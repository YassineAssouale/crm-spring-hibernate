package crm.hibernate.repository;

import java.util.List;

import crm.hibernate.exception.DaoException;
import crm.hibernate.model.Order;


public interface OrderRepository {

	/**
	 * Get an order by id 
	 * @param id the order id
	 * @return the order
	 * @throws DaoException
	 */
	Order getById(Integer id) throws DaoException;

	/**
	 * Get all orders
	 * @return a list of orders
	 * @throws DaoException
	 */
	List<Order> getAllOrders() throws DaoException;
	
	/**
	 * Get a list of orders for a type and a status
	 * @param type the type
	 * @param status the status
	 * @return a list of orders
	 * @throws DaoException
	 */
	List<Order> getOrdersByTypeAndStatus(String type, String status) throws DaoException;
	
	/**
	 * Get a list of orders for a number of days greater than the value of parameter
	 * @param numberOfDays the number of days parameter
	 * @return a list of orders
	 * @throws DaoException
	 */
	List<Order> getOrdersForNumberOfDays(Double numberOfDays) throws DaoException;
	
	/**
	 * Get all orders or a customer
	 * @param customerId id of customer
	 * @return a list of orders for a customer
	 * @throws DaoException
	 */
	List<Order> getOrdersByCustomer(Integer customerId) throws DaoException;

	/**
	 * Create an order
	 * @param order the order to create
	 * @throws DaoException
	 */
	void createOrder(Order order) throws DaoException;
	
	/**
	 * Update an order
	 * @param order the order to update
	 * @throws DaoException
	 */
	void updateOrder(Order order) throws DaoException;
	
	/**
	 * Update status of orders with an old status and a type
	 * @param newStatus the new status
	 * @param oldStatus the old status
	 * @param type the type
	 * @throws DaoException
	 */
	void updateOrderStatus(String newStatus, String oldStatus, String type) throws DaoException;

	/**
	 * Delete an order
	 * @param order the order to delete
	 * @throws DaoException
	 */
	void deleteOrder(Order order) throws DaoException;

}
