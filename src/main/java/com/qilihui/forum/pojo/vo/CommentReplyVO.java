package com.qilihui.forum.pojo.vo;

import com.qilihui.forum.pojo.CommentReply;
import lombok.Data;

@Data
public class CommentReplyVO extends CommentReply {

    //目标用户名
    private String touname;

    //用户头像
    private String rAvatar;

    //用户名
    private String rName;
}
