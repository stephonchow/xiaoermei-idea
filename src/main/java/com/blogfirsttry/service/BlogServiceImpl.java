package com.blogfirsttry.service;
import com.blogfirsttry.NotFoundException;
import com.blogfirsttry.dao.BlogRepository;
import com.blogfirsttry.po.Blog;
import com.blogfirsttry.po.Type;
import com.blogfirsttry.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }
//    @Transactional
//    @Override
//    public Blog getAndConvert(Long id) {
//        Blog blog = blogRepository.getOne(id);
//        if (blog == null) {
//            throw new NotFoundException("该博客不存在");
//        }
//        Blog b = new Blog();
//        BeanUtils.copyProperties(blog,b);
//        String content = b.getContent();
//        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
//
//        blogRepository.updateViews(id);
//        return b;
//    }


    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {

//        public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {//标题查询条件
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null) {//分类查询条件
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {//推荐查询条件
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                //where相当于SQL中的where语句
                cq.where(predicates.toArray(new Predicate[predicates.size()]));//将条件转换成数组
                return null;
            }
        },pageable);
    }

//    @Override
//    public Page<Blog> listBlog(Pageable pageable) {
//        return blogRepository.findAll(pageable);
//    }
//
//    @Override
//    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
//        return blogRepository.findAll(new Specification<Blog>() {
//            @Override
//            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
//                Join join = root.join("tags");
//                return cb.equal(join.get("id"),tagId);
//            }
//        },pageable);
//    }
//
//    @Override
//    public Page<Blog> listBlog(String query, Pageable pageable) {
//        return blogRepository.findByQuery(query,pageable);
//    }
//
//    @Override
//    public List<Blog> listRecommendBlogTop(Integer size) {
//        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
//        Pageable pageable = new PageRequest(0, size, sort);
//        return blogRepository.findTop(pageable);
//    }
//
//    @Override
//    public Map<String, List<Blog>> archiveBlog() {
//        List<String> years = blogRepository.findGroupYear();
//        Map<String, List<Blog>> map = new HashMap<>();
//        for (String year : years) {
//            map.put(year, blogRepository.findByYear(year));
//        }
//        return map;
//    }
//
//    @Override
//    public Long countBlog() {
//        return blogRepository.count();
//    }
//

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.getOne(id);
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(b,blog);
//        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
