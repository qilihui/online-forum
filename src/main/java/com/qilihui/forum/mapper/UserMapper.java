package com.qilihui.forum.mapper;

import com.qilihui.forum.pojo.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户Mapper
 */
@Component
public interface UserMapper {

    /**
     * 获取所有的用户
     *
     * @return list
     */
    List<User> getAllUsers();

    /**
     * 新增用户
     *
     * @param user user对象
     * @return
     */
    int insertUser(User user);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    User getUserByName(String username);

    /**
     * 修改密码
     *
     * @param username
     * @param newPassword
     * @return
     */
    int updatePassword(String username, String newPassword);

    /**
     * 根据用户Id删除用户信息
     *
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
     * 根据用户uid查询用户信息
     *
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
