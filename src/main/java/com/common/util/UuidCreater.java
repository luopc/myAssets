package com.common.util;

import java.util.UUID;


public class UuidCreater {
	
	public static String getUuid(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	 /**
     * 生成34位UUID
     * @param str String
     * @return String
     */
    public static String getUUID(String str) {
        if(str.length()!=2){
            throw new RuntimeException("输入的参数的位数必须为两位！");
        }
        return str+getUuid();
        
    }
}
