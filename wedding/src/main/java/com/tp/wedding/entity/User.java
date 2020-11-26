package com.tp.wedding.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String userId;
    private String userName;
    private String nickName;
    private String password;
    private Long mobile;
    private Integer sex;
    private Date createTime;
    private Date updateTime;
}
