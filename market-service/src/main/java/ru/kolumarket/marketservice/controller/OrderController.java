package ru.kolumarket.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kolumarket.marketservice.services.OrderService;
import ru.kolumarket.marketservice.services.UserService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/orders")
//http://localhost:8189/market/orders/
public class OrderController {
    @Autowired
    private HttpSession httpSession;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(value = "sort", required = false, defaultValue = "") String[] sort
    ) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(orderService.getCutomerOrder(userService.findByLogin(userName)));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/add/{productId}")
    public ResponseEntity<?> addProduct(
            @PathVariable Long productId
    ) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addProduct(productId, userService.findByLogin(userName)));
    }

}
