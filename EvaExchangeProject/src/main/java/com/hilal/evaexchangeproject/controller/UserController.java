package com.hilal.evaexchangeproject.controller;



import com.hilal.evaexchangeproject.dto.UserDto;

import com.hilal.evaexchangeproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hilal.evaexchangeproject.constant.Endpoints.*;

@RestController
@RequestMapping(ROOT+USER)
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }
    @PostMapping(CREATEUSER)
    public ResponseEntity<String> saveUser(@RequestBody UserDto userDto) {
        String result = userService.saveUser(userDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("UPDATEUSER/{username}")
    public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody UserDto updatedUserDto) {
        String result = userService.updateUser(username, updatedUserDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("FINDUSER/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        UserDto userDto = userService.getUserByUsername(username);

        if (userDto != null) {
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(GETALLUSERS)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();

        if (!userDtos.isEmpty()) {
            return ResponseEntity.ok(userDtos);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    }


