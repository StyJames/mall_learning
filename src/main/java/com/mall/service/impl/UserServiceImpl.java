package com.mall.service.impl;

import com.mall.common.ServerResponse;
import com.mall.dao.UserMapper;
import com.mall.pojo.*;
import com.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by james on 2018/6/8.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;



    @Override
    public ServerResponse<User> login(String userName, String password) {
        int ResultCount = userMapper.checkUserName(userName);

        if (ResultCount == 0) {
            return ServerResponse.createByErrorMsg("用户名不存在");
        }

        //todo 密码登陆MD5

        User user = userMapper.selectLogin(userName, password);
        if (user == null) {
            return ServerResponse.createByErrorMsg("密码错误");
        }

        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccessMsg("登陆成功");
    }
}
