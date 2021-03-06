package com.qilihui.forum.service;

import com.qilihui.forum.pojo.Categories;

import java.util.List;

public interface CategoryService {

    /**
     * 查询所有分类信息
     *
     * @return 分类信息
     */
    List<Categories> getAllCategories();
}
