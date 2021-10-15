package com.qilihui.forum.mapper;

import com.qilihui.forum.pojo.SocialUser;
import org.springframework.stereotype.Component;

@Component
public interface SocialUserDao {
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