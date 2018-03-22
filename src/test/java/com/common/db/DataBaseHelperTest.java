package com.common.db;

import com.luopc.web.ucenter.model.user.LoginLog;
import com.luopc.web.ucenter.model.user.User;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataBaseHelperTest {

    @Test
    public void getUserTable(){
        String userTableName = DatabaseHelper.getTableName(User.class);
        String loginLogTableName = DatabaseHelper.getTableName(LoginLog.class);
        System.out.println(userTableName);
        System.out.println(loginLogTableName);
    }

    @Test
    public void testInsert() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_id", UUID.randomUUID().toString().replace("-","").toUpperCase());
        map.put("user_name", "user_zs");
        DatabaseHelper.insertEntity(User.class, map);
    }

    @Test
    public void testUpdate() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_name", "user_zs");
        map.put("credits", "credits");
        map.put("pass_word", "123456");
        map.put("last_visit", new Date());
        DatabaseHelper.updateEntity(User.class, "876B0FEB941449418002C12F18E01409",map);
    }
}
