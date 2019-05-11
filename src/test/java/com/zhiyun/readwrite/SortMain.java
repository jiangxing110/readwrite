package com.zhiyun.readwrite;/**
 * @Title: SortMain
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/4/915:09
 */

import java.util.Arrays;

/**
 * @Description: java 排序
 * @Param:排序的方法有：插入排序（直接插入排序、希尔排序）， 交换排序（冒泡排序、快速排序），
 * 选择排序（直接选择排序、堆排序），
 * 归并排序，
 * 分配排序（箱排序、基数排序）
 * @return:
 * @Author: jiangxing
 * @Date: 2019/4/9 15:09
 */
public class SortMain {

    public static void main(String[] args) {
        int[] array = {12, 1, 6, 5, 9, 4, 23, 8, 11, 15};
        System.err.println("直接插入排序" + insertSort(array));
        System.err.println("希尔排序" + sheelSort(array));
        System.err.println("简单选择排序" + selectSort(array));
        System.err.println("堆排序" + heapSort(array));
        System.err.println("冒泡排序" + bubbleSort(array));
        System.err.println("快速排序" + quickSort(array));
        System.err.println("归并排序" + mergeSort(array));
        System.err.println("基数排序" + baseSort(array));
    }

    /**
     * 直接插入排序:从1开始，1和0比较 0比1大的话1的位置插入0的值，0的位置插入1 的值
     */
    public static String insertSort(int[] a) {

        int len = a.length;//单独把数组长度拿出来，提高效率
        int insertNum;//要插入的数
        for (int i = 1; i < len; i++) {//因为第一次不用，所以从1开始
            insertNum = a[i];
            int j = i - 1;//序列元素个数
            while (j >= 0 && a[j] > insertNum) {//从后往前循环，将大于insertNum的数向后移动
                a[j + 1] = a[j];//元素向后移动
                j--;
            }
            a[j + 1] = insertNum;//找到位置，插入当前元素
        }
        return Arrays.toString(a);
    }

    /**
     * 希尔排序对于直接插入排序问题，数据量巨大时。
     * <p>
     * 将数的个数设为n，取奇数k=n/2，将下标差值为k的数分为一组，构成有序序列。
     * <p>
     * 再取k=k/2 ，将下标差值为k的书分为一组，构成有序序列。
     * <p>
     * 重复第二步，直到k=1执行简单插入排序。
     */
    public static String sheelSort(int[] a) {
        int len = a.length;
        while (len != 0) {
            len = len / 2;
            for (int i = 0; i < len; i++) {
                for (int j = i + len; j < a.length; j += len) {
                    int k = j - len;
                    int temp = a[j];
                    while (k >= 0 && a[k] > temp) {
                        a[k + len] = a[k];
                        k -= len;
                    }
                    a[k + len] = temp;
                }
            }
        }
        return Arrays.toString(a);
    }

    /**
     * 简单选择排序
     */
    public static String selectSort(int[] a) {
        int len = a.length;
        for (int i = 0; i < len; i++) {//循环次数
            int value = a[i];
            int position = i;
            for (int j = i + 1; j < len; j++) {//找到最小的值和位置
                if (a[j] < value) {
                    value = a[j];
                    position = j;
                }
            }
            a[position] = a[i];//进行交换
            a[i] = value;
        }
        return Arrays.toString(a);
    }

    /**
     * 堆排序
     */
    public static String heapSort(int[] a) {
        int len = a.length;
        //循环建堆
        for (int i = 0; i < len - 1; i++) {
            //建堆
            buildMaxHeap(a, len - 1 - i);
            //交换堆顶和最后一个元素
            swap(a, 0, len - 1 - i);
        }
        return Arrays.toString(a);
    }

    //交换方法
    private static void swap(int[] data, int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    //对data数组从0到lastIndex建大顶堆
    private static void buildMaxHeap(int[] data, int lastIndex) {
        //从lastIndex处节点（最后一个节点）的父节点开始
        for (int i = (lastIndex - 1) / 2; i >= 0; i--) {
            //k保存正在判断的节点
            int k = i;
            //如果当前k节点的子节点存在
            while (k * 2 + 1 <= lastIndex) {
                //k节点的左子节点的索引
                int biggerIndex = 2 * k + 1;
                //如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if (biggerIndex < lastIndex) {
                    //若果右子节点的值较大
                    if (data[biggerIndex] < data[biggerIndex + 1]) {
                        //biggerIndex总是记录较大子节点的索引
                        biggerIndex++;
                    }
                }
                //如果k节点的值小于其较大的子节点的值
                if (data[k] < data[biggerIndex]) {
                    //交换他们
                    swap(data, k, biggerIndex);
                    //将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    k = biggerIndex;
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 冒泡排序
     */
    public static String bubbleSort(int[] a) {
        int len = a.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - i - 1; j++) {//注意第二重循环的条件
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
        return Arrays.toString(a);
    }

    /**
     * 快速排序
     */
    public static String quickSort(int[] array) {
        return "";
    }

    /**
     * 归并排序
     */
    public static String mergeSort(int[] array) {
        return "";
    }

    /**
     * 基数排序
     */
    public static String baseSort(int[] array) {
        return "";
    }


}
