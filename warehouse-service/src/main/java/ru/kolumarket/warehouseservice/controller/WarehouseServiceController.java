package ru.kolumarket.warehouseservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    //http://localhost:8192/warehouse/
    @PostMapping("/addproduct")
    public ResponseEntity<?> add(
            @RequestBody ProductItemDTO productItemDTO
    ) {
        return ResponseEntity.ok(warehouseService.addProduct(productItemDTO));
    }
}
