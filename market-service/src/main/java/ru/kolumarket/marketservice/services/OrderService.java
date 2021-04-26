package ru.kolumarket.marketservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kolumarket.core.domain.UserInfo;
import ru.kolumarket.marketservice.domain.*;
import ru.kolumarket.marketservice.dto.OrderItemDTO;
import ru.kolumarket.marketservice.repository.CustomerRepository;
import ru.kolumarket.marketservice.repository.OrderItemRepository;
import ru.kolumarket.marketservice.repository.OrderRepository;
import ru.kolumarket.marketservice.repository.ProductRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<OrderItemDTO> getCutomerOrder(UserInfo user) {
        return orderItemRepository.getOrderItemsByOrder(getCreatedUserOrder(user)).stream().map(orderItem -> new OrderItemDTO(orderItem)).collect(Collectors.toList());
    }

    private Order getCreatedUserOrder(UserInfo user) {
        Customer currentCustomer = customerRepository.getCustomerByUserId(user.getUserId().intValue()).orElseGet(()->customerRepository.save(new Customer("",user)));
//        return orderRepository.getOrderByCustomerAndStatus(currentCustomer, OrderStatus.CREATE.name()).orElseGet(()->
//                orderRepository.save(new Order(currentCustomer, OrderStatus.CREATE))
//
//        );
        return orderRepository.getOrderByCustomer(currentCustomer).orElseGet(()->
                orderRepository.save(new Order(currentCustomer, OrderStatus.CREATE))
        );
    }

    @Transactional
    public OrderItemDTO addProduct(Long productId, UserInfo user) {
        Order currentOrder = getCreatedUserOrder(user);
        Product currentProduct = productRepository.findById(productId).get();
        OrderItem orderItem = orderItemRepository.getOrderItemByProductAndOrder(currentProduct,currentOrder).orElseGet(()->
                new OrderItem(currentProduct,currentOrder)
        );
        orderItem.setCount(orderItem.getCount()+1);
        return new OrderItemDTO(orderItemRepository.save(orderItem));
    }

}
