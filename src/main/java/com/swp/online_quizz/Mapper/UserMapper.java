package com.swp.online_quizz.Mapper;

import com.swp.online_quizz.Dto.UserDtoRegister;
import com.swp.online_quizz.Dto.UserDtoResponse;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Model.TokenPayLoad;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static TokenPayLoad toTokenPayLoad(User user) {
        return TokenPayLoad.builder()
                .userId(user.getId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public static UserDtoResponse toUserDtoResponse(User user) {
        return UserDtoResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public static User toUser(UserDtoRegister userDtoRegister) {
        return User.builder()
                .username(userDtoRegister.getUsername())
                .email(userDtoRegister.getEmail())
                .role(userDtoRegister.getRole())
                .build();
    }
}
