package com.qilihui.forum.controller;

import com.qilihui.forum.common.ForumResult;
import com.qilihui.forum.pojo.Categories;
import com.qilihui.forum.pojo.Tags;
import com.qilihui.forum.service.CategoryService;
import com.qilihui.forum.service.TagService;
import com.qilihui.forum.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 问题分类和标签Controller
 */
@Controller
public class CategoryAndTagController {

    private final CategoryService categoryService;

    private final TagService tagService;

    @Autowired
    public CategoryAndTagController(CategoryService categoryService, TagService tagService) {
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    /**
     * 获取问题分类信息
     *
     * @return 通用返回结果
     */
    @GetMapping("/getCategories")
    @ResponseBody
    public ForumResult getCategories() {
        List<Categories> categories = categoryService.getAllCategories();
        return new ForumResult(200, "查询成功", JsonUtils.objectToJson(categories));
    }

    /**
     * 获取标签信息
     *
     * @return  通用返回结果
     */
    @GetMapping("/getTags")
    @ResponseBody
    public ForumResult getTags() {
        List<Tags> tags = tagService.queryAllTagsForOrigin();
        return new ForumResult(200, "查询成功", JsonUtils.objectToJson(tags));
    }
}
