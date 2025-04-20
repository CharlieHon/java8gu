package com.charlie.proxyx.cglibx;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/7 9:20
 * @Description: AliService
 */
public class AliService {
    public String send(String message) {
        System.out.println(this.getClass().getSimpleName() + "$send(" + message + ")");
        return message;
    }
}
