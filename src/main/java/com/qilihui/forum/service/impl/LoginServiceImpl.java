package com.qilihui.forum.service.impl;

import com.qilihui.forum.pojo.SocialUser;
import com.qilihui.forum.pojo.UserInfo;
import com.qilihui.forum.service.LoginService;
import com.qilihui.forum.service.SocialUserService;
import com.qilihui.forum.service.UserInfoService;
import com.qilihui.forum.service.UserService;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.qilihui.forum.util.MD5Password.md5Password;

/**
 * @author qilihui
 * @date 2021/2/1 16:01
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LoginServiceImpl implements LoginService {
    private final UserService userService;
    private final SocialUserService socialUserService;
    private final UserInfoService userInfoService;

    @Override
    public int insertUser(String username, String password) {
        return userService.insertUser(username, password, Optional.empty());
    }

    @Override
    public int insertUser(String username, String password, Optional<AuthUser> authUser, SocialUser socialUser) {
        int uid = userService.insertUser(username, password, authUser);
        socialUser.setUId(uid);
        socialUserService.insert(socialUser);
        return uid;
    }

    @Override
    public int updateSocialUserStatus(String username, String password, int uid) throws Exception {
        userService.updateUsernamePassword(uid, username, md5Password(password));
        UserInfo userInfo = userInfoService.selectUserInfoByUid(uid);
        if (userInfo == null) {
            throw new Exception("未注册");
        }
        userInfo.setLoginName(username);
        userInfo.setUsername(username);
        userInfoService.updateUserInfoByUid(userInfo);
        socialUserService.updateIsOpenByUid(uid, 1);
        return uid;
    }
}
