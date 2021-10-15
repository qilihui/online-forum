package com.qilihui.forum.admin.mapper;

import com.qilihui.forum.pojo.Tags;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminTagsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Tags record);

    int insertSelective(Tags record);

    Tags selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tags record);

    int updateByPrimaryKey(Tags record);

    List<Tags> queryAll();

    void updateOriginSystemByPrimaryKey(Tags record);
}