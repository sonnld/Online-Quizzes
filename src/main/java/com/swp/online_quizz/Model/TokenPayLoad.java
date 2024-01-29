package com.swp.online_quizz.Model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenPayLoad {
    int userId;
    String userName;
    String email;
    String role;
}
