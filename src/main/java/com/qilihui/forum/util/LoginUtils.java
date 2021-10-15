package com.qilihui.forum.util;

import com.qilihui.forum.pojo.UserInfo;
import com.qilihui.forum.pojo.vo.UserVO;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qilihui
 * @date 2021/2/1 15:51
 */
public class LoginUtils {
    public static void setSessionInfo(HttpServletRequest request, UserInfo userInfo) {
        request.getSession().setAttribute("username", userInfo.getUsername());
        request.getSession().setAttribute("uid", userInfo.getUId());

        //将所有页面需要的用户信息存储到session中，主要就是ID和用户名
        UserVO userVO = new UserVO();
        userVO.setUid(userInfo.getUId());
        userVO.setUsername(userInfo.getUsername());

        //将所有需要用到的用户信息放到session中
        request.getSession().setAttribute("loginUserInfo", userVO);
    }

    public static boolean isRight(String username, String password) throws Exception {
        if (StringUtils.isEmpty(username))
            throw new Exception("用户名不能为空");
        if (username.length() < 2 || username.length() > 12)
            throw new Exception("用户名必须在2-12个字符之内");
        if (StringUtils.isEmpty(password))
            throw new Exception("密码不能为空");


        //后端校验用户名格式是否正确，要求：仅包含中文、英文字母、数字，且数字不能在最前面
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
        if (!(username.matches(regex) && !Character.isDigit(username.toCharArray()[0]))) {
            throw new Exception("用户名必须由中文、英文字母或者数字组成，且数字不能在最前面");
        }
        return true;
    }
}
