package com.blogfirsttry.service;
import com.blogfirsttry.po.Blog;
import com.blogfirsttry.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    //根据id查询blog
    Blog getBlog(Long id);
//获取并转换，Markdown转换为HTML
    Blog getAndConvert(Long id);

//    Page<Blog> listBlog(Pageable pageable,BlogQuery blog);
//    分页查询
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Page<Blog> listBlog(Pageable pageable);

//    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Page<Blog> listBlog(String query,Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

//    Map<String,List<Blog>> archiveBlog();

//    Long countBlog();
//新增
    Blog saveBlog(Blog blog);
//修改
    Blog updateBlog(Long id,Blog blog);
//删除
    void deleteBlog(Long id);
}
