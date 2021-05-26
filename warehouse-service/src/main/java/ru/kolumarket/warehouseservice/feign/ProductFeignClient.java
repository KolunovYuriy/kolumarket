package ru.kolumarket.warehouseservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import ru.kolumarket.core.externalclient.ProductClient;

@FeignClient("product-service")
public interface ProductFeignClient extends ProductClient {
}