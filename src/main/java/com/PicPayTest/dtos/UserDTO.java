package com.PicPayTest.dtos;

import java.math.BigDecimal;

import com.PicPayTest.domain.user.UserType;

public record UserDTO(String firstName, String lastName, String document, BigDecimal balance, String email, String password, UserType userType) {
    
}
