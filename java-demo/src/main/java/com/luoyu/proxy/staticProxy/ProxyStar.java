package com.luoyu.proxy.staticProxy;

/**
 * @author ${林锋鹏}
 * @Title: ProxyStar
 * @ProjectName springboot-demo
 * @Description: TODO
 * @date 2021/8/11 23:43
 */
public class ProxyStar implements Star{
    private Star star;

    public ProxyStar(Star star){
        this.star = star;
    }

    @Override
    public void sing() {
        System.out.println("代理先进行谈判……");
        // 唱歌只能明星自己唱
        this.star.sing();
        System.out.println("演出完代理去收钱……");
    }
}
