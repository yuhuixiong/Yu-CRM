package com.fisher.controller;

import com.fisher.annotation.ApiLog;
import com.fisher.web.Result;
import com.fisher.entity.User;
import com.fisher.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //-------------------------------------------------查询----------------------------------------------------

    @GetMapping("/user")
    @ApiLog
    public Result findUserList(){
        List<User> users = userService.selectByCondition(new User());
        return Result.success(users);
    }


    @GetMapping("/user/{id}")
    @ApiLog
    public Result findUserOne(@PathVariable("id") Long id){
        return Result.success(userService.selectByPrimaryKey(id));
    }


    @GetMapping("/user/search")
    @ApiLog
    public Result findUser(@RequestBody User user){
        return Result.success(userService.selectByCondition(user));
    }

    //-------------------------------------------------更新----------------------------------------------------

    @PutMapping("/user/{id}/updatePassword")
    @ApiLog
    public Result updatePassword(@PathVariable("id") Long id,@RequestBody String password){
        userService.updatePassword(id,password);
        return Result.success();
    }

}
