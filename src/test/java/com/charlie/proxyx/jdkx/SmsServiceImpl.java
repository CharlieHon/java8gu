package com.charlie.proxyx.jdkx;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/7 9:16
 * @Description: SmsServiceImpl
 */
public class SmsServiceImpl implements SmsService{
    @Override
    public String send(String message) {
        System.out.println(this.getClass().getSimpleName() + "$send(" + message + ")");
        return message;
    }
}
