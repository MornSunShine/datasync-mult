package com.maomorn.datasync;

/**
 * Author: MaoMorn
 * Date: 2017/9/25
 * Time: 8:40
 * Description: 工具类，一些同步过程中使用到的零散方法
 */
public class Tool {
    /**
     * 生成作业名，方便在日志中进行定位
     * @param length 标识名的长度
     * @return 生成的标识名
     */
    public static String generateString(int length){
        if(length < 1) length = 6;
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String genStr = "";
        for(int i = 0; i < length; i++)
        {
            genStr = genStr + str.charAt((int) ((Math.random() * 100) % 26));
        }
        return genStr;
    }
}
