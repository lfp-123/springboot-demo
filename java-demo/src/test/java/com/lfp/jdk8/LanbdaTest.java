package com.lfp.jdk8;

import java.util.Comparator;

/**
 * @author ${林锋鹏}
 * @Title: LanbdaTest
 * @ProjectName springboot-demo
 * @Description:
 * @date 2021/8/15 17:08
 */
public class LanbdaTest {

    public static void main(String[] args) {
        Comparator<Integer> comparable = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1,Integer o2) {
                return Integer.compare(o1,o2);
            }
        };

        int compare = comparable.compare(12, 21);
        System.out.println(compare);
        System.out.println("********************************");
        Comparator<Integer> comparator1 = (o1, o2) -> Integer.compare(o1,o2);
        int compare1 = comparator1.compare(12, 21);
        System.out.println(compare1);
        System.out.println("********************************");
        Comparator<Integer> comparator2 = Integer::compare;
        int compare2 = comparator2.compare(12, 21);
        System.out.println(compare2);

    }
}
