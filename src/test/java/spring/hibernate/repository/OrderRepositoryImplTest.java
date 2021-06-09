package spring.hibernate.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import crm.hibernate.config.AppConfig;
import crm.hibernate.model.Order;
import crm.hibernate.repository.OrderRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext
@TestMethodOrder(OrderAnnotation.class)
class OrderRepositoryImplTest {

	Logger logger = LoggerFactory.getLogger(OrderRepositoryImplTest.class);

	public static final String LABEL = "Formation Java";
	public static final String LASTNAME = "GILBERT";

	@Autowired
	private OrderRepository orderRepository;

	@Test
	@org.junit.jupiter.api.Order(1)
	void givenOrderId_whenCallingTypedQueryMethod_thenReturnOrder() {
		Order order = orderRepository.getById(1);
		logger.debug("Commande = {}", order);
		Assertions.assertNotNull(order, "Order not found");
	}

	@Test
	@org.junit.jupiter.api.Order(2)
	void givenOrderId_whenCallingTypedQueryMethod_thenReturnExpectedOrder() {
		Order order = orderRepository.getById(1);
		Assertions.assertEquals(LABEL, order.getLabel(), "Order label should be " + LABEL);
	}

	@Test
	@org.junit.jupiter.api.Order(3)
	void givenOrderId_whenCallingTypedQueryMethod_thenReturnExpectedCustomer() {
		Order order = orderRepository.getById(1);
		Assertions.assertEquals(LASTNAME, order.getCustomer().getLastname(), "Customer lastname should be " + LASTNAME);
	}

	@Test
	@org.junit.jupiter.api.Order(4)
	void whenCallingTypedQueryMethod_thenReturnListOfOrders() {
		List<Order> orders = orderRepository.getAllOrders();
		Assertions.assertEquals(3, orders.size(), "Wrong number of orders");
	}

	@Test
	@org.junit.jupiter.api.Order(5)
	void whenCallingNamedQueryMethod_thenReturnListOfOrders() {
		List<Order> orders = orderRepository.getOrdersByTypeAndStatus("Forfait", "En attente");
		Assertions.assertEquals(1, orders.size(), "Wrong number of orders for type and status");
	}

	@Test
	@org.junit.jupiter.api.Order(6)
	void whenCallingNativeQueryMethod_thenReturnListOfOrders() {
		List<Order> orders = orderRepository.getOrdersForNumberOfDays(Double.valueOf(2.0));
		Assertions.assertEquals(2, orders.size(), "Wrong number of orders for a number of days greater than 2");
	}

	@Test
	@org.junit.jupiter.api.Order(7)
	void givenOrder_whenCallingTypedQueryMethodForCreate_thenReturnOK() {
		Order newOrder = new Order();
		newOrder.setLabel(LABEL);
		newOrder.setNumberOfDays(Double.valueOf(5));
		newOrder.setAdrEt(Double.valueOf(350));
		newOrder.setTva(Double.valueOf(20.0));
		newOrder.setType("Super commande");
		newOrder.setStatus("En cours");
		newOrder.setNotes("Les notes sur la commande");

		List<Order> orders = orderRepository.getAllOrders();
		int numberOfOrdersBeforeCreation = orders.size();

		orderRepository.createOrder(newOrder);

		List<Order> ordersAfterCreation = orderRepository.getAllOrders();
		int numberOfOrdersAfterCreation = ordersAfterCreation.size();
		Assertions.assertEquals(numberOfOrdersBeforeCreation + 1, numberOfOrdersAfterCreation);
	}

	@Test
	@org.junit.jupiter.api.Order(8)
	void givenOrder_whenCallingTypedQueryMethodForUpdate_thenReturnOK() {

		Order order = orderRepository.getById(2);
		order.setLabel("Nouveau libellé");

		orderRepository.updateOrder(order);

		Order updatedOrder = orderRepository.getById(2);
		Assertions.assertEquals("Nouveau libellé", updatedOrder.getLabel());
	}

	@Test
	@org.junit.jupiter.api.Order(9)
	void givenOrder_whenCallingTypedQueryMethodForDelete_thenReturnOK() {
		Order order = orderRepository.getById(2);

		orderRepository.deleteOrder(order);

		Order deletedOrder = orderRepository.getById(2);
		logger.debug("Deleted Order = {}", deletedOrder);
		Assertions.assertNull(deletedOrder, "Deleted order must be null");
	}

	@Test
	@org.junit.jupiter.api.Order(10)
	void whenCallingCriteriaUpdateQueryMethodForUpdate_thenReturnOK() {

		orderRepository.updateOrderStatus("Terminé", "En cours", "Forfait");

		List<Order> updatedOrders = orderRepository.getOrdersByTypeAndStatus("Forfait", "Terminé");
		Assertions.assertEquals(1, updatedOrders.size(), "Wrong number of orders with type Forfait and status Terminé");
	}

}
