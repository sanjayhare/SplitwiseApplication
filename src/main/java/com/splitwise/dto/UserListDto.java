package com.splitwise.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserListDto {


    @NotEmpty(message = "Name cannot be blank")
    @Size(min = 3, max = 50, message = "Name be must be at least 3 characters long")
    private String customerName;

    @NotEmpty(message = "location cannot be blank")
    private String location;

    @NotEmpty(message = "date cannot be blank")
    private String date;
}
