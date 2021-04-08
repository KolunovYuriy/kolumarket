package ru.kolumarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kolumarket.configurations.jwt.JwtProvider;
import ru.kolumarket.domain.User;
import ru.kolumarket.dto.AuthRequestDTO;
import ru.kolumarket.dto.AuthResponseDTO;
import ru.kolumarket.dto.SignUpRequestDTO;
import ru.kolumarket.exeptions.MarketError;
import ru.kolumarket.services.UserService;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public String registerUser(@RequestBody SignUpRequestDTO signUpRequest) {
        User user = new User();
        user.setPassword(signUpRequest.getPassword());
        user.setLogin(signUpRequest.getLogin());
        userService.saveUser(user);
        return "OK";
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody AuthRequestDTO request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        if (user == null) return new ResponseEntity<>(new MarketError(HttpStatus.NOT_FOUND.value(), "Access denied"),HttpStatus.NOT_ACCEPTABLE);
        else {
            String token = jwtProvider.generateToken(user.getLogin());
            return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponseDTO(token));
        }
    }
}