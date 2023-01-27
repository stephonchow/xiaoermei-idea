package com.blogfirsttry.service;

import com.blogfirsttry.po.User;

//接口，检查用户登录的，实现在UserServiceImpl
public interface UserService {
    User checkUser(String username, String password);
}
