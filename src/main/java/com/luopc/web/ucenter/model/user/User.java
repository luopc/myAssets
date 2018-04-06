package com.luopc.web.ucenter.model.user;

import lombok.*;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private String userId;

    private String userName;

    private String credits;

    private String password;

    private String lastVisit;

    private String lastIp;

    private String salt;
}
