package ru.kolumarket.product.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kolumarket.core.dto.ProductItemDtoCore;

@Component
public class WarehouseConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(WarehouseConsumerService.class);

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = {"warehouse_domain_event"})
    public void receiveMessage(String ProductItemDTO) // ProductItemDtoCore ProductItemDTO
    {
        ObjectMapper mapper = new ObjectMapper();
        ProductItemDtoCore productItemInfo = null;
        try {
            productItemInfo = mapper.readValue(ProductItemDTO, ProductItemDtoCore.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        productService.updateProductRemainder(productItemInfo.getProductId(), productItemInfo.getCount());

        logger.info(" receive message [" + productItemInfo.toString() + "] ");
    }

}
