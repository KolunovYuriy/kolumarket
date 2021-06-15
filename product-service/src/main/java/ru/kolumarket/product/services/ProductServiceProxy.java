package ru.kolumarket.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.kolumarket.core.dto.ProductDtoCore;
import ru.kolumarket.product.dto.ProductDTO;
import ru.kolumarket.product.repository.ProductRepository;

import java.util.Optional;

@Service
public class ProductServiceProxy {

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisTemplate redisTemplate;

    public Page<ProductDtoCore> getAll(Integer leftCost, Integer rightCost, Integer page, Integer size, String[] sort) {
        String key = "getPageableFromRequest_leftCost="+leftCost+ "rightCost="+rightCost+ "_page=" + page + "_size="+ size + "_sort="+sort;
        if (redisTemplate.hasKey(key)) {
            return (Page<ProductDtoCore>)redisTemplate.opsForValue().get(key);
        }
        else {
            return productService.getAll(leftCost, rightCost, page,size,sort);
        }
    }

}
