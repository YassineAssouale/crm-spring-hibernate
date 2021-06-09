package crm.hibernate.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import crm.hibernate.exception.DaoException;
import crm.hibernate.model.Customer;
import crm.hibernate.repository.CustomerRepository;
import crm.hibernate.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {

	Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.getAllCustomers();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Customer getCustomerById(Integer customerId) {
		return customerRepository.getCustomerById(customerId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Customer> getCustomersByActive(Boolean active) {
		return customerRepository.getCustomersByActive(active);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Customer createCustomer(Customer customer) {
		return customerRepository.createCustomer(customer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void updateCustomer(Customer customer) {
		try {
			customerRepository.updateCustomer(customer);
		} catch (DaoException e) {
			logger.error("Erreur de la modification du customer : {}", e.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void deleteCustomer(Customer customer) {
		if (getCustomerById(customer.getId()) != null) {
			try {
				customerRepository.deleteCustomer(customer);
			} catch (DaoException e) {
				logger.error("Erreur de la modification du customer : {}", e.getMessage());
			}
		} else {
			logger.error("Le customer n'existe pas : {}", customer.getId());
		}
	}

}
