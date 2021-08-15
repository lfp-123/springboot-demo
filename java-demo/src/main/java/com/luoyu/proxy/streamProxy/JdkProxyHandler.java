package com.luoyu.proxy.streamProxy;

import com.luoyu.proxy.staticProxy.Star;

import java.lang.reflect.Proxy;

/**
 * @author ${林锋鹏}
 * @Title: JdkProxyHandler
 * @ProjectName springboot-demo
 * @Description: TODO
 * @date 2021/8/11 23:49
 */
public class JdkProxyHandler  {
    private Object realStar;

    public JdkProxyHandler(Star star){
        this.realStar = star;
    }

    public Object getProxyInstance(){
        return Proxy.newProxyInstance(realStar.getClass().getClassLoader(),
                realStar.getClass().getInterfaces(),(proxy,method,args)->{
                    System.out.println("代理先进性谈判");
                    Object invoke = method.invoke(realStar, args);
                    System.out.println("演出完代理去收钱");
                    return invoke;
        });
    }


}
