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

import crm.hibernate.config.AppConfig;
import crm.hibernate.model.Customer;
import crm.hibernate.repository.CustomerRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext
class CustomerRepositoryImplTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	void testGetAllCustomers() {
		List<Customer> customers = customerRepository.getAllCustomers();
		assertEquals(4, customers.size());
	}

	@Test
	void testGetCustomersByActive() {
		List<Customer> customers = customerRepository.getCustomersByActive(true);
		assertEquals(2, customers.size());
	}

	@Test
	void testCreate() {
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
		Assertions.assertNotNull(customer, "Winnie not found");
	}

	@Test
	void testUpdate() {
		Customer customer = customerRepository.getCustomerByLastname("GILBERT");
		customer.setCompany("Nouvelle entreprise");

		customerRepository.updateCustomer(customer);

		Customer updatedCustomer = customerRepository.getCustomerByLastname("GILBERT");
		Assertions.assertEquals("Nouvelle entreprise", updatedCustomer.getCompany());
	}

	@Test
	void testDelete() {
		Customer customer = customerRepository.getCustomerById(2);

		customerRepository.deleteCustomer(customer);

		Customer deletedCustomer = customerRepository.getCustomerById(2);
		Assertions.assertNull(deletedCustomer, "Deleted customer must be null");

	}
}
