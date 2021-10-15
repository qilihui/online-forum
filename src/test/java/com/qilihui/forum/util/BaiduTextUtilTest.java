package com.qilihui.forum.util;

import com.qilihui.forum.ForumApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author qilihui
 * @date 2021/5/1 0:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class BaiduTextUtilTest {

    @Autowired
    private BaiduTextUtil baiduTextUtil;


    @Test
    public void getTextInfo() {
        baiduTextUtil.textIsNormal("大sb");
    }
}