package com.qilihui.forum.mapper;

import com.qilihui.forum.pojo.QuestionDing;
import org.springframework.stereotype.Component;

@Component
public interface QuestionDingDao {
    int deleteByPrimaryKey(Integer id);

    int insert(QuestionDing record);

    int insertSelective(QuestionDing record);

    QuestionDing selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuestionDing record);

    int updateByPrimaryKey(QuestionDing record);

    QuestionDing selectOneByQid(Integer qid);
}