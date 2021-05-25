package ru.kolumarket.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import ru.kolumarket.core.externalclient.ProductClient;

@FeignClient("market-service")
public interface ProductFeignClient extends ProductClient {
}
