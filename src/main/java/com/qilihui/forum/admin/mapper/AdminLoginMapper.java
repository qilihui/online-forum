package com.qilihui.forum.admin.mapper;

import com.qilihui.forum.admin.pojo.LoginInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 管理员登录Mapper
 */
@Repository
public interface AdminLoginMapper {

    /**
     * 新增一条登录信息
     * @param ip
     * @param info
     * @param time
     * @return
     */
    int insertLoginInfo(String ip, String info, String time);

    /**
     * 查询最近5次的登录信息
     * @return
     */
    List<LoginInfo> queryAllLoginInfo();
}
