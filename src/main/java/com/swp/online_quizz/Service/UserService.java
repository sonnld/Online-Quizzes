package com.swp.online_quizz.Service;


import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{
    @Autowired
    public UserRepository userRepository;
    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
