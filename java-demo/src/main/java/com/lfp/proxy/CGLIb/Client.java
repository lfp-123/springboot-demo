package com.lfp.proxy.CGLIb;

import com.lfp.proxy.staticProxy.RealStar;
import com.lfp.proxy.staticProxy.Star;

/**
 * @author ${林锋鹏}
 * @Title: Client
 * @ProjectName springboot-demo
 * @Description: TODO
 * @date 2021/8/12 0:23
 */
public class Client {
    public static void main(String[] args) {

        RealStar realStar = new RealStar();
        Star proxyInstance =(Star) new CglibProxyHandler().getProxyInstance(realStar);
        proxyInstance.sing();
    }
}
