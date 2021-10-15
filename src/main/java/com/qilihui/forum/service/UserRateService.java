package com.qilihui.forum.service;

import com.qilihui.forum.pojo.UserRate;

import java.util.List;

public interface UserRateService {

    /**
     * 查询积分排行靠前的用户信息
     *
     * @return
     */
    List<UserRate> selectTopRateUserInfo(int n);

    /**
     * 根据用户ID查询积分
     *
     * @param userId
     * @return
     */
    UserRate selectRateById(Integer userId);
}
