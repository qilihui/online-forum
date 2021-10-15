package com.qilihui.forum.admin.service;

import com.qilihui.forum.admin.controller.VO.AdminTagsVo;
import com.qilihui.forum.admin.mapper.AdminTagsMapper;
import com.qilihui.forum.mapper.CategoryMapper;
import com.qilihui.forum.pojo.Categories;
import com.qilihui.forum.pojo.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qilihui
 * @date 2021/3/18 15:41
 */
@Service
public class AdminTagsService {
    private final AdminTagsMapper adminTagsMapper;
    private final CategoryMapper categoryMapper;

    @Autowired
    public AdminTagsService(AdminTagsMapper adminTagsMapper, CategoryMapper categoryMapper) {
        this.adminTagsMapper = adminTagsMapper;
        this.categoryMapper = categoryMapper;
    }

    public void setTag(int id, boolean is) {
        Tags tags = new Tags();
        tags.setId(id);
        tags.setIsOriginTag(is ? 1 : 0);
        adminTagsMapper.updateOriginSystemByPrimaryKey(tags);
    }

    public List<AdminTagsVo> getAllTag() {
        List<Tags> tags = adminTagsMapper.queryAll();
        List<Categories> categories = categoryMapper.getAllCategories();
        List<AdminTagsVo> voList = new ArrayList<>();
        Map<String, Categories> map = new HashMap<>();
        for (Categories category : categories) {
            map.put(String.valueOf(category.getId()), category);
        }
        for (Tags tag : tags) {
            AdminTagsVo vo = AdminTagsVo.builder()
                    .id(tag.getId())
                    .name(tag.getTagsName())
                    .categoryId(tag.getCategoryId())
                    .categoryName(map.get(String.valueOf(tag.getCategoryId()))
                            .getCategory())
                    .isSystemTag(tag.getIsOriginTag() == 1)
                    .build();
            voList.add(vo);
        }
        return voList;
    }
}
