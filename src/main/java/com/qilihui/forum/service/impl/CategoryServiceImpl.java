package com.qilihui.forum.service.impl;

import com.qilihui.forum.mapper.CategoryMapper;
import com.qilihui.forum.pojo.Categories;
import com.qilihui.forum.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类信息Service
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    /**
     * 查询带有默认标签的所有分类信息
     *
     * @return 分类信息
     */
    @Override
    public List<Categories> getAllCategories() {
        return categoryMapper.getAllCategories();
    }
}
