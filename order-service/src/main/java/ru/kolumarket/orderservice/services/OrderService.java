package ru.kolumarket.orderservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kolumarket.core.domain.UserInfo;
import ru.kolumarket.core.dto.ProductDtoCore;
import ru.kolumarket.orderservice.domain.Customer;
import ru.kolumarket.orderservice.domain.Order;
import ru.kolumarket.orderservice.domain.OrderItem;
import ru.kolumarket.orderservice.domain.OrderStatus;
import ru.kolumarket.orderservice.dto.OrderItemDTO;
import ru.kolumarket.orderservice.feign.ProductFeignClient;
import ru.kolumarket.orderservice.repository.CustomerRepository;
import ru.kolumarket.orderservice.repository.OrderItemRepository;
import ru.kolumarket.orderservice.repository.OrderRepository;

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
    private RestTemplate restTemplate;

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
    public OrderItemDTO addProduct(Long productId, ProductFeignClient productFeignClient, UserInfo user) {
        Order currentOrder = getCreatedUserOrder(user);
        //Product currentProduct = productRepository.findById(productId).get();
        OrderItem orderItem = orderItemRepository.getOrderItemByProductIdAndOrder(productId,currentOrder).orElseGet(()->
                new OrderItem(productId,productFeignClient.getById(productId).getTitle(),currentOrder)
                //new OrderItem(productId,restTemplate.getForObject("http://localhost:8190/market/products/"+productId, ProductDtoCore.class).getTitle(),currentOrder)
        );
        orderItem.setCount(orderItem.getCount()+1);
        return new OrderItemDTO(orderItemRepository.save(orderItem));
    }

}
