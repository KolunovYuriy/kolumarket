package ru.kolumarket.dto;

import lombok.Data;

@Data
public class SignUpRequestDTO {

    private String login;

    private String password;
}