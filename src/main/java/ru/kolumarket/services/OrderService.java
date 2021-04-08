package ru.kolumarket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kolumarket.domain.Customer;
import ru.kolumarket.domain.OrderItem;
import ru.kolumarket.domain.Product;
import ru.kolumarket.domain.User;
import ru.kolumarket.dto.OrderItemDTO;
import ru.kolumarket.repository.CustomerRepository;
import ru.kolumarket.repository.OrderItemRepository;
import ru.kolumarket.repository.ProductRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<OrderItemDTO> getCutomerOrder(String sessionId) {
        return orderItemRepository.getOrderItemsByCustomer(customerRepository.getCustomerBySessionId(sessionId).orElseGet(()->customerRepository.save(new Customer()))).stream().map(orderItem -> new OrderItemDTO(orderItem)).collect(Collectors.toList());
    }

    public List<OrderItemDTO> getCutomerOrder(User user) {
        return orderItemRepository.getOrderItemsByCustomer(customerRepository.getCustomerByUser(user).orElseGet(()->customerRepository.save(new Customer("", user)))).stream().map(orderItem -> new OrderItemDTO(orderItem)).collect(Collectors.toList());
    }

    @Transactional
    public OrderItemDTO addProduct(Long productId, String sessionId) {
        Customer currentCustomer = customerRepository.getCustomerBySessionId(sessionId).orElseGet(()->customerRepository.save(new Customer("noname",sessionId)));
        Product currentProduct = productRepository.findById(productId).get();
        OrderItem orderItem = orderItemRepository.getOrderItemByCustomerAndProduct(currentCustomer,currentProduct).orElseGet(()->
                orderItemRepository.save(new OrderItem(currentProduct,currentCustomer))
        );
        orderItem.setCount(orderItem.getCount()+1);
        return new OrderItemDTO(orderItemRepository.save(orderItem));
    }

    @Transactional
    public OrderItemDTO addProduct(Long productId, User user) {
        Customer currentCustomer = customerRepository.getCustomerByUser(user).orElseGet(()->customerRepository.save(new Customer("",user)));
        Product currentProduct = productRepository.findById(productId).get();
        OrderItem orderItem = orderItemRepository.getOrderItemByCustomerAndProduct(currentCustomer,currentProduct).orElseGet(()->
                orderItemRepository.save(new OrderItem(currentProduct,currentCustomer))
        );
        orderItem.setCount(orderItem.getCount()+1);
        return new OrderItemDTO(orderItemRepository.save(orderItem));
    }

}
