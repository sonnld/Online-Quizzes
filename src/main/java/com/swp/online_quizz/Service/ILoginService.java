package com.swp.online_quizz.Service;

import com.swp.online_quizz.Dto.LoginDtoRequest;
import com.swp.online_quizz.Dto.LoginDtoResponse;
import com.swp.online_quizz.Dto.UserDtoRegister;
import com.swp.online_quizz.Dto.UserDtoResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface ILoginService {
    LoginDtoResponse login(LoginDtoRequest loginDtoRequest);

    UserDtoResponse register(UserDtoRegister userDtoRegister);

    void rememberMe(LoginDtoRequest loginDtoRequest, String rememberMe, HttpServletResponse response);
}
