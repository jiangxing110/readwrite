package com.zhiyun.readwrite;

import jdk.nashorn.internal.objects.annotations.Where;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @Title: tryThrow
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/4/914:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class tryThrow {
    @Test
    public void test1() {

        int[] a = {5, 1, 6, 2, 3, 9};
        for (int i = 1; i < a.length; i++) {

            for (int j = 0; j < a.length - 1; j++) {
                if (a[j] > a[i]) {
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }

        System.err.println(Arrays.toString(a));
    }

    @Test
    public void test2() {

        int[] a = {5, 1, 6, 2, 3, 9};

        System.err.println(Arrays.toString(a));
    }
}
