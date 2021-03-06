package com.qilihui.forum.service.impl;

import com.qilihui.forum.mapper.FollowMapper;
import com.qilihui.forum.mapper.UserInfoMapper;
import com.qilihui.forum.pojo.Follow;
import com.qilihui.forum.pojo.UserInfo;
import com.qilihui.forum.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 关注与粉丝Service
 */
@Service
public class FollowServiceImpl implements FollowService {

    private final FollowMapper followMapper;

    private final UserInfoMapper userInfoMapper;

    @Autowired
    public FollowServiceImpl(FollowMapper followMapper, UserInfoMapper userInfoMapper) {
        this.followMapper = followMapper;
        this.userInfoMapper = userInfoMapper;
    }

    /**
     * 新增一条关注信息
     * @param follow 关注者
     * @return  更新行数
     */
    @Override
    @Transactional
    public int insertFollow(Follow follow) {
        followMapper.insertFollow(follow);
        return 1;
    }

    /**
     * 根据关注者的用户名和被关注者的用户名查询关注信息
     * @param userName  用户名
     * @param followName  关注用户名
     * @return  关注信息
     */
    @Override
    public Follow selectFollowByUserNameAndFollowName(String userName, String followName) {
        UserInfo userInfo = userInfoMapper.selectUserInfoByName(userName);
        UserInfo userInfo1 = userInfoMapper.selectUserInfoByName(followName);
        return followMapper.selectFollowByUserIdAndFollowId(userInfo.getUId(), userInfo1.getUId());
    }

    /**
     * 根据主键改变关注状态
     * @param status  关注状态
     * @param id  主键ID
     * @return 更新行数
     */
    @Override
    @Transactional
    public int updateFollowStatusById(int status, Integer id) {
        return followMapper.updateFollowStatusById(status, id);
    }

    /**
     * 根据用户名查询关注的人
     * @param username 用户名
     * @return 关注信息
     */
    @Override
    public List<Follow> selectFollowByUsername(String username) {
        UserInfo userInfo = userInfoMapper.selectUserInfoByName(username);
        return followMapper.selectFollowByFollowId(userInfo.getUId());
    }

    /**
     * 根据用户名查询关注我的人
     * @param username 用户名
     * @return 关注信息
     */
    @Override
    public List<Follow> selectFansByUsername(String username) {
        UserInfo userInfo = userInfoMapper.selectUserInfoByName(username);
        return followMapper.selectFansByUid(userInfo.getUId());
    }
}
