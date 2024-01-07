package com.hilal.evaexchangeproject.converter;



import com.hilal.evaexchangeproject.dto.UserDto;
import com.hilal.evaexchangeproject.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {


    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()

                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .build();
    }

    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());

        return user;
    }

    public List<UserDto> toDtoList(List<User> users) {
        return users.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<User> toEntityList(List<UserDto> userDtos) {
        return userDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
