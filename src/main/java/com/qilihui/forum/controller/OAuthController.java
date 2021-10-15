package com.qilihui.forum.controller;

import com.qilihui.forum.common.ForumResult;
import com.qilihui.forum.pojo.SocialUser;
import com.qilihui.forum.pojo.UserInfo;
import com.qilihui.forum.service.LoginService;
import com.qilihui.forum.service.SocialUserService;
import com.qilihui.forum.service.UserInfoService;
import com.qilihui.forum.service.UserService;
import com.qilihui.forum.util.LoginUtils;
import com.alibaba.fastjson.JSON;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author qilihui
 * @date 2021/2/1 12:02
 */
@Slf4j
@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OAuthController {
    private final AuthRequestFactory factory;

    private final UserService userService;
    private final SocialUserService socialUserService;
    private final UserInfoService userInfoService;
    private final LoginService loginService;

    @GetMapping
    @ResponseBody
    public List<String> list() {
        return factory.oauthList();
    }

    @GetMapping("/login/{type}")
    public void login(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = factory.get(type);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/callback/{type}")
    public String login(@PathVariable String type, AuthCallback callback, HttpServletRequest request, Model model) {
        AuthRequest authRequest = factory.get(type);
        AuthResponse<AuthUser> response = authRequest.login(callback);
        AuthUser authUser = response.getData();
        log.info("【response】= {}", JSON.toJSON(response));
        SocialUser socialUser = socialUserService.selectBySourceUuid(authUser.getSource(), authUser.getUuid());
        if (null == socialUser) {
            //注册新用户
            socialUser = SocialUser.builder().source(authUser.getSource()).uuid(authUser.getUuid()).accessToken(authUser.getToken().getAccessToken()).isOpen(0).build();
            String username = authUser.getSource() + authUser.getUuid();
            loginService.insertUser(username, null, Optional.of(authUser), socialUser);
        }
        //未修改用户名
        if (socialUser.getIsOpen().equals(0)) {
            log.info("未修改用户名");
            model.addAttribute("type", type);
            model.addAttribute("uid", socialUser.getUId());
            return "social-register";
        }
        //将用户信息存在session中
        UserInfo userInfo = userInfoService.selectUserInfoByUid(socialUser.getUId());
        if (userInfo.getDisable()) {
            model.addAttribute("info", "用户被禁用");
            return "error";
        }
        LoginUtils.setSessionInfo(request, userInfo);
        return "redirect:/";
    }

    @PostMapping("/register")
    @ResponseBody
    public ForumResult register(@RequestParam String username, @RequestParam String password, @RequestParam int uid, HttpServletRequest request) {
        log.info(username + " ", password + " " + uid);
        try {
            LoginUtils.isRight(username, password);
            if (null != userService.getUserByName(username)) {
                throw new Exception("用户名已存在");
            }
            loginService.updateSocialUserStatus(username, password, uid);
        } catch (Exception e) {
            return new ForumResult(400, e.getMessage(), null);
        }
        userInfoService.selectUserInfoByUid(uid);
        //将用户信息存在session中
        UserInfo userInfo = userInfoService.selectUserInfoByUid(uid);
        LoginUtils.setSessionInfo(request, userInfo);
        return ForumResult.ok();
    }
}
