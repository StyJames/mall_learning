package com.mall.service.impl;

import com.mall.common.Const;
import com.mall.common.ServerResponse;
import com.mall.dao.UserMapper;
import com.mall.pojo.*;
import com.mall.service.IUserService;
import com.mall.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by james on 2018/6/8.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;


    /**
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public ServerResponse<User> login(String userName, String password) {
        int ResultCount = userMapper.checkUserName(userName);
        if (ResultCount == 0) {
            return ServerResponse.createByErrorMsg("用户名不存在");
        }

        //todo 密码登陆MD5
        String MD5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(userName, MD5Password);
        if (user == null) {
            return ServerResponse.createByErrorMsg("密码错误");
        }

        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccessMsg("登陆成功");
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public ServerResponse<String> register(User user) {

        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        validResponse =this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }


        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        int ResultCount = userMapper.insert(user);
        if (ResultCount == 0) {
            return ServerResponse.createByErrorMsg("注册失败");
        }
        return ServerResponse.createBySuccessMsg("注册成功");
    }

    /**
     *
     * @param str
     * @param type
     * @return
     */
    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(type)) {
            //开始校验
            if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUserName(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMsg("用户已存在");
                }
            }
            if (Const.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMsg("email已存在");
                }
            }
        } else {
            return ServerResponse.createByErrorMsg("参数错误");
        }
        return ServerResponse.createBySuccessMsg("校验成功");
    }
}
