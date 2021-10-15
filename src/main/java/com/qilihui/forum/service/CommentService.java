package com.qilihui.forum.service;

import com.qilihui.forum.config.ContentNoLegalException;
import com.qilihui.forum.pojo.Comment;

import java.util.List;

public interface CommentService {

    /**
     * 插入一条评论
     * @param comment
     * @return
     */
    Comment insertCommentByQuestionId(String username, String comment, Integer questionId) throws ContentNoLegalException;

    /**
     * 根据问题ID查询评论信息
     * @param questionId
     * @return
     */
    List<Comment> queryCommentByQuestionId(Integer questionId);

    /**
     * 根据评论者查询评论信息
     */
    List<Comment> selectCommentsByUserId(Integer uid);

    /**
     * 根据评论ID查询评论信息
     * @param id
     * @return
     */
    Comment selectCommentByPrimaryKey(Integer id);
}
