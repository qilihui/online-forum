package com.qilihui.forum.admin.service;

import com.qilihui.forum.admin.mapper.AdminLoginMapper;
import com.qilihui.forum.admin.pojo.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 管理员Service
 */
@Service
public class AdminLoginService {

    @Autowired
    private AdminLoginMapper loginMapper;

    @Transactional
    public int insertLoginInfo(String ip,String info,String time){
        return loginMapper.insertLoginInfo(ip, info, time);
    }

    /**
     * 查询最近5次的登录的信息
     * @return
     */
    public List<LoginInfo> queryAllLoginInfo(){
        return loginMapper.queryAllLoginInfo();
    }
}
