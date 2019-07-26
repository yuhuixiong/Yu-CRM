package com.fisher.service.impl;

import com.fisher.entity.User;
import com.fisher.mapper.UserMapper;
import com.fisher.service.UserService;
import com.fisher.utils.PasswordHelper;
import com.fisher.web.exception.InputException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Autowired
    private PasswordHelper passwordHelper;


    @Override
    public User selectByPrimaryKey(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> selectByPrimaryKeys(Long[] ids) {
        return userMapper.selectByPrimaryKeys(ids);
    }

    @Override
    public List<User> selectByCondition(User user) {
        return userMapper.selectByCondition(user);
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    @Transactional
    public void update(User user) {
//        passwordHelper.encryptPassword(user);
        userMapper.update(user);
    }

    @Override
    @Transactional
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    @Transactional
    public void del(Long id) {
        userMapper.del(id);
    }

    @Override
    @Transactional
    public void updatePassword(Long id, String password) {
        try {
            User user = userMapper.selectByPrimaryKey(id);
            user.setPassword(password);
            passwordHelper.encryptPassword(user);
            userMapper.update(user);
        }catch (Exception e){
            throw new InputException("");
        }
    }
}
