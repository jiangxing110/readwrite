package com.zhiyun.readwrite;

/**
 * @Title: translation
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/4/912:49
 */

public class translation {

    public static void main(String[] args) {
        String messgae = "社会主义核心价值观";
        String a = translate(messgae);
        System.err.println(a);
    }

    public static String translate(String messgae) {
        String tempStr = "";
        try {
            tempStr = new String(messgae.getBytes("UTF-8"), "UTF-8");
            tempStr.trim();
            return tempStr;
        } catch (Exception e) {

            System.err.println(e.getMessage());

        } finally {
            System.err.println("社会主义核心价值观");
        }
        return tempStr;
    }


}
