package com.qilihui.forum.util;

/*对图片进行处理的类和方法*/

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;

import java.awt.*;

/**
 * 后台生成验证码
 */
public class VerifyCode {

    public static LineCaptcha drawRandomText(int width, int height) {
        //定义图形验证码的长和宽
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(width, height);
//        Font font= new Font("Time New Roman", Font.BOLD,60);
//        lineCaptcha.setFont(font);
        lineCaptcha.setGenerator(randomGenerator);
        return lineCaptcha;
    }
}
