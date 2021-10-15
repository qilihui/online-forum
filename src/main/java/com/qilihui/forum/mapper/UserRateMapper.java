package com.qilihui.forum.mapper;

import com.qilihui.forum.pojo.UserRate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户积分Mapper
 */
@Component
public interface UserRateMapper {

    /**
     * 新增用户积分信息
     * @param userRate
     * @return
     */
    int insertUserRate(UserRate userRate);

    /**
     * 根据用户ID查询用户积分
     * @param userId
     * @return
     */
    UserRate selectRateById(Integer userId);

    /**
     * 查询积分最多的前十名用户信息
     * @return
     */
    List<UserRate> selectTopRateUser(int n);

    /**
     * 根据用户ID更新用户积分
     */
    int updateRateById(Integer userId, Integer rate);
}
