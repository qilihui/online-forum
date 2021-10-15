package com.qilihui.forum.service.impl;

import com.qilihui.forum.mapper.EmailMapper;
import com.qilihui.forum.mapper.UserInfoMapper;
import com.qilihui.forum.mapper.UserMapper;
import com.qilihui.forum.mapper.UserRateMapper;
import com.qilihui.forum.pojo.EmailInfo;
import com.qilihui.forum.pojo.User;
import com.qilihui.forum.pojo.UserInfo;
import com.qilihui.forum.pojo.UserRate;
import com.qilihui.forum.service.UserService;
import com.qilihui.forum.util.MD5Password;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 用户Service
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private EmailMapper emailMapper;

    @Autowired
    private UserRateMapper userRateMapper;

    /**
     * 获取所有用户信息
     *
     * @return
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = userMapper.getAllUsers();
        return users;
    }

    /**
     * 用户注册（事务）
     *
     * @param username 用户名
     * @param password 密码
     */
    @Override
    @Transactional
    public int insertUser(String username, String password, Optional<AuthUser> authUser) {

        //插入 用户名-密码 数据库中 密码加密
        User user = new User();
        user.setUsername(username);  //注册用户名
        user.setPassword(MD5Password.md5Password(password));
        userMapper.insertUser(user);

        //插入用户信息表中
        UserInfo userInfo = new UserInfo();
        userInfo.setUId(user.getUid());
        userInfo.setLoginName(username); //不可修改的登录用户名
        userInfo.setUsername(username);  //可以修改的用户昵称
        userInfo.setEmail(authUser.map(AuthUser::getEmail).orElse(null));
        userInfo.setAvatar(authUser.map(AuthUser::getAvatar).orElse("/static/images/default_avatar.jpg"));  //默认头像的地址
        userInfo.setCompany(authUser.map(AuthUser::getCompany).orElse(null));
//        userInfo.setAvatar("/static/images/headimg.jpg");  //默认头像的地址
        userInfo.setRegisterDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        userInfoMapper.insertSelective(userInfo);

        //插入邮箱表
        EmailInfo emailInfo = new EmailInfo();
        emailInfo.setUid(user.getUid());
        emailInfo.setEmail(authUser.map(AuthUser::getEmail).orElse(null));
        emailInfo.setCheck(0);
        emailMapper.insertEmailInfo(emailInfo);

        //新增用户等级信息
        UserRate userRate = new UserRate();
        userRate.setUserId(user.getUid());
        userRate.setRate(0);
        userRateMapper.insertUserRate(userRate);

        //注册成功
        return user.getUid();
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public User getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    /**
     * 修改密码
     *
     * @param username    用户名
     * @param newPassword 新密码
     * @return
     */
    @Override
    @Transactional
    public int updatePassword(String username, String oldPassword, String newPassword) {
        //数据校验
        User user = userMapper.getUserByName(username);
        if (!MD5Password.verify(oldPassword, user.getPassword())) {
            return -1;
        }
        //执行更新
        userMapper.updatePassword(username, MD5Password.md5Password(newPassword));
        return 1;
    }

    /**
     * 根据用户Id删除用户信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public int deleteUserById(Integer id) {
        return userMapper.deleteUserById(id);
    }

    /**
     * 设置为禁用
     * @return
     */
    @Override
    @Transactional
    public int setDisable(Integer uid){
        return userMapper.setDisable(uid);
    }

    /**
     * 根据用户ID查询用户
     *
     * @param userId
     * @return
     */
    @Override
    public User getUserByUserId(Integer userId) {
        return userMapper.getUserByUserId(userId);
    }

    @Override
    public int updateUsernamePassword(int uid, String username, String password) {
        return userMapper.updateUsernamePassword(uid, username, password);
    }
}
