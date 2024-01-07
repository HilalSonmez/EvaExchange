package com.hilal.evaexchangeproject.service;


import com.hilal.evaexchangeproject.converter.UserConverter;
import com.hilal.evaexchangeproject.dto.UserDto;
import com.hilal.evaexchangeproject.entity.User;
import com.hilal.evaexchangeproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;



@Service
public class UserService{
    private final UserRepository userRepository;
     private final UserConverter userConverter;


    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userConverter.toDtoList(users);
    }

    public UserDto getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(userConverter::toDto).orElse(null);
    }

    public String saveUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.findByUsername(userDto.getUsername());

        if (existingUser.isPresent()) {
            return "User already exists with the provided username";
        }

        User user = userConverter.toEntity(userDto);
        userRepository.save(user);
        return "User saved successfully";
    }

    public String updateUser(String username, UserDto updatedUserDto) {
        Optional<User> existingUser = userRepository.findByUsername(username);

        if (existingUser.isPresent()) {
            User existingUserData = existingUser.get();
            User updatedUserData = userConverter.toEntity(updatedUserDto);

            existingUserData.setFirstName(updatedUserData.getFirstName());
            existingUserData.setLastName(updatedUserData.getLastName());


            userRepository.save(existingUserData);
            return "User information updated successfully";
        } else {
            return "User not found with the provided username";
        }
}}

