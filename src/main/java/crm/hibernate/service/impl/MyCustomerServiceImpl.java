package crm.hibernate.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import crm.hibernate.exception.DaoException;
import crm.hibernate.model.Customer;
import crm.hibernate.repository.CustomerRepository;
import crm.hibernate.service.MyCustomerService;

public class MyCustomerServiceImpl implements MyCustomerService{

	
	Logger logger = LoggerFactory.getLogger(MyCustomerServiceImpl.class);
	
	@Autowired
	CustomerRepository customerRepository;
	
	
	@Override
	public Customer getCustomerById(Integer id) {
		
		return customerRepository.getCustomerById(id);
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return customerRepository.getAllCustomers();
	}

	@Override
	public List<Customer> getCustomerByActive(Boolean active) {
		// TODO Auto-generated method stub
		return customerRepository.getCustomersByActive(active);
	}

	@Override
	public Customer createCustomer(Customer c) {
		// TODO Auto-generated method stub
		return customerRepository.createCustomer(c);
	}

	@Override
	public void updateCustomer(Customer c) {
		try {
			customerRepository.updateCustomer(c);
		} catch (DaoException e) {
			logger.error("Error while updating customer : {}", e.getMessage());
		}
		
		
	}

	@Override
	public void deleteCustomer(Customer c) {
		if(getCustomerById(c.getId()) != null) {
			try {
				customerRepository.deleteCustomer(c);
			} catch (DaoException e) {
				logger.error("Error while deleting customer : {}", e.getMessage());
			}
			
		} else {
			logger.error("Customer not found ! : {}", c.getId());
		}	
	}

}
