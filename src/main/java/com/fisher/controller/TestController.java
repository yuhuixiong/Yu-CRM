package com.fisher.controller;

import com.fisher.annotation.ApiLog;
import com.fisher.web.Result;
import com.fisher.entity.User;
import com.fisher.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@RestController
//@RequestMapping("")
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    @ApiLog
    public Result findUserList(){
        List<User> users = userService.selectByCondition(new User());
        return Result.success(users);
    }

    @GetMapping("/user/query/{id}")
    @ApiLog
    public Result findUserOne(@PathVariable("id") Long id){
        return Result.success(userService.selectByPrimaryKey(id));
    }

    @PostMapping("/user/query")
    @ApiLog
    public Result findUser(@RequestBody Map<String,Object> map){
        User user = new User();
        user.setUsername((String) map.get("username"));
        return Result.success(userService.selectByCondition(user));
    }


}
