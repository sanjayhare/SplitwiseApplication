package com.splitwise.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AuthenticateRequestDto {
    private String username;
    private String password;
}
