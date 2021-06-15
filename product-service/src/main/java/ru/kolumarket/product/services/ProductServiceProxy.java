package ru.kolumarket.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.kolumarket.core.dto.ProductDtoCore;

import java.time.Duration;

@Service
public class ProductServiceProxy {

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${kolumarket.jwt.redis_cash_expiration}")
    private String redisCashExpiration;

    public Page<ProductDtoCore> getAll(Integer leftCost, Integer rightCost, Integer page, Integer size, String[] sort) {
        String key = "getPageableFromRequest_leftCost="+leftCost+ "rightCost="+rightCost+ "_page=" + page + "_size="+ size + "_sort="+sort;
        if (redisTemplate.hasKey(key)) {
            return (Page<ProductDtoCore>)redisTemplate.opsForValue().get(key);
        }
        else {
            Page<ProductDtoCore> result = productService.getAll(leftCost, rightCost, page,size,sort);
            redisTemplate.opsForValue().setIfAbsent(key, result, Duration.ofHours(Long.parseLong(redisCashExpiration)));
            return result;
        }
    }

}
