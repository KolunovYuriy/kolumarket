package ru.kolumarket.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolumarket.core.dto.ProductDtoCore;
import ru.kolumarket.core.externalclient.ProductClient;
import ru.kolumarket.product.dto.ProductDTO;
import ru.kolumarket.core.exeptions.ResourceNotFoundException;
import ru.kolumarket.product.services.ProductService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
//@RequestMapping("/products")
//http://localhost:8190/
public class ProductController implements ProductClient {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisTemplate redisTemplate;

    //http://localhost:8189/market/products/{id}
    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable Long id) {
        return productService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id: " + id + " doesn't exist"));
    }

    /**
     * http://localhost:8189/market/products
     * Для сортировки  необходимо использовать зарезервированный параметр sort, в котором указывать поле и направление сортировки через запятую, в случае необходимости сортировки по N полям использовать N пар поле / направление дублируя параметр sort
     * пример http://localhost:8189/app/products?sort=cost,ASC&sort=title,DESC&sort=id,ASC
     * @return
     */
    @GetMapping
    public Page<ProductDtoCore> getAll(
            @RequestParam(value = "l", required = false) Integer leftCost,
            @RequestParam(value = "r", required = false) Integer rightCost,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(value = "sort", required = false, defaultValue = "") String[] sort
    ) {
        return productService.getAll(leftCost, rightCost, page-1, size, sort);
    }

//    @GetMapping("/find")
//    public Page<ProductDTO> getProductsLikeTitle(
//            @RequestParam String title,
//            @RequestParam(defaultValue = "1") Integer page,
//            @RequestParam(defaultValue = "10") Integer size,
//            @RequestParam(value = "sort", required = false, defaultValue = "") String[] sort
//    ) {
//        return productService.getProductsLikeTitle(title.toLowerCase(),page-1, size, sort);
//    }

    @GetMapping("/find")
    public ResponseEntity<?> getProductsLikeTitle(
            HttpServletResponse response,
            @RequestParam String title,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(value = "sort", required = false, defaultValue = "") String[] sort
    ) {
        return ResponseEntity.ok(productService.getProductsLikeTitle(title.toLowerCase(),page-1, size, sort));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PostMapping
    public ProductDtoCore add(@RequestBody ProductDtoCore productDTO) {
        return productService.addProduct(productDTO);
    }

    @PutMapping
    public ProductDtoCore update(@RequestBody ProductDtoCore productDTO) {
        if (productDTO.getId()<=0) {
            throw new ResourceNotFoundException("doesn't insert Product id");
        }
        return productService.updateProduct(productDTO);
    }

}
