package com.qilihui.forum.pojo.vo;

import lombok.Data;

@Data
public class UserRateVO {

    //用户ID
    private int uid;

    //用户名
    private String username;

    //用户头像地址
    private String avatar;

    //积分
    private int rate;
}
