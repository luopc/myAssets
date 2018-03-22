package com.luopc.framework.common.utils;


import com.common.webUtil.ProPsUtil;
import com.luopc.framework.common.ConfigConstant;

import java.util.Properties;

public class ConfigHelper {

    private static final Properties CONFIG_PROPS = ProPsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * 获取 JDBC 驱动
     */
    public static String getJdbcDriver() {
        return ProPsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    /**
     * 获取 JDBC URL
     */
    public static String getJdbcUrl() {
        return ProPsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    /**
     * 获取 JDBC 用户名
     */
    public static String getJdbcUsername() {
        return ProPsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    /**
     * 获取 JDBC 密码
     */
    public static String getJdbcPassword() {
        return ProPsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取应用基础包名
     */
    public static String getAppBasePackage() {
        return ProPsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取应用 JSP 路径
     */
    public static String getAppJspPath() {
        return ProPsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH, "/pages/");
    }

    /**
     * 获取应用静态资源路径
     */
    public static String getAppAssetPath() {
        return ProPsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_ASSET_PATH, "/static/");
    }

    /**
     * 获取应用文件上传限制
     */
    public static int getAppUploadLimit() {
        return ProPsUtil.getInt(CONFIG_PROPS, ConfigConstant.APP_UPLOAD_LIMIT, 10);
    }

    /**
     * 根据属性名获取 String 类型的属性值
     */
    public static String getString(String key) {
        return ProPsUtil.getString(CONFIG_PROPS, key);
    }

    /**
     * 根据属性名获取 int 类型的属性值
     */
    public static int getInt(String key) {
        return ProPsUtil.getInt(CONFIG_PROPS, key);
    }

    /**
     * 根据属性名获取 boolean 类型的属性值
     */
    public static boolean getBoolean(String key) {
        return ProPsUtil.getBoolean(CONFIG_PROPS, key);
    }

}
