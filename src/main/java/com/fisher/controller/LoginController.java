package com.fisher.controller;

import com.fisher.annotation.ApiLog;
import com.fisher.web.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    @PostMapping("/login")
    @ApiLog
    public Result login(@RequestBody Map<String,Object> map){
        Subject subject = SecurityUtils.getSubject();
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        subject.login(token);
        return Result.success("登陆成功  " + SecurityUtils.getSubject().getSession().getId().toString());
    }

    @GetMapping("/logout")
    @ApiLog
    public Result logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.success("登出成功");
    }

}
