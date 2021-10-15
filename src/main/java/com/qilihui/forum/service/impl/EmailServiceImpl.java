package com.qilihui.forum.service.impl;

import com.qilihui.forum.admin.controller.VO.AdminEmailVo;
import com.qilihui.forum.mapper.EmailMapper;
import com.qilihui.forum.mapper.UserInfoMapper;
import com.qilihui.forum.pojo.EmailInfo;
import com.qilihui.forum.pojo.UserInfo;
import com.qilihui.forum.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮箱信息Service
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final EmailMapper emailMapper;

    private final UserInfoMapper userInfoMapper;

    @Autowired
    public EmailServiceImpl(EmailMapper emailMapper, UserInfoMapper userInfoMapper) {
        this.emailMapper = emailMapper;
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public int insertEmailInfo(EmailInfo emailInfo) {
        return emailMapper.insertEmailInfo(emailInfo);
    }

    @Override
    public EmailInfo selectEmailInfoByUsername(String username) {
        return emailMapper.selectEmailInfoByUid(userInfoMapper.selectUserInfoByName(username).getUId());
    }

    @Override
    public EmailInfo selectEmailInfoByUid(Integer uid) {
        return emailMapper.selectEmailInfoByUid(uid);
    }

    @Override
    public Integer selectEmailCheckStatusByUsername(String username) {
        return emailMapper.selectEmailCheckStatusByUid(userInfoMapper.selectUserInfoByName(username).getUId());
    }

    @Override
    @Transactional
    public int updateEmailByUsername(String email, String username) {
        return emailMapper.updateEmailByUid(email, userInfoMapper.selectUserInfoByName(username).getUId());
    }

    @Override
    @Transactional
    public int updateEmailStatusByEmail(Integer check, String email) {
        return emailMapper.updateEmailStatusByEmail(check, email);
    }

    /**
     * 查询所有验证的邮箱
     *
     * @return
     */
    @Override
    public List<String> queryAllEmails() {
        return emailMapper.queryAllEmails();
    }

    /**
     * 根据邮箱查询邮箱信息
     *
     * @param email
     * @return
     */
    @Override
    public EmailInfo selectEmailInfoByEmail(String email) {
        return emailMapper.selectEmailInfoByEmail(email);
    }

    @Override
    public List<AdminEmailVo> getAll() {
        List<EmailInfo> all = emailMapper.getAll();
        List<AdminEmailVo> list = new ArrayList<>();
        for (EmailInfo emailInfo : all) {
            Integer uid = emailInfo.getUid();
            UserInfo userInfo = userInfoMapper.selectUserInfoByUid(uid);
            if (userInfo == null) continue;
            AdminEmailVo vo = new AdminEmailVo();
            vo.setEmail(emailInfo.getEmail());
            vo.setCheck(emailInfo.getCheck());
            vo.setEId(emailInfo.getEId());
            vo.setUid(emailInfo.getUid());
            vo.setUsername(userInfo.getUsername());
            list.add(vo);
        }
        return list;
    }
}
