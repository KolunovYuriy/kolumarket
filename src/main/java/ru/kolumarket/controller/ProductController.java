package ru.kolumarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolumarket.domain.Product;
import ru.kolumarket.dto.ProductDTO;
import ru.kolumarket.exeptions.ResourceNotFoundException;
import ru.kolumarket.services.ProductService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/products")
//http://localhost:8189/market/products/
public class ProductController {

    @Autowired
    private HttpSession httpSession;

    @Value("${server.servlet.context-path}")
    String path;

    @Autowired
    private ProductService productService;

    //http://localhost:8189/market/products/{id}
    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable Long id) {
        return productService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id: " + id + " doesn't exist"));
    }

    /**
     * http://localhost:8189/market/products
     * Для сортировки  необходимо использовать зарезервированный параметр sort, в котором указывать поле и направление сортировки через запятую, в случае необходимости сортировки по N полям использовать N пар поле / направление дублируя параметр sort
     * пример http://localhost:8189/app/products?sort=cost,ASC&sort=title,DESC&sort=id,ASC
     */
    @GetMapping
    public Page<ProductDTO> getAll(
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
        Cookie cookie = new Cookie("data", "Hello");//создаем объект Cookie,
        //в конструкторе указываем значения для name и value

        cookie.setPath(path);//устанавливаем путь
        cookie.setMaxAge(86400);//здесь устанавливается время жизни куки
        response.addCookie(cookie);//добавляем Cookie в запрос
        response.setContentType("text/plain");//устанавливаем контекст
        return ResponseEntity.ok(productService.getProductsLikeTitle(title.toLowerCase(),page-1, size, sort));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PostMapping
    public ProductDTO add(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping
    public ProductDTO update(@RequestBody Product product) {
        if (product.getId()<=0) {
            throw new ResourceNotFoundException("doesn't insert Product id");
        }
        return productService.updateProduct(productService.getProductById(product.getId()).orElseThrow(() -> new ResourceNotFoundException("Product with id: " + product.getId() + " doesn't exist")));
    }

}
