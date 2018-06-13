package com.mall.controller.portal;

import com.mall.common.Const;
import com.mall.common.ServerResponse;
import com.mall.pojo.User;
import com.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * Created by james on 2018/6/8.
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 登陆
     * @param userName
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String userName, String password, HttpSession session) {
        //Service->mybatis->dao
        ServerResponse<User> response = iUserService.login(userName, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 登出
     * @param session
     * @return
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    /**
     * 验证
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "checkValid.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    /**
     * 获取用户信息
     * @param session
     * @return
     */
    @RequestMapping(value = "getUserInfo.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMsg("用户未登录，无法获取当前用户信息");
    }

    /**
     * 获取密码提示问题
     * @param userName
     * @return
     */
    @RequestMapping(value = "forgetGetQuestion.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String userName) {
        return iUserService.selectQuestion(userName);
    }

    /**
     * 检查问题答案
     * @param userName
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "forgetCheckAnswer.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String userName, String question, String answer) {
            return iUserService.checkAnswer(userName, question, answer);
    }

    /**
     * 根据密码提示问题重置密码
     * @param userName
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @RequestMapping(value = "forgetResetPassword.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String userName, String passwordNew, String forgetToken) {
        return iUserService.forgetResetPassword(userName, passwordNew, forgetToken);
    }

    /**
     *登陆状态下重置密码
     * @param session
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @RequestMapping(value = "resetPassword.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session, String passwordOld, String passwordNew) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            return ServerResponse.createByErrorMsg("用户未登陆");
        }
        return iUserService.resetPassword(passwordOld, passwordNew, user);
    }

}
