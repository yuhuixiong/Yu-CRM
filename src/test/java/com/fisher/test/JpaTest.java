package com.fisher.test;

import com.fisher.entity.User;
import com.fisher.mapper.UserMapper;
import com.fisher.service.UserService;
import com.fisher.utils.PasswordHelper;
import com.fisher.utils.IdWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.fisher.mapper")
public class JpaTest {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    private UserService userService;

    @Test
    public void getId(){
        for(int i=0;i<10;i++){
            System.out.println(idWorker.nextId());
        }
    }

    @Test
    public void testInsert() throws Exception {
        User user = new User();
        System.out.println(user);
        user.setId(idWorker.nextId());
        user.setUsername("admin5");
        user.setPassword("123456");
        passwordHelper.encryptPassword(user);
        userService.insert(user);
    }

    @Test
    public void testUpdate() throws Exception{
        User user = new User();
        user.setId(1153136376527527936L);
        userService.update(user);
    }

    @Test
    public void testDelete() throws Exception{
        userService.del(1153121761609424896L);
    }

    @Test
    public void testFindAll() throws Exception{
        User user = new User();
        user.setLocked("1");
        List<User> users = userService.selectByCondition(user);
        for(User u : users){
            System.out.println(u);
        }
    }

    @Test
    public void testFindKeys() throws Exception{
//        List<Long> ids = new ArrayList<Long>();
//        ids.add(1151774013543002112L);
//        ids.add(1153136376527527936L);
        Long[] ids = {1151774013543002112L,1153136376527527936L};
        List<User> users = userService.selectByPrimaryKeys(ids);
        for(User u : users){
            System.out.println(u);
        }
    }

}
