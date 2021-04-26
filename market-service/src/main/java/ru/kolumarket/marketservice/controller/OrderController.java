package ru.kolumarket.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kolumarket.core.domain.UserInfo;
import ru.kolumarket.core.interfaces.ITokenService;
import ru.kolumarket.marketservice.dto.OrderItemDTO;
import ru.kolumarket.marketservice.services.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/orders")
//http://localhost:8189/market/orders/
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<?> getAll(
            HttpServletRequest request,
            @RequestParam(value = "sort", required = false, defaultValue = "") String[] sort
    ) {
        return ResponseEntity.ok(orderService.getCutomerOrder((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/add/{productId}")
    public ResponseEntity<OrderItemDTO> addProduct(
            @PathVariable Long productId
    ) {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addProduct(productId, userInfo));
    }

}
