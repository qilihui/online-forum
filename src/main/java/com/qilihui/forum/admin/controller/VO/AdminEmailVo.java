package com.qilihui.forum.admin.controller.VO;

import lombok.Data;

/**
 * @author qilihui
 * @date 2021/4/27 21:06
 */
@Data
public class AdminEmailVo {

    private Integer eId;

    private Integer uid;

//    private String username;

    private String email;

    //是否验证，0-未验证，1-已验证
    private Integer check;

    private String username;
}
