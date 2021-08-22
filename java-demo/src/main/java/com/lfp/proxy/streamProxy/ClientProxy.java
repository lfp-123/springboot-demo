package com.lfp.proxy.streamProxy;

import com.lfp.proxy.staticProxy.RealStar;
import com.lfp.proxy.staticProxy.Star;

/**
 * @author ${林锋鹏}
 * @Title: ClientProxy
 * @ProjectName springboot-demo
 * @Description: TODO
 * @date 2021/8/11 23:54
 */
public class ClientProxy  {


    public static void main(String[] args) {

        RealStar realStar = new RealStar();

        Star proxyInstance = (Star) new JdkProxyHandler(realStar).getProxyInstance();
        proxyInstance.sing();
    }
}
