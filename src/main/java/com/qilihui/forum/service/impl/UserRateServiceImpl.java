package com.qilihui.forum.service.impl;

import com.qilihui.forum.mapper.UserRateMapper;
import com.qilihui.forum.pojo.UserRate;
import com.qilihui.forum.service.UserRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户积分service
 */
@Service
public class UserRateServiceImpl implements UserRateService {

    @Autowired
    private UserRateMapper userRateMapper;

    /**
     * 查询积分排名靠前的用户
     * @return
     */
    @Override
    public List<UserRate> selectTopRateUserInfo(int n) {
        return userRateMapper.selectTopRateUser(n);
    }

    /**
     * 根据用户ID查询积分
     * @param userId
     * @return
     */
    @Override
    public UserRate selectRateById(Integer userId) {
        return userRateMapper.selectRateById(userId);
    }
}
