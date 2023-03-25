package com.example.springbootproject.dao;


import com.example.springbootproject.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface UserMapper {

    /**
     * 确认该邮箱是否已经注册
     * @param usrEmail
     * @return 返回值大于0 表示该邮箱已经注册
     */
    int confirmEmail(String usrEmail);

    /**
     * 根据用户输入的账号密码去查找数据库是否有此条记录
     * @param user
     * @return 返回查找到的用户对象
     */
    User userLogin(User user);

    /**
     * 获取所有用户
     * @return 返回所有用户的集合
     */
    ArrayList<User> selectAllUser();

    /**
     * 根据用户id删除用户
     * @param usrId
     * @return 返回成功删除的条数
     */
    int deleteUserByUsrId(String usrId);

    /**
     * 封禁用户 usrId
     * @param usrId
     * @return 返回封禁成功的条数
     */
    int banUserByUsrId(String usrId);

    /**
     * 根据用户id查找用户
     * @param usrId
     * @return 返回符合查找条件的用户
     */
    User selectByPrimaryKey(String usrId);

    /**
     * 注册用户接口
     * @param user
     * @return 返回值大于0 表示注册成功
     */
    int insertSelective(User user);

    /**
     * 根据用户id 可以有选择地更新表中的字段
     * @param user
     * @return 更新成功的条数
     */
    int updateByPrimaryKeySelective(User user);


    /**
     * 根据用户id 更新表中所有字段
     * @param user
     * @return 更新成功的条数
     */
    int updateByPrimaryKey(User user);

    /**
     * 查找想要申请成为审核员的用户 不包括注销用户和封禁用户
     * @param
     * @return 返回提出申请的用户集合
     */
    ArrayList<User> selectUserAsk();

    /**
     * 根据用户id更新用户的权限为审核员
     * @param usrId
     * @return 返回更新成功的个数
     */
    int updateUsrAuthority(String usrId);

    /**
     * 根据用户id更新用户的请求标志位
     * @param user
     * @return 更新成功的条数
     */
    int updateAskFlag(User user);
}