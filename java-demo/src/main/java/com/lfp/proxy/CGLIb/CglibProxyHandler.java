package com.lfp.proxy.CGLIb;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author ${林锋鹏}
 * @Title: CglibProxyHandler
 * @ProjectName springboot-demo
 * @Description: TODO
 * @date 2021/8/12 0:16
 */
public class CglibProxyHandler implements MethodInterceptor {
    private Object object;

    public Object getProxyInstance(final Object target){
        this.object = target;
        //Enhancer 类CGLIb中的字节码增强器，它可以方便对你的类进行拓展。
        Enhancer enhancer = new Enhancer();
        // 将被代理的对象设置成父类
        enhancer.setSuperclass(this.object.getClass());
        //回调方法，设置拦截器
        enhancer.setCallback(this);
        // 动态创建一个代理类
        return enhancer.create();
    }


// 使用 CGLIB 需要实现 MethodInterceptor 接口，并重写intercept 方法，
// 在该方法中对原始要执行的方法前后做增强处理。该类的代理对象可以使用代码中的字节码增强器来获取。

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("代理先进行谈判 ");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("演出完代理去收钱");
        return result;
    }
}
