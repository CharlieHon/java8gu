package com.charlie.proxyx.jdkx;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/5 9:43
 * @Description: 实现发送短信接口
 */
public class SmsServiceImpl implements SmsService {
    @Override
    public String send(String message) {
        System.out.println("send message: " + message);
        return message;
    }
}
