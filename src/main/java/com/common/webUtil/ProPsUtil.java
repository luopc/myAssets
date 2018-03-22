package com.common.webUtil;

import com.common.util.CastUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class ProPsUtil {

    public static Properties loadProps(String fileName) {
        Properties properties = null;
        InputStream is = null;

        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + "file is not found");
            }
            properties = new Properties();
            properties.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is !=null){
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("close input stream failure",e);
                }
            }
        }
        return properties;
    }

    public static String getString(Properties props,String key){
        return  getString(props,key,"");
    }

    public static String getString(Properties props, String key, String defaultVale) {
        String value = defaultVale;
        if(props.containsKey(key)){
            value = props.getProperty(key);
        }
        return value;
    }

    public static int getInt(Properties props,String key){
        return  getInt(props,key,0);
    }

    public static int getInt(Properties props, String key, int defaultVale) {
        int value = defaultVale;
        if(props.containsKey(key)){
            value = CastUtil.castInt(props.getProperty(key));
        }
        return value;
    }

    public static boolean getBoolean(Properties props,String key){
        return  getBoolean(props,key,false);
    }

    public static boolean getBoolean(Properties props, String key, boolean defaultVale) {
        boolean value = defaultVale;
        if(props.containsKey(key)) value = CastUtil.castBoolean(props.getProperty(key));
        return value;
    }

}
