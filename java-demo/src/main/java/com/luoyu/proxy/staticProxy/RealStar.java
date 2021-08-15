package com.luoyu.proxy.staticProxy;

/**
 * @author ${林锋鹏}
 * @Title: RealStar
 * @ProjectName springboot-demo
 * @Description: TODO
 * @date 2021/8/11 23:42
 */
public class RealStar implements Star {
    @Override
    public void sing() {
        System.out.println("明星本人开始唱歌....");
    }
}
