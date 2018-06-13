package com.mall.dao;

import com.mall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUserName(String userName);

    int checkEmail(String email);

    User selectLogin(@Param("userName") String userName, @Param("password") String password);

    String selectQuestionByUserName(String userName);

    int checkAnswer(@Param("userName") String userName, @Param("question") String question,@Param("answer") String answer);
}