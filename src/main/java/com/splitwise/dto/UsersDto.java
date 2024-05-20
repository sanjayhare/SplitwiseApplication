package com.splitwise.dto;
import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class UsersDto {

    @NotEmpty(message = "Name cannot be blank")
    @Size(min = 3, max = 50, message = "Name be must be at least 3 characters long")
    private String userName;

    @NotEmpty(message = "Gender cannot be blank")
    private String gender;

    @NotEmpty(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String emailId;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message="dateOfBirth must not be blank")
    private String dateOfBirth;

    @NotBlank(message="address must not be blank")
    private String address;
}
