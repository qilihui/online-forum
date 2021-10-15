package com.qilihui.forum.service;

import com.qilihui.forum.pojo.SocialUser;

/**
 * @author qilihui
 * @date 2021/2/1 14:44
 */
public interface SocialUserService {
    int deleteByPrimaryKey(Integer id);

    int insert(SocialUser record);

    int insertSelective(SocialUser record);

    SocialUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SocialUser record);

    int updateByPrimaryKey(SocialUser record);

    SocialUser selectBySourceUuid(String source, String uuid);

    int deleteByUid(Integer uId);

    int updateIsOpenByUid(Integer uid, Integer isOpen);

    int setDisable(Integer uid);
}
