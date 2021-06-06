package ru.kolumarket.warehouseservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kolumarket.core.dto.ProductDtoCore;
import ru.kolumarket.core.dto.ProductItemDtoCore;
import ru.kolumarket.warehouseservice.domain.ProductItem;
import ru.kolumarket.warehouseservice.dto.ProductItemDTO;
import ru.kolumarket.warehouseservice.feign.ProductFeignClient;
import ru.kolumarket.warehouseservice.repository.ProductItemRepository;
import ru.kolumarket.warehouseservice.repository.WarehouseRepository;

@Service
public class WarehouseService {

    public static final String EXCHANGE_FOR_PROCESSING_TASK = "warehouseExchanger";

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ProductFeignClient productClient;

    @Autowired
    private RestTemplate restTemplate;

    private RabbitTemplate rabbitTemplate;
    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public ProductItemDtoCore addProduct(ProductItemDTO productItemDTO) {

        ProductItemDtoCore result;

        ProductItem currentProductItem = productItemRepository.getProductItemByProductIdAndWarehouse(productItemDTO.getProductId(),warehouseRepository.findById(productItemDTO.getWarehouseId()).get()).orElseGet(()->
                new ProductItem(productItemDTO.getProductId(), warehouseRepository.findById(productItemDTO.getWarehouseId()).get())
        );
        currentProductItem.setCount(currentProductItem.getCount()+1);
        ProductDtoCore productDTO;
        //productDTO = productClient.getById(productItemDTO.getProductId()); // TODO: разобраться, почему перестало работать
        productDTO = restTemplate.getForObject("http://localhost:8190/"+productItemDTO.getProductId(), ProductDtoCore.class);
        result = new ProductItemDTO(productItemRepository.save(currentProductItem),productDTO.getTitle(), productItemRepository.getCountOfProductInAllWarehouses(productItemDTO.getProductId()));

        rabbitTemplate.convertAndSend(EXCHANGE_FOR_PROCESSING_TASK,"ru.kolumarket.warehouse.ProductItemDTO", result);
        //rabbitTemplate.convertAndSend("ru.kolumarket.warehouse.ProductItemDTO", result); // нужно задать exchange

        return result;
    }

}
