package com.qilihui.forum.service;

import com.qilihui.forum.pojo.SocialUser;
import me.zhyd.oauth.model.AuthUser;

import java.util.Optional;

/**
 * @author qilihui
 * @date 2021/2/1 15:59
 */
public interface LoginService {
    int insertUser(String username, String password);

    int insertUser(String username, String password, Optional<AuthUser> authUser, SocialUser socialUser);

    int updateSocialUserStatus(String username, String password, int uid) throws Exception;
}
