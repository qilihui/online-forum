package com.qilihui.forum.util;

import com.baidu.aip.contentcensor.AipContentCensor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author qilihui
 * @date 2021/4/30 23:44
 */
@Component
@Slf4j
public class BaiduTextUtil {

    @Value("${forum.baidu.app-id}")
    private String apiId;

    @Value("${forum.baidu.api-key}")
    private String apiKey;

    @Value("${forum.baidu.secret-key}")
    private String secretKey;

    private volatile AipContentCensor client = null;

    private final String CONCLUSIONTYPE = "conclusionType";
    private final int LEGAL = 1;
    private final int NO_LEGAL = 2;


    private AipContentCensor getClient() {
        if (client == null) {
            synchronized (this) {
                if (client == null) {
                    client = new AipContentCensor(apiId, apiKey, secretKey);
                }
            }
        }
        return client;
    }

    public boolean textIsNormal(String s) {
        JSONObject jsonObject = getClient().textCensorUserDefined(s);
        log.info("请求百度文本内容审核：{}", jsonObject.toString(2));
        int conclusionType = jsonObject.getInt(CONCLUSIONTYPE);
        if (NO_LEGAL == conclusionType) {
            return false;
        }
        return LEGAL == conclusionType;
    }
}
