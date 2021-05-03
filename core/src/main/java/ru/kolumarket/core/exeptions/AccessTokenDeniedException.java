package ru.kolumarket.core.exeptions;

public class AccessTokenDeniedException extends RuntimeException {
    public AccessTokenDeniedException(String jwt) {
        super("Access Denied with JWT: " + jwt);
    }
}