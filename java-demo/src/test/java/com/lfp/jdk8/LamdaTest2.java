package com.lfp.jdk8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author ${林锋鹏}
 * @Title: LamdaTest2
 * @ProjectName springboot-demo
 * @Description: java 内置的四大核心函数式接口
 *
 * 消费性接口 Consumer<T> void accept(T t)
 * 供给型接口  Supplier<T>  T get()
 * 函数型接口 Function<T,R> R apply<t1,t2>
 * 断定型接口 Predicate<T> boolean test<T t>
 * @date 2021/8/17 23:01
 */
public class LamdaTest2 {

    @Test
    public void test1(){
        happyTime(500, aDouble -> System.out.println("学习太累了，买了瓶水，价格为："+aDouble));
    }


    public void happyTime(double money, Consumer<Double> con){
        con.accept(money);
    }

    @Test
    public void test2(){
        List<String> strings = Arrays.asList("beijing", "nanjing", "dongjing", "anhui", "shandong");
        List<String> jing = filterString(strings, new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("jing");
            }
        });
      //  List<String> jing = filterString(strings, s -> s.contains("jing"));
        System.out.println(jing);

    }

    public List<String>  filterString (List<String> list, Predicate<String> pre){
        ArrayList<String> strings = new ArrayList<>();
        for (String s : list) {
            if (pre.test(s)){
                strings.add(s);
            }
        }
        return strings;
    }
}
