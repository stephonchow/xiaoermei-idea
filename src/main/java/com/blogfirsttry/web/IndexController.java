package com.blogfirsttry.web;

import com.blogfirsttry.NotFoundException;
import com.blogfirsttry.service.BlogService;
import com.blogfirsttry.service.TagService;
import com.blogfirsttry.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @GetMapping("/")
//    //添加@PathVariable 注解使参数能被接收//@PathVariable Integer id,@PathVariable  String name
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
//        //int i = 9/0;//故意制造一个错误看是否显示错误页面
////        String blog = null;
////        if(blog == null){
////            throw new NotFoundException("博客不存在，请尝试访问其他博客");
////        }
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        System.out.println("------index------");
        return "index";
    }
    @GetMapping("/blog")
    public String blog() {
        System.out.println("------blog------");
        return "blog";
    }
//搜索功能
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
//        query加上 两个 “%” 表示模糊查询
        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);//保存输入框内容
        return "search";
    }

//    博客详情
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
//        model.addAttribute("blog", blogService.getBlog(id));
        return "blog";
    }
}
