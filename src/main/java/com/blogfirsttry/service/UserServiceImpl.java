package com.blogfirsttry.service;
import com.blogfirsttry.dao.UserRepository;
import com.blogfirsttry.po.User;
//import com.blogfirsttry.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {//MD5加密
        User user = userRepository.findByUsernameAndPassword(username, DigestUtils.md5DigestAsHex(password.getBytes()));
        return user;
    }
}
