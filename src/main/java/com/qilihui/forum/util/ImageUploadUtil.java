package com.qilihui.forum.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;

/**
 * @author qilihui
 * @date 2021/4/20 12:04
 */
@Component
public class ImageUploadUtil {

    @Value("${forum.upload-url}")
    private String url;

    public String upload(File file) throws Exception {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file", file);

        String result = HttpUtil.post(url, paramMap);
        JSON parse = JSONUtil.parse(result);
        Integer code = parse.getByPath("status", Integer.class);
        if (200 != code) {
            throw new Exception(parse.getByPath("msg", String.class));
        }
        return parse.getByPath("data").toString();
    }
}
