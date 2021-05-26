package ru.kolumarket.warehouseservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kolumarket.warehouseservice.domain.ProductItem;
import ru.kolumarket.warehouseservice.dto.ProductItemDTO;
import ru.kolumarket.warehouseservice.feign.ProductFeignClient;
import ru.kolumarket.warehouseservice.repository.ProductItemRepository;
import ru.kolumarket.warehouseservice.repository.WarehouseRepository;

@Service
public class WarehouseService {

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ProductFeignClient productClient;

    public ProductItemDTO addProduct(ProductItemDTO productItemDTO) {

        ProductItem currentProductItem = productItemRepository.getProductItemByProductIdAndWarehouse(productItemDTO.getProductId(),warehouseRepository.findById(productItemDTO.getWarehouseId()).get()).orElseGet(()->
                new ProductItem(productItemDTO.getProductId(), warehouseRepository.findById(productItemDTO.getWarehouseId()).get())
        );
        currentProductItem.setCount(currentProductItem.getCount()+1);
        return new ProductItemDTO(productItemRepository.save(currentProductItem),productClient.getById(productItemDTO.getProductId()).getTitle());
    }

}
