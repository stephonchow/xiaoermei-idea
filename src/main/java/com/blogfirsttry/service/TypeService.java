package com.blogfirsttry.service;

import com.blogfirsttry.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//接口定义
public interface TypeService {
    Type saveType(Type type);
    Type getType(Long id);
    Type getTypeByName(String name);
    Page<Type> listType(Pageable pageable);
//    List<Type> listType();
    Type updateType(Long id,Type type);
    void deleteType(Long id);
}
