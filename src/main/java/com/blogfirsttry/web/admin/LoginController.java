package com.blogfirsttry.web.admin;

import com.blogfirsttry.po.User;
import com.blogfirsttry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

//登录控制器，打开登录页面用的
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        //调用方法检查账号
        if (user != null) {
            user.setPassword(null);//放置密码留在页面造成泄露
            session.setAttribute("user",user);
            //登录用户暂时储存在session
            return "admin/index";
        } else {//用户名密码不对
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";//重定向到登录页面
        }
    }
    //登出
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");//登出就清空session
        return "redirect:/admin";
    }
}
