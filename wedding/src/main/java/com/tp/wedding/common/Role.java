package com.tp.wedding.common;

import com.sun.org.apache.bcel.internal.generic.RETURN;

public enum Role {
    ROOT(999,"超级管理员"),
    ADMIN(998,"普通管理员"),
    CUSTOMER(0,"普通用户"),
    ;

    private Integer value;
    private String desc;

    Role(Integer value,String desc){
        this.value = value;
        this.desc = desc;
    }

    public static Role getByIndex(Integer value){
        Role[] roles = Role.values();
        for(Role role:roles){
            if(role.getValue() == value){
                return role;
            }
        }
        return null;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
