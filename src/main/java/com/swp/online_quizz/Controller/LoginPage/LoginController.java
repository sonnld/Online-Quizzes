package com.swp.online_quizz.Controller.LoginPage;

import com.swp.online_quizz.Dto.LoginDtoRequest;
import com.swp.online_quizz.Dto.LoginDtoResponse;
import com.swp.online_quizz.Dto.UserDtoRegister;
import com.swp.online_quizz.Dto.UserDtoResponse;
import com.swp.online_quizz.Service.ILoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LoginController {
    ILoginService iLoginService;

    @GetMapping("/login")
    public String loginPage(){
        return "Login";
    }
//    @PostMapping("/login")
//    public LoginDtoResponse login(@ModelAttribute LoginDtoRequest loginDtoRequest, @RequestParam String rememberMe, HttpServletResponse response){
//        iLoginService.rememberMe(loginDtoRequest, rememberMe, response);
//        return iLoginService.login(loginDtoRequest);
//    }

    @GetMapping("/register")
    public String registerPage(){
        return "Register";
    }
    @PostMapping("/register")
    public UserDtoResponse register(@RequestBody UserDtoRegister userDtoRegister){
        return iLoginService.register(userDtoRegister);
    }
}
