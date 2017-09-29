package com.maomorn.datasync;

/**
 * Author: MaoMorn
 * Date: 2017/9/25
 * Time: 8:40
 * Description: 生成随机job标识名，由大写字母组成，最短6位
 */
public class Tool {
    public static String generateString(int length){
        if(length < 1) length = 6;
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String genStr = "";
        for(int i = 0; i < length; i++)
        {
            genStr = genStr + str.charAt((int) ((Math.random() * 100) % 26));
        }
//        System.out.println(genStr);
        return genStr;
    }
}
