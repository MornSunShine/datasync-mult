package com.maomorn.datasync;

import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/9/30
 * Time: 14:13
 * Description:
 */
public class Test {

    public static void main(String[] args){
        TestBean test=new TestBean();
        Field[] fields=test.getClass().getDeclaredFields();
//        for (int i = 0; i < fields.length; i++) {
//            System.out.println(fields[i].getName());
//        }
    }
}
