package com.splitwise.entity;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "user")
@Data
@RequiredArgsConstructor
public class Users {

    @Id
    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String emailId;

    @NotNull(message = "Name cannot be blank")
    @Size(min = 3, message = "Name be must be at least 3 characters long")
    private String name;

    @NotNull(message = "Gender cannot be blank")
    private String gender;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message="dateOfBirth must not be blank")
    private String dateOfBirth;

    @NotBlank(message="address must not be blank")
    private String address;

    @NotBlank(message="pwd must not be blank")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String pwd;

    private String authorities;

}
