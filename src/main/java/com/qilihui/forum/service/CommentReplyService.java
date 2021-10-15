package com.qilihui.forum.service;

import com.qilihui.forum.config.ContentNoLegalException;
import com.qilihui.forum.pojo.CommentReply;

import java.util.List;

public interface CommentReplyService {

    /**
     * 新增一条回复信息
     * @return
     */
    CommentReply insertCommentReply(Integer qId, String username, String content, Integer parentCommentId, String replyfor) throws ContentNoLegalException;

    /**
     * 根据评论ID查找楼中楼回复
     * @param commentId
     * @return
     */
    List<CommentReply> queryReplyByCommentId(Integer commentId);

    /**
     * 查询一个评论下所有回复数
     * @param parentCommentId
     * @return
     */
    int queryReplyNum(Integer parentCommentId);
}
