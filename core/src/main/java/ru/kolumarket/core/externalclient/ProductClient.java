package ru.kolumarket.core.externalclient;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolumarket.core.dto.ProductDtoCore;

import javax.servlet.http.HttpServletResponse;

public interface ProductClient {

    @GetMapping("/{id}")
    ProductDtoCore getById(@PathVariable Long id);

    @GetMapping
    Page<ProductDtoCore> getAll(
            @RequestParam(value = "l", required = false) Integer leftCost,
            @RequestParam(value = "r", required = false) Integer rightCost,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(value = "sort", required = false, defaultValue = "") String[] sort
    );

    @GetMapping("/find")
    ResponseEntity<?> getProductsLikeTitle(
            HttpServletResponse response,
            @RequestParam String title,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(value = "sort", required = false, defaultValue = "") String[] sort
    );

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);

    @PostMapping
    ProductDtoCore add(@RequestBody ProductDtoCore productDTO);

    @PutMapping
    ProductDtoCore update(@RequestBody ProductDtoCore productDTO);
}
