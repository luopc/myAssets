package com.luopc.web.ucenter.model.user;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
public class LoginLog implements Serializable{

    private String loginLogId;

    private String userId;

    private String ip;

    private Date loginDate;

}
