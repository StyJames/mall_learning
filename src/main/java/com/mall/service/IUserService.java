package com.mall.service;

import com.mall.pojo.*;
import com.mall.common.ServerResponse;

/**
 * 用户模块
 * Created by james on 2018/6/8.
 */
public interface IUserService {
    ServerResponse<User> login(String userName, String password);

    public ServerResponse<String> register(User user);

    public ServerResponse<String> checkValid(String str, String type);

    public ServerResponse<String> selectQuestion(String userName);

    public ServerResponse<String> checkAnswer(String userName, String question, String answer);

    public ServerResponse<String> forgetResetPassword(String userName, String passwordNew, String forgetToken);

    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    public ServerResponse<User> updateInformation(User user);

    public ServerResponse<User> getInformation(Integer userId);
}
