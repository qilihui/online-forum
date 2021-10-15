package com.qilihui.forum.service;

import com.qilihui.forum.pojo.User;
import me.zhyd.oauth.model.AuthUser;

import java.util.List;
import java.util.Optional;

/**
 * 用户Service
 */
public interface UserService {

    /**
     * 获取所有用户信息
     * @return
     */
    List<User> getAllUsers();

    /**
     * 新增用户信息
     * @param username 用户名
     * @param password 密码
     * @param authUser 第三方应用注册需要传递的参数
     */
    int insertUser(String username, String password, Optional<AuthUser> authUser);

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    User getUserByName(String username);

    /**
     * 修改密码
     * @param username
     * @param oldPassword
     * @param newPassword
     * @return
     */
    int updatePassword(String username, String oldPassword, String newPassword);

    /**
     * 根据用户Id删除用户信息
     * @param id
     * @return
     */
    int deleteUserById(Integer id);

    /**
     * 设置为禁用
     * @return
     */
    int setDisable(Integer uid);

    /**
     * 根据用户ID查询用户
     * @param userId
     * @return
     */
    User getUserByUserId(Integer userId);

    /**
     * 第三方登录后需要修改用户名和密码
     *
     * @param uid
     * @param username
     * @param password
     * @return
     */
    int updateUsernamePassword(int uid, String username, String password);

}
