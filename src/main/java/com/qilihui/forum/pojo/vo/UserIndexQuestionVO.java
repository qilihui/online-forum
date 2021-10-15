package com.qilihui.forum.pojo.vo;

import com.qilihui.forum.pojo.Question;
import lombok.Data;

@Data
public class UserIndexQuestionVO extends Question {
    //问题ID
    private Integer id;

    //标题
    private String title;

    //发布时间
    private String gmtCreate;

    //评论数
    private Integer commentCount;

    //浏览数
    private Integer viewCount;

    //点赞数
    private Integer likeCount;

    //内容
    private String description;

    private String publisher;
}
