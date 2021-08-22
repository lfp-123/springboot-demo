package com.lfp.jdk8;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.junit.Test;
import org.w3c.dom.ls.LSOutput;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * @author ${林锋鹏}
 * @Title: LambdaTest1
 * @ProjectName springboot-demo
 * @Description: 1举例 （01,02） - > Integer.compare(01,02);
 *     格式：
 *      1  :lambda操作符 或 箭头操作符
 *      2  左边：lambda形参列表 （其实就是接口中的抽象方法的形参）
 *      3  右边：lambda体（其实就是重写的抽象方法的方法体）
 *      Lambda 表达式的本质：作为接口的示例
 * @date 2021/8/15 17:17
 */

public class LambdaTest1 {
    @Test  //语法格式1 无参 无返回值
   public void test1(){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("我爱北京天安门");
            }
        };
        Runnable r1 = ()-> System.out.println("我爱北京天安门");
        runnable.run();
        r1.run();
    }
    @Test //语法格式2 需要一个参数 无返回值
    public void test2(){

        Consumer<String> stringConsumer = new Consumer<String>() {
            @Override
            public void accept(String o) {
                System.out.println(o);
            }
        };
        stringConsumer.accept("谎言和誓言的区别是什么");
        Consumer<String> con1 = (String s) -> {
            System.out.println(s);
        };
        con1.accept("一个是听的人当真了，一个是说的人当真了");

    }
    @Test //数据类型可以省略，因为可由编译器推断得出，称为 “类型推断”
    public void test3(){
        Consumer<String> con1 = (String s) -> {
            System.out.println(s);
        };
        con1.accept("一个是听的人当真了，一个是说的人当真了");
        Consumer<String> con2 = (s) -> {
            System.out.println(s);
        };
        con2.accept("一个是听的人当真了，一个是说的人当真了");
    }
    @Test //需要两个以上的参数，多条执行语句，并且可以有返回值
    public void test4(){
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                System.out.println(o1);
                System.out.println(o2);
                return o1.compareTo(o2);
            }
        };
        System.out.println(comparator.compare(12,21));
        Comparator<Integer> comparator1 = (o1, o2) ->{
            System.out.println(o1);
            System.out.println(o2);
            return o1.compareTo(o2);};

        System.out.println(comparator1.compare(12,21));
    }

    @Test //当Lambda体只有一条语句时，return与大括号若有则可以省略
    public void test5(){
        Comparator<Integer> comparator = (o1, o2) -> {
            return o1.compareTo(o2);
        };
        System.out.println(comparator.compare(12,6));

        System.out.println("*************************");
        Comparator<Integer> com2 = (o1, o2) -> o1.compareTo(o2);

        System.out.println(com2.compare(12,6));
    }

    @Test
    public void test6(){
        Consumer<String> con1 = s -> {
            System.out.println(s);
        };
        con1.accept("一个是听的人当真了，一个是说的人当真了");
        System.out.println("***************************");
        Consumer<String> con2 = System.out::println;
        con2.accept("一个是听的人当真了，一个是说的人当真了");
    }
    

}
