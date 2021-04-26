package ru.kolumarket.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kolumarket.authservice.domain.User;
import ru.kolumarket.authservice.dto.AuthRequestDTO;
import ru.kolumarket.authservice.dto.AuthResponseDTO;
import ru.kolumarket.authservice.dto.SignUpRequestDTO;
import ru.kolumarket.authservice.services.UserService;
import ru.kolumarket.core.domain.UserInfo;
import ru.kolumarket.core.exeptions.MarketError;
import ru.kolumarket.core.interfaces.ITokenService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private ITokenService tokenService;

    @Value("${server.servlet.context-path}")
    String path;

    @PostMapping("/register")
    public String registerUser(@RequestBody SignUpRequestDTO signUpRequest) {
        User user = new User();
        user.setPassword(signUpRequest.getPassword());
        user.setLogin(signUpRequest.getLogin());
        userService.saveUser(user);
        return "OK";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletResponse response, @RequestBody AuthRequestDTO request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        if (user == null) return new ResponseEntity<>(new MarketError(HttpStatus.NOT_FOUND.value(), "Access denied"),HttpStatus.NOT_ACCEPTABLE);
        else {
            UserInfo userInfo = UserInfo.builder()
                    .userId(user.getId())
                    .role(user.getRole().getName())
                    .build();
            String token = tokenService.generateToken(userInfo);

            Cookie cookie = new Cookie("token", token.replace("Bearer ", ""));
            cookie.setPath(path);
            cookie.setMaxAge(tokenService.getJwtExpiration().intValue()*3600);
            response.addCookie(cookie);
            response.setContentType("text/plain");

            return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponseDTO(token));
        }
    }

}