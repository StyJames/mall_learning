package com.mall.service;

import com.mall.pojo.*;
import com.mall.common.ServerResponse;

/**
 * Created by james on 2018/6/8.
 */
public interface IUserService {
    ServerResponse<User> login(String userName, String password);

    public ServerResponse<String> register(User user);

    public ServerResponse<String> checkValid(String str, String type);
}
