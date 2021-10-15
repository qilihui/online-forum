package com.qilihui.forum.mapper;

import com.qilihui.forum.pojo.IndexImage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IndexImageDao {
    int deleteByPrimaryKey(Integer id);

    int insert(IndexImage record);

    int insertSelective(IndexImage record);

    IndexImage selectByPrimaryKey(Integer id);

    List<IndexImage> selectAll();

    int updateByPrimaryKeySelective(IndexImage record);

    int updateByPrimaryKey(IndexImage record);
}