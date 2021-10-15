package com.qilihui.forum.pojo;

import lombok.Data;

/**
 * 用户积分实体类
 */
@Data
public class UserRate {

    //自增主键
    private int id;

    //用户Id
    private int userId;

    //用户积分
    private int rate;
}
