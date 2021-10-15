package com.qilihui.forum.pojo;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * tb_social_user
 */
@Data
@Builder
public class SocialUser implements Serializable {
    private Integer id;

    /**
     * 系统用户id
     */
    private Integer uId;

    /**
     * 第三方系统的唯一ID
     */
    private String uuid;

    /**
     * 第三方用户来源
     */
    private String source;

    /**
     * 用户的授权令牌
     */
    private String accessToken;

    /**
     * 是否创建用户了
     */
    private Integer isOpen;

    //是否被禁用
    private Boolean disable = false;

    private static final long serialVersionUID = 1L;
}