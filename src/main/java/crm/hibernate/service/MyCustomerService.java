package crm.hibernate.service;

import java.util.List;

import crm.hibernate.model.Customer;

public interface MyCustomerService {
	
	Customer getCustomerById(Integer id);
	
	List<Customer> getAllCustomers();
	
	List<Customer> getCustomerByActive(Boolean active);
	
	Customer createCustomer(Customer c);
	
	void updateCustomer(Customer c);
	
	void deleteCustomer(Customer c);

}
