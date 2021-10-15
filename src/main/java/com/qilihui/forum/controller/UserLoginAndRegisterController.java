package com.qilihui.forum.controller;

import cn.hutool.captcha.LineCaptcha;
import com.qilihui.forum.common.ForumResult;
import com.qilihui.forum.pojo.EmailInfo;
import com.qilihui.forum.pojo.User;
import com.qilihui.forum.pojo.UserInfo;
import com.qilihui.forum.service.EmailService;
import com.qilihui.forum.service.UserInfoService;
import com.qilihui.forum.service.UserService;
import com.qilihui.forum.util.LoginUtils;
import com.qilihui.forum.util.MD5Password;
import com.qilihui.forum.util.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

/**
 * 用户登录注册Controller
 */
@Controller
public class UserLoginAndRegisterController {

    private final UserService userService;

    private final EmailService emailService;

    private final UserInfoService userInfoService;

    @Autowired
    public UserLoginAndRegisterController(UserService userService, EmailService emailService, UserInfoService userInfoService) {
        this.userService = userService;
        this.emailService = emailService;
        this.userInfoService = userInfoService;
    }

    /**
     * 跳转到注册页面
     *
     * @return 页面
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 跳转到登录页面
     *
     * @return 页面
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 注册页面获取验证码图片
     *
     * @param response 响应
     * @param request  请求
     */
    @GetMapping("/getVerifyCode")
    public void getVerificationCode(HttpServletResponse response, HttpServletRequest request) {
        try {
            int width = 200;
            int height = 60;
            LineCaptcha lineCaptcha = VerifyCode.drawRandomText(width, height);
            request.getSession().setAttribute("verifyCode", lineCaptcha.getCode());
            response.setContentType("image/png");//必须设置响应内容类型为图片，否则前台不识别
            OutputStream os = response.getOutputStream(); //获取文件输出流
            lineCaptcha.write(os);
            os.flush();
            os.close();//关闭流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询该用户名是否已被注册
     *
     * @param username 用户名
     * @return 包装结果
     */
    @GetMapping("/getUserByName")
    @ResponseBody
    public ForumResult getUserByName(@RequestParam("username") String username) {
        //后端校验判空
        if (StringUtils.isEmpty(username)) {
            return new ForumResult(400, "用户名不能为空", null);
        }

        return userService.getUserByName(username) == null ? ForumResult.ok() : new ForumResult(500, "用户名已被注册", null);
    }

    /**
     * 用户注册按钮
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ForumResult register(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                @RequestParam("verifyCode") String verifyCode,
                                HttpServletRequest request) {

        //后端数据校验
        try {
            LoginUtils.isRight(username, password);
        } catch (Exception e) {
            return new ForumResult(400, e.getMessage(), null);
        }
        if (StringUtils.isEmpty(verifyCode))
            return new ForumResult(400, "验证码不能为空", null);

        //后端校验该用户名是否已经注册
        if (userService.getUserByName(username) != null)
            return new ForumResult(400, "该用户名已被注册", null);

        //放行，可以注册
        String code = (String) request.getSession().getAttribute("verifyCode");
        //验证码校验
        if (code.compareToIgnoreCase(verifyCode) != 0)
            return new ForumResult(405, "验证码错误", null);

        //插入用户信息表
        return userService.insertUser(username, password, Optional.empty()) > 0 ? ForumResult.ok() : new ForumResult(500, "注册失败，请稍后再试", username);
    }

    /**
     * 登录请求
     *
     * @param username 用户名
     * @param password 密码
     * @return json
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ForumResult loginUser(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("verifyCode") String verifyCode,
                                 HttpServletRequest request) {
        //后端校验用户名
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(verifyCode)) {
            return new ForumResult(500, "输入信息不能为空", null);
        }

        //判断用户名格式
        User user;
        String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

        //先判断是否为邮箱格式，如果不为邮箱格式，那么就是使用用户名登录的
        if (username.matches(regex)) {
            //使用邮箱登录的，先验证邮箱是否验证过
            EmailInfo emailInfo = emailService.selectEmailInfoByEmail(username);

            //邮箱是否存在
            if (emailInfo == null) return new ForumResult(400, "邮箱不存在", null);

            //邮箱是否已经被激活
            if (emailInfo.getCheck() == 0) return new ForumResult(500, "邮箱未验证", null);

            //再根据邮箱查询密码
            user = userService.getUserByUserId(emailInfo.getUid());
        } else {
            user = userService.getUserByName(username);
            if (user == null) return new ForumResult(500, "用户不存在", null);
            if (user.getDisable()) return new ForumResult(500, "用户被禁用", null);
        }

        //后端校验密码
        if (!MD5Password.verify(password, user.getPassword())) {
            return new ForumResult(500, "密码错误", null);
        }
        //校验验证码
        String code = (String) request.getSession().getAttribute("verifyCode");
        if (code.compareToIgnoreCase(verifyCode) != 0)
            return new ForumResult(500, "验证码错误", null);

        //将用户信息存在session中
        UserInfo userInfo = userInfoService.selectUserInfoByUid(user.getUid());
        LoginUtils.setSessionInfo(request, userInfo);
        return ForumResult.ok();
    }

    /**
     * 注销
     *
     * @param username 用户名
     * @param request  请求
     * @return 页面
     */
    @GetMapping("/logout")
    public String logout(@RequestParam("username") String username, HttpServletRequest request) {
        String name = (String) request.getSession().getAttribute("username");
        if (name != null) {
            request.getSession().setAttribute("username", null);
            request.getSession().removeAttribute("username");
            request.getSession().invalidate();
        }
        return "redirect:/";
    }
}
