package com.lfp.jdk8;

import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author ${林锋鹏}
 * @Title: MethodRefTest
 * @ProjectName springboot-demo
 * @Description: 方法引用
 * 1 使用情景 当要传递给Lambda体的操作，已经有实现的方法了,可以使用方法引用！
 * 2 方法引用 本质上是lambda表达式 而lambda表达式作为函数式接口的实例，所以方法引用，也是函数式接口的示例
 * 3 使用格式 类（或对象） ：： 实例方法
 * 4 具体分为如下三种情况
 *  对象:: 非静态方法
 *  类:: 静态方法
 *  类:: 非静态方法
 * 5 方法引用使用的要求，要求接口中的抽象方法的形参列表和返回值类型 与 方法引用的参数和返回值列表要一致
 * @date 2021/8/17 23:29
 */
public class MethodRefTest {
    //情况1 对象::非静态方法
    @Test
    public void test1(){
        Consumer<String> tConsumer = str -> System.out.println(str);
        tConsumer.accept("beijing");
        System.out.println("*************************");

        PrintStream out = System.out;
        Consumer<String> runnable = out::println;
        runnable.accept("nanjing");
    }

    @Test
    public void test2(){
        Employee employee = new Employee("beijing",1);
        employee.setAge(1);
        employee.setName("beijing");
       Supplier<Integer> supplier =  ()->employee.getAge();
        System.out.println(supplier.get());

        System.out.println("********************");

        Supplier<Integer> getAge = employee::getAge;
        System.out.println(getAge.get());
    }

    //情况2 ：类::静态方法
    //Comparator 中的int compara(T t1,T t2)
    //Integer中的int compare(T t1,T t2)
    public void test3(){
        Comparator<Integer> com1  = (t1,t2) ->Integer.compare(t1,t2);
        System.out.println(com1.compare(12,21));
        Comparator<Integer> compare = Integer::compare;
        System.out.println(compare.compare(12,14));

    }

    //Function中的R apply(T t)
    //Math中的Long round(Double d)
    @Test
    public void test4(){
        Function<Double,Long> func = new Function<Double, Long>() {
            @Override
            public Long apply(Double aDouble) {
                return Math.round(aDouble);
            }
        };
        System.out.println("**********************");
        Function<Double,Long> func1 = d -> Math.round(d);
        System.out.println("********************");


    }
     //情况3 类::实例方法
    //Comparator 中的int compara(T t1,Tt2)
    //String 中的int  t1.comparaTo(t2)
    @Test
    public void test5(){
        Comparator<String> com1 = (s1,s2) -> s1.compareTo(s2);
        System.out.println(com1.compare("acb","acb"));
        System.out.println("****************************");
        Comparator<String> comparator = String::compareTo;
        System.out.println(comparator.compare("abc","abc"));

    }

    //BigPredicate 中的boolean test(T t1,T t2);
    //String 中的Boolean t1.equals(t2)
    @Test
    public void test6(){

    }

    //Function中的R apply(T,t)
    //Employee中的String getName();
    //
    @Test
    public void test7(){
        Function<Employee,String> func = e ->e.getName();
        System.out.println(func.apply(new Employee("bei",1)));
        System.out.println("**********************");
        Function<Employee, String> getName = Employee::getName;
        String name = getName.apply(new Employee("name", 1));
        System.out.println(name);
    }
}
