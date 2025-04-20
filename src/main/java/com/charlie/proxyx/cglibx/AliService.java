package com.charlie.proxyx.cglibx;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/5 9:54
 * @Description: 实现一个使用阿里云发送短信的类
 */
public class AliService {
    public String send(String message) {
        System.out.println("send message: " + message);
        return message;
    }
}
