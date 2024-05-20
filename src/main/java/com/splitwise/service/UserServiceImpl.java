package com.splitwise.service;
import com.splitwise.dto.UserListDto;
import com.splitwise.dto.UsersDto;
import com.splitwise.entity.SecurityUser;
import com.splitwise.entity.Users;
import com.splitwise.exception.CustomerAlreadyExistsException;
import com.splitwise.exception.ResourceNotFoundException;
import com.splitwise.mapper.UsersListMapper;
import com.splitwise.mapper.UsersMapper;
import com.splitwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    public UsersDto getUserDetails(String userID) {
        boolean isUpdated = false;
        Users user = userRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User", "UserID", userID));
        UsersDto usersDto = UsersMapper.mappedToUserDTO(new UsersDto(), user);
        return usersDto;
    }
    public void createUser(Users user) {
        Optional<Users> optionalUsers = userRepository.findByMobileNumberOrEmailId(user.getMobileNumber(), user.getEmailId());
        if (optionalUsers.isPresent() == true && optionalUsers.get().getMobileNumber().equalsIgnoreCase(user.getMobileNumber())) {
            throw new CustomerAlreadyExistsException("User already registered with given mobileNumber : " + user.getMobileNumber());
        } else if (optionalUsers.isPresent() == true && optionalUsers.get().getEmailId().equalsIgnoreCase(user.getEmailId())) {
            throw new CustomerAlreadyExistsException("User already registered with given EmailId: " + user.getEmailId());
        }

        user.setAuthorities("ROLE_USER");
        userRepository.save(user);

    }

    public boolean updateUser(UsersDto userDto) {
        boolean isUpdated = false;
        Users existingUser = userRepository.findById(userDto.getEmailId()).orElseThrow(() -> new ResourceNotFoundException("User", "UserID", String.valueOf(userDto.getEmailId())));
        if (existingUser != null) {

            Users user = UsersMapper.mappedToUser(userDto, existingUser);
            userRepository.save(user);
            isUpdated = true;
        }
        return isUpdated;
    }

    public boolean deleteUser(String userID) {
        boolean isDeleted = false;
        Users existingUser = userRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User", "UserID", userID));
        if (existingUser != null) {
            userRepository.deleteById(userID);
            isDeleted = true;
        }
        return isDeleted;


    }
    public List<UserListDto> getAllUsers() {

        List<Users> users = (List<Users>) userRepository.findAll();
        List <UserListDto> userListDto = new ArrayList<UserListDto>();

        for (int i =0 ; i<users.size() ; i++)
        {
            Users user = users.get(i);
            UserListDto userDto= UsersListMapper.mappedToUserDTO(new UserListDto(),user);
            userListDto.add(userDto);
        }

        return userListDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println("beofre authentication username:"+username);
        Optional<Users> user = userRepository.findById(username);
        return user.map(SecurityUser::new).orElseThrow(() -> new ResourceNotFoundException("User", "UserID", username));
    }
}
