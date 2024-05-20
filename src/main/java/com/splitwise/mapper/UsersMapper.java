package com.splitwise.mapper;


import com.splitwise.dto.UsersDto;
import com.splitwise.entity.Users;

public class UsersMapper {

    public static UsersDto mappedToUserDTO (UsersDto userDto, Users users){


        userDto.setUserName(users.getName());
        userDto.setGender(users.getGender());
        userDto.setEmailId(users.getEmailId());
        userDto.setMobileNumber(users.getMobileNumber());
        userDto.setDateOfBirth(users.getDateOfBirth());
        userDto.setAddress(users.getAddress());
        return userDto;
    }
    public static Users mappedToUser (UsersDto userDto, Users users){


        users.setName(userDto.getUserName());
        users.setGender(userDto.getGender());
        users.setEmailId(userDto.getEmailId());
        users.setMobileNumber(userDto.getMobileNumber());
        users.setDateOfBirth(userDto.getDateOfBirth());
        users.setAddress(userDto.getAddress());
        return users;
    }
}
