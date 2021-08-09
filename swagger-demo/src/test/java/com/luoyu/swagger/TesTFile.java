package com.luoyu.swagger;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author ${林锋鹏}
 * @Title: TesTFile
 * @ProjectName springboot-demo
 * @Description: TODO
 * @date 2021/8/9 21:02
 */
public class TesTFile {


    /**
     * @descriptions 遍历目录时执行自定义操作
     * @author zhangfaquan
     * @date 2021/8/9 20:21
     * @param file 文件对象
     * @param consumer 自定义操作
     * @param predicate 判断条件
     * @return void
     */
    public static void treeWalk(File file, Consumer<File> consumer, Predicate<File> predicate) {

        Predicate<File> finalPredicate = predicate == null ? file1 -> true : predicate;
        File[] files = file.listFiles(fileTmp -> fileTmp.isDirectory() || finalPredicate.test(fileTmp));
        if (files == null)
            return;
        for (File item : files) {
            if (item.isDirectory())
                treeWalk(item, consumer, predicate);
            if (consumer != null)
                // 传入的都是文件，没有目录。
                consumer.accept(item);
        }
    }


    /**
     * @descriptions 遍历目录下的所有文件
     * @author zhangfaquan
     * @date 2021/8/9 20:19
     * @param file 文件对象
     * @return java.util.List<java.io.File>
     */
    public static List<File> getAllFile(File file) {
        List<File> fileList = new ArrayList<>();
        treeWalk(file, fileList::add);
        return fileList;
    }


    /**
     * @descriptions 遍历目录时执行自定义操作。
     * @author zhangfaquan
     * @date 2021/8/9 20:19
     * @param file 文件对象
     * @param consumer 自定义操作
     * @return void
     */
    public static void treeWalk(File file, Consumer<File> consumer) {
        treeWalk(file, consumer, null);
    }
    @Test
    public void testGetAllFile() {
        // 获取指定目录下的所有文件及目录
        File file = new File("D:\\笔记资料");
        List<File> fileList = getAllFile(file);
        System.out.println(fileList);
    }


}
