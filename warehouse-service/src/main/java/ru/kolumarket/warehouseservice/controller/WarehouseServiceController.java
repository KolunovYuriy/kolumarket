package ru.kolumarket.warehouseservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolumarket.core.dto.ProductDtoCore;
import ru.kolumarket.core.exeptions.ResourceNotFoundException;
import ru.kolumarket.warehouseservice.dto.ProductItemDTO;
import ru.kolumarket.warehouseservice.services.WarehouseService;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@RequestMapping("/warehouse")
//http://localhost:8192/warehouse/
public class WarehouseServiceController {
    @Autowired
    private HttpSession httpSession;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private RedisTemplate redisTemplate;

    //http://localhost:8192/warehouse/
    @PostMapping("/addproduct")
    public ResponseEntity<?> add(
            @RequestBody ProductItemDTO productItemDTO
//            @PathVariable(required = true) Long productId,
//            @PathVariable(value = "1") Long warehouseId
    ) {
        return ResponseEntity.ok(warehouseService.addProduct(productItemDTO));
                //.getById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id: " + id + " doesn't exist"));
    }
}
