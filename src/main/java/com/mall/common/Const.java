package com.mall.common;

import org.apache.commons.lang3.ClassUtils;

/**
 * Created by james on 2018/6/9.
 */
public class Const {
    public static final String CURRENT_USER = "currentUser";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public interface Role {
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 0; //管理员
    }

}
