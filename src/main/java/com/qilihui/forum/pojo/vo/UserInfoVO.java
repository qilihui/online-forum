package com.qilihui.forum.pojo.vo;

import com.qilihui.forum.pojo.UserInfo;
import lombok.Data;

@Data
public class UserInfoVO extends UserInfo {

    //用户Id
    private Integer uid;

    //用户名
    private String username;

    //所在地
    private String area;

    //头像地址
    private String avatar;

    //学校
    private String university;

    //公司
    private String company;

    //职位
    private String jobTitle;

    //发起的问题数
    private Integer questionNum;
}
