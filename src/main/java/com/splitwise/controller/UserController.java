package com.splitwise.controller;

import com.splitwise.constant.TaskConstants;
import com.splitwise.dto.ResponseDto;
import com.splitwise.dto.UserListDto;
import com.splitwise.dto.UsersDto;
import com.splitwise.entity.Users;
import com.splitwise.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path = "/split", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/fetchUser")
    public ResponseEntity<UsersDto> fetchUserDetails(@RequestParam String userID) {
        UsersDto userDetails = userService.getUserDetails(userID);
        return ResponseEntity.status(HttpStatus.OK).body(userDetails);
    }
    /*
    Creating user from authcontroller
    @PostMapping("/createUser")
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody Users user) {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(TaskConstants.STATUS_201, TaskConstants.MESSAGE_201));
    }*/
    @PutMapping("/updateUser")
    public ResponseEntity<ResponseDto> updateUser(@Valid @RequestBody UsersDto usersDto) {
        //System.out.println("User by me"+user.toString());
        boolean isUpdated = userService.updateUser(usersDto);
        if (isUpdated == true) {
            return ResponseEntity.status(HttpStatus.CREATED).
                    body(new ResponseDto(TaskConstants.STATUS_201, TaskConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ResponseDto(TaskConstants.STATUS_417, TaskConstants.MESSAGE_417_UPDATE));
        }
    }
    @DeleteMapping("/deleteUser")
    public ResponseEntity<ResponseDto> updateUser(@RequestParam String userID) {
        //System.out.println("User by me"+user.toString());
        boolean isDeleted = userService.deleteUser(userID);
        if (isDeleted == true) {
            return ResponseEntity.status(HttpStatus.CREATED).
                    body(new ResponseDto(TaskConstants.STATUS_201, TaskConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ResponseDto(TaskConstants.STATUS_417, TaskConstants.MESSAGE_417_DELETE));
        }
    }
    @GetMapping("/getUsers")
    public ResponseEntity<List<UserListDto>> fetchAllUserDetails() {
        List<UserListDto> userListDetails = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userListDetails);
    }
}
