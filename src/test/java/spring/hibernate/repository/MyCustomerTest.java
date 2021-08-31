package spring.hibernate.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import crm.hibernate.config.MyConfig;
import crm.hibernate.model.Customer;
import crm.hibernate.repository.CustomerRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MyConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext
public class MyCustomerTest {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Test
	void testGetAllCustomers() {
		List<Customer> customers = customerRepository.getAllCustomers();
		assertEquals(4, customers.size());
	}
	
	@Test
	void testGetCustomerByActive() {
		List<Customer> customers = customerRepository.getCustomersByActive(true);
		assertEquals(2, customers.size());
	}
	
	@Test
	void testCreateCustomer() {
		Customer newCustomer = new Customer();
		newCustomer.setFirstname("Winnie");
		newCustomer.setLastname("L'Ourson");
		newCustomer.setCompany("Disney");
		newCustomer.setPhone("0222222222");
		newCustomer.setMobile("0666666666");
		newCustomer.setMail("winnie.l.ourson@disney.com");
		newCustomer.setNotes("Les notes de Winnie");
		newCustomer.setActive(true);
		customerRepository.createCustomer(newCustomer);
		
		Customer customer = customerRepository.getCustomerByLastname("L'Ourson");
		Assertions.assertNotNull(customer,"L'Ourson not found !");
	}
	
	@Test
	void testUpdateCustomer() {
		Customer customer = customerRepository.getCustomerById(1);
		customer.setFirstname("Hihi");
		customerRepository.updateCustomer(customer);
		
		Customer c = customerRepository.getCustomerByLastname("GILBERT");
		assertEquals("Hihi", customer.getFirstname());
	}
	
	@Test
	void testDelete() {
		customerRepository.deleteCustomer(customerRepository.getCustomerById(1));
		Assertions.assertNull(customerRepository.getCustomerById(1),"Deleted customer must be null !");
	}
}
