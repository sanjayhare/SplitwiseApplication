package com.splitwise.mapper;


import com.splitwise.dto.UserListDto;
import com.splitwise.entity.Users;

public class UsersListMapper {
    public static UserListDto mappedToUserDTO (UserListDto userListDto, Users users){


        userListDto.setCustomerName(users.getName());
        userListDto.setDate(users.getDateOfBirth());
        userListDto.setLocation(users.getAddress());
        return userListDto;
    }

}
