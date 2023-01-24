package com.blogfirsttry.web;

import com.blogfirsttry.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {
    @GetMapping("/")
//    //添加@PathVariable 注解使参数能被接收//@PathVariable Integer id,@PathVariable  String name
    public String index() {
//        //int i = 9/0;//故意制造一个错误看是否显示错误页面
////        String blog = null;
////        if(blog == null){
////            throw new NotFoundException("博客不存在，请尝试访问其他博客");
////        }
        System.out.println("------index------");
        return "index";
    }
    @GetMapping("/blog")
    public String blog() {
        System.out.println("------blog------");
        return "blog";
    }
}
