package com.qilihui.forum.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.qilihui.forum.util.MD5Password;
import me.zhyd.oauth.model.AuthUser;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author qilihui
 * @date 2021/2/1 13:12
 */
public class UserServiceImplTest {

    @Test
    public void insertUser() {
        AuthUser authUser = AuthUser.builder().avatar("teststafsdfs").build();
        Optional<AuthUser> authUserOptional = Optional.of(authUser);
//        Optional<AuthUser> authUserOptional = Optional.empty();
        System.out.println(authUserOptional.map(AuthUser::getAvatar).orElse("/static/images/headimg.jpg"));
    }

    @Test
    public void test1() {
        System.out.println(UUID.fastUUID());
        System.out.println(MD5Password.md5Password("qilihui"));
    }

//    public String upload(File file) throws Exception {
//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("file", file);
//
//        String result = HttpUtil.post("http://abiao.me:6688/upload", paramMap);
//        JSON parse = JSONUtil.parse(result);
//        Integer code = parse.getByPath("status", Integer.class);
//        if (200 != code) {
//            throw new Exception(parse.getByPath("msg", String.class));
//        }
//        return parse.getByPath("data").toString();
//    }
//
//    @Test
//    public void test2() {
//        try {
//            String upload = upload(FileUtil.file("C:\\Users\\Tom\\Desktop\\test.jpg"));
//            System.out.println(upload);
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
}