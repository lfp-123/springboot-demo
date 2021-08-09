package com.luoyu.swagger;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;
import java.util.function.Function;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class SwaggerApplicationTests {

    @BeforeEach
    void testBefore(){
        log.info("测试开始!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @AfterEach
    void testAfter(){
        log.info("测试结束!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }


    @Test
    public void test9() {

        TerminateOperation terminateOperation = new TerminateOperation();
        Class<? extends TerminateOperation> aClass = terminateOperation.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            System.out.println(method.getName()+":"+method.getReturnType().getSimpleName()); // testM:Object
        }

        String a = testM("a", s -> "a1");
        System.out.println(a);

        Cat cat = testM(new Animal(), animal -> new Cat());
        MinCat minCat = testM(new Animal(), animal -> new MinCat());

        Animal animal = testM(new Cat(), c -> new Animal());
        Animal animal1 = testM(new MinCat(), minCat1 -> new Animal());

        Cat cat1 = testM(new Dog(), dog -> new Cat());

        System.out.println("===========================");

        TerminateOperation terminateOperation1 = new TerminateOperation();
        Class<? extends TerminateOperation> aClass1 = terminateOperation1.getClass();
        Method[] declaredMethods1 = aClass1.getDeclaredMethods();
        for (Method method : declaredMethods1) {
            System.out.println(method.getName()+":"+method.getReturnType().getSimpleName()); // testM:Object
        }
    }



    // <R> Stream<R> map(Function<? super T, ? extends R> mapper);
    public <T, R> R testM(T t, Function<? super T, R> function) {

        return function.apply(t);
    }

    class Animal {

    }

    class Cat extends Animal{

    }

    class MinCat extends Cat{

    }

    class Dog {

    }
}
