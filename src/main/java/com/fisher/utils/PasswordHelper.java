package com.fisher.utils;

import com.fisher.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 加密类
 */
@Component
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private String algorithmName = "md5";
    private int hashIterations = 2;

    // 生成随机盐并加密密码
    public void encryptPassword(User user) {
        // 生成随机盐
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        // 加密后的密码
        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getUsername()+user.getSalt()),
                hashIterations).toHex();

        user.setPassword(newPassword);
    }

}
