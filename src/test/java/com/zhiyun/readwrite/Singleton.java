package com.zhiyun.readwrite;

/**
 * @Title: Singleton
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/4/911:22
 */

public class Singleton {
    /*  懒汉模式 非线程安全
        private static Singleton instance=null;

        public Singleton() {
        }

        public static Singleton getInstance() {
            if (instance==null){
                instance=new Singleton();
            }
            return instance;
        }*/
    /* 线程安全懒汉
   private static Singleton instance = null;

    public Singleton() {
    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }*/
    /* 双重校验懒汉
    private static Singleton instance = null;

    public Singleton() {
    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                instance = new Singleton();
            }
        }
        return instance;
    }*/
    /* 饿汉模式
    private static Singleton instance = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return instance;
    }*/
   /*静态内部类 static nested class
   private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }
    private Singleton (){}
    public static final Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }*/
}
