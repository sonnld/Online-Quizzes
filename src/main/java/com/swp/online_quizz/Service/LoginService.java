package com.swp.online_quizz.Service;

import com.swp.online_quizz.Dto.LoginDtoRequest;
import com.swp.online_quizz.Dto.LoginDtoResponse;
import com.swp.online_quizz.Dto.UserDtoRegister;
import com.swp.online_quizz.Dto.UserDtoResponse;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Mapper.UserMapper;
import com.swp.online_quizz.Repository.UserRepository;
import com.swp.online_quizz.Util.JwtTokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginService implements ILoginService{
    JwtTokenUtil jwtTokenUtil;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    @Override
    public LoginDtoResponse login(LoginDtoRequest loginDtoRequest) {
        User user = userRepository.findByUsername(loginDtoRequest.getUsername())
                .orElseThrow(()->{
                   return new RuntimeException("User does not exist!");
                });
        boolean isAuthorization = passwordEncoder.matches(loginDtoRequest.getPassword(), user.getPasswordHash());
        if(!isAuthorization){
            throw new RuntimeException("Username or password is incorrect!");
        }
        final int ONE_DAY_SECOND = 60*24*24;
        String accessToken = jwtTokenUtil.generateToken(UserMapper.toTokenPayLoad(user),ONE_DAY_SECOND);
        return LoginDtoResponse.builder()
                .accessToken(accessToken)
                .userDtoResponse(UserMapper.toUserDtoResponse(user))
                .build();
    }

    @Override
    public UserDtoResponse register(UserDtoRegister userDtoRegister) {
        User user = UserMapper.toUser(userDtoRegister);
        user.setPasswordHash(passwordEncoder.encode(userDtoRegister.getPassword()));
        user = userRepository.save(user);
        return UserMapper.toUserDtoResponse(user);
    }

    @Override
    public void rememberMe(LoginDtoRequest loginDtoRequest, String rememberMe, HttpServletResponse response) {
        Cookie user = new Cookie("user", loginDtoRequest.getUsername());
        Cookie pass = new Cookie("pass", loginDtoRequest.getPassword());
        Cookie r = new Cookie("r", rememberMe);
        if(r!=null){
            final int ONE_YEAR = 24*60*60*7;
            user.setMaxAge(ONE_YEAR);
            pass.setMaxAge(ONE_YEAR);
            r.setMaxAge(ONE_YEAR);
        }else{
            user.setMaxAge(0);
            pass.setMaxAge(0);
            r.setMaxAge(0);
        }
        response.addCookie(user);
        response.addCookie(pass);
        response.addCookie(r);
    }
}
