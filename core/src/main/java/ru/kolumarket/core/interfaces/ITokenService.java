package ru.kolumarket.core.interfaces;

import ru.kolumarket.core.domain.UserInfo;

public interface ITokenService {

    String generateToken(UserInfo user);
    UserInfo parseToken(String token);
    Long getJwtExpiration();
}
