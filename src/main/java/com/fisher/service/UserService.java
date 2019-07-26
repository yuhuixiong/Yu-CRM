package com.fisher.service;

import com.fisher.entity.User;
import com.fisher.web.exception.InputException;

import java.util.List;

public interface UserService {

    public User selectByPrimaryKey(Long id) ;

    public List<User> selectByPrimaryKeys(Long[] ids);

    public List<User> selectByCondition (User user);

    public User selectByUsername (String username);

    public void update (User user);

    public void insert (User user);

    public void del (Long id);

    public void updatePassword(Long id,String password) throws InputException;

}
