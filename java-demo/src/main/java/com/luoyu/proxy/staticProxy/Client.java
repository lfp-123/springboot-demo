package com.luoyu.proxy.staticProxy;

/**
 * @author ${林锋鹏}
 * @Title: Client
 * @ProjectName springboot-demo
 * @Description: TODO
 * @date 2021/8/11 23:45
 */
public class Client {
    public static void main(String[] args) {
        RealStar realStar = new RealStar();
        ProxyStar proxyStar = new ProxyStar(realStar);
        proxyStar.sing();
    }
}
