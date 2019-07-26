package com.fisher.mapper;

import com.fisher.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    User selectByPrimaryKey(Long id);

    List<User> selectByPrimaryKeys(Long[] ids);

    /**
     * 模糊查询
     * @param user
     * @return
     */
    List<User> selectByCondition (User user);

    User selectByUsername(String username);

    void update (User user);

    void insert (User user);

    void del (Long id);



}
