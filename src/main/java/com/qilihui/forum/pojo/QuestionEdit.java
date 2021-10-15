package com.qilihui.forum.pojo;

import lombok.Data;

/**
 * 问题置顶和加精实体类
 */
@Data
public class QuestionEdit {

    //自增主键
    private int id;

    //问题ID
    private int questionId;

    //提问者ID
    private int userId;

    //是否加精，0-否，1-是
    private int isJing;

    //是否制定，0-否，1-是
    private int isDing;
}
