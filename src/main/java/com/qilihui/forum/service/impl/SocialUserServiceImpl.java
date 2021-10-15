package com.qilihui.forum.service.impl;

import com.qilihui.forum.mapper.SocialUserDao;
import com.qilihui.forum.pojo.SocialUser;
import com.qilihui.forum.service.SocialUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qilihui
 * @date 2021/2/1 14:44
 */
@Service
public class SocialUserServiceImpl implements SocialUserService {
    private final SocialUserDao socialUserDao;

    @Autowired
    public SocialUserServiceImpl(SocialUserDao socialUserDao) {
        this.socialUserDao = socialUserDao;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return socialUserDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SocialUser record) {
        return socialUserDao.insert(record);
    }

    @Override
    public int insertSelective(SocialUser record) {
        return socialUserDao.insertSelective(record);
    }

    @Override
    public SocialUser selectByPrimaryKey(Integer id) {
        return socialUserDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SocialUser record) {
        return socialUserDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SocialUser record) {
        return socialUserDao.updateByPrimaryKey(record);
    }

    @Override
    public SocialUser selectBySourceUuid(String source, String uuid) {
        return socialUserDao.selectBySourceUuid(source, uuid);
    }

    @Override
    public int deleteByUid(Integer uId) {
        return socialUserDao.deleteByUid(uId);
    }

    @Override
    public int setDisable(Integer uid) {
        return socialUserDao.setDisable(uid);
    }

    @Override
    public int updateIsOpenByUid(Integer uid, Integer isOpen) {
        return socialUserDao.updateIsOpenByUid(uid, isOpen);
    }
}
