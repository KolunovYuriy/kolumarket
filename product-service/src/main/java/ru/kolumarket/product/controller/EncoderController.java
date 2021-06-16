package ru.kolumarket.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/encode")
//http://localhost:8189/encode/
public class EncoderController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @param password - пароль, который необходим закодировать
     * @return BCryptPasswordEncoder.encode
     */
    @GetMapping
    public ResponseEntity<String> getEncodePassword(
            @RequestParam(value = "password") String password
    ) {
        return ResponseEntity.ok(passwordEncoder.encode(password));
    }

}
