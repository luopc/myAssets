package com.common.db;

import com.common.util.CollectionUtil;
import com.common.util.StringUtil;
import com.common.webUtil.ProPsUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class DatabaseHelper {

    private static final QueryRunner QUERY_RUNNER; //查询工具类
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL;//使用ThreadLocal来存放本地线程变量
    private static final BasicDataSource DATA_SOURCE;//池化数据库连接

//    private static final String DRIVER;
//    private static final String URL;
//    private static final String USERNAME;
//    private static final String PASSWORD;

    private static final String TABLE_PREFIX;

    static {
        CONNECTION_THREAD_LOCAL = new ThreadLocal<Connection>();
        QUERY_RUNNER = new QueryRunner();

        Properties conf = ProPsUtil.loadProps("jdbc.properties");
        String driver = conf.getProperty("jdbc.driver");
        String url = conf.getProperty("jdbc.url");
        String username = conf.getProperty("jdbc.username");
        String password = conf.getProperty("jdbc.password");
        TABLE_PREFIX = conf.getProperty("jdbc.table_prefix", "");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);

    }

    public static Map<String, Object> queryForMap(String sql, Object... params) {
        Map<String, Object> entity = null;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new MapHandler(), params);
        } catch (SQLException e) {
            log.error("query entityList failure ", e);
        } finally {
//            closeConnection();
        }
        return entity;
    }

    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity = null;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            log.error("query entityList failure ", e);
        } finally {
//            closeConnection();
        }
        return entity;
    }

    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList = null;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            log.error("query entityList failure ", e);
        } finally {
//            closeConnection();
        }
        return entityList;
    }

    public static List<Map<String, Object>> queryForListMap(String sql, Object... params) {
        List<Map<String, Object>> entityList = null;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            log.error("query entityList failure ", e);
        } finally {
//            closeConnection();
        }
        return entityList;
    }

    /**
     * 执行更新语句（包括：update、insert、delete）
     */
    public static int executeUpdate(String sql, Object... params) {
        int rows = 0;
        try {
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            log.error("execute update failure", e);
            throw new RuntimeException(e);
        }
        return rows;
    }

    /**
     * 插入实体
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            log.error("can not insert entity: fieldMap is empty");
            return false;
        }

        String sql = "INSERT INTO " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + " VALUES " + values;

        Object[] params = fieldMap.values().toArray();

        return executeUpdate(sql, params) == 1;
    }

    /**
     * 更新实体
     */
    public static <T> boolean updateEntity(Class<T> entityClass, String id, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            log.error("can not update entity: fieldMap is empty");
            return false;
        }

        String sql = "UPDATE " + getTableName(entityClass) + " SET ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(" = ?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE " + StringUtil.getUnderlineName(entityClass.getSimpleName()) + "_id = ?";

        List<Object> paramList = new ArrayList<Object>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();

        return executeUpdate(sql, params) == 1;
    }

    /**
     * 删除实体
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE " + StringUtil.getUnderlineName(entityClass.getSimpleName()) + "_id = ?";
        return executeUpdate(sql, id) == 1;
    }

    /**
     * 执行 SQL 文件
     */
    public static void executeSqlFile(String filePath) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String sql;
            while ((sql = reader.readLine()) != null) {
                executeUpdate(sql);
            }
        } catch (Exception e) {
            log.error("execute sql file failure", e);
            throw new RuntimeException(e);
        }
    }

    public static String getTableName(Class<?> entityClass) {
        String simpleName = entityClass.getSimpleName();

        return TABLE_PREFIX + StringUtil.getUnderlineName(simpleName);
    }

    public static Connection getConnection() {
        Connection conn = CONNECTION_THREAD_LOCAL.get();
        try {
            conn = DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            log.error("get connection failure ", e);
            throw new RuntimeException(e);
        } finally {
            CONNECTION_THREAD_LOCAL.set(conn);
        }
        return conn;
    }

//    public static void closeConnection() {
//        Connection conn = CONNECTION_THREAD_LOCAL.get();
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                log.error("close connection failure ", e);
//            } finally {
//                CONNECTION_THREAD_LOCAL.remove();
//            }
//        }
//    }

}
