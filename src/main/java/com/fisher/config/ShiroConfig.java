package com.fisher.config;

import com.fisher.web.shiro.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 */
@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 自定义拦截器
        Map<String, Filter> filter = new HashMap<String, Filter>();
        filter.put("custom",new ShiroUserFilter());
        shiroFilterFactoryBean.setFilters(filter);

        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 过滤器链配置
        /**
         * anon: 无需认证
         * authc: 必须认证
         * user: 使用rememberMe可以访问
         * perms: 该资源必须得到资源权限才可以访问
         * role: 改资源必须得到角色权限才可以访问
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/**","custom");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 凭证匹配器
     */
    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher getCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    /**
     * 创建 Realm
     */
    @Bean(name="userRealm")
    public UserRealm getRealm(@Qualifier("credentialsMatcher")CredentialsMatcher credentialsMatcher
                              ){
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher);
        // 启用缓存
        userRealm.setCachingEnabled(true);
        // 启用身份验证缓存
        userRealm.setAuthenticationCachingEnabled(true);
        // 设置缓存 AuthenticationInfo 信息的缓存名称
        userRealm.setAuthenticationCacheName("authenticationCache");
        // 启用授权缓存
        userRealm.setAuthorizationCachingEnabled(true);
        // 设置缓存 AuthorizationInfo 信息的缓存名称
        userRealm.setAuthorizationCacheName("authorizationCache");
//        userRealm.setCacheManager(shiroRedisCacheManager);
        return userRealm;
    }


    /**
     * 创建 SecurityManager
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("userRealm") UserRealm userRealm,
                                                     @Qualifier("defaultWebSessionManager") DefaultWebSessionManager defaultWebSessionManager,
                                                     @Qualifier("shiroRedisCacheManager") ShiroRedisCacheManager shiroRedisCacheManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 关联 realm
        securityManager.setRealm(userRealm);
        // 关联 session manager
        securityManager.setSessionManager(defaultWebSessionManager);
        // 关联缓存管理器
        securityManager.setCacheManager(shiroRedisCacheManager);
        return securityManager;
    }

    @Bean(name = "shiroRedisCacheManager")
    public ShiroRedisCacheManager shiroRedisCacheManager(@Qualifier("shiroRedisCache") ShiroRedisCache shiroRedisCache){
        ShiroRedisCacheManager shiroRedisCacheManager = new ShiroRedisCacheManager(shiroRedisCache);
        return shiroRedisCacheManager;
    }

    @Bean(name = "shiroRedisCache")
    public ShiroRedisCache shiroRedisCache(RedisTemplate redisTemplate){
        ShiroRedisCache shiroRedisCache = new ShiroRedisCache(redisTemplate);
        return shiroRedisCache;
    }

    /**
     * 创建 SessionManager
     * @return
     */
    @Bean(name = "defaultWebSessionManager")
    public DefaultWebSessionManager defaultSessionManager(@Qualifier("shiroSessionDao") ShiroSessionDao shiroSessionDao,
                                                          @Value("${web.sessionTimeout}")Long sessionTimeout,
                                                          @Value("${web.sessionValidationInterval}")Long sessionValidationInterval){
        DefaultWebSessionManager defaultSessionManager = new ShiroSessionManager();
        // session 超时时间
        defaultSessionManager.setGlobalSessionTimeout(sessionTimeout);
        // 超时删除 session
        defaultSessionManager.setDeleteInvalidSessions(true);
        // session 验证检查
        defaultSessionManager.setSessionValidationSchedulerEnabled(true);
        // 每半小时验证一次
        defaultSessionManager.setSessionValidationInterval(sessionValidationInterval);
        // 配置 SessionDao
        defaultSessionManager.setSessionDAO(shiroSessionDao);
//        defaultSessionManager
        return defaultSessionManager;
    }

    /**
     * 创建 SessionDao
     * @return
     */
    @Bean(name = "shiroSessionDao")
    public ShiroSessionDao shiroSessionDao(RedisTemplate redisTemplate){
        ShiroSessionDao shiroSessionDao = new ShiroSessionDao(redisTemplate);
        return shiroSessionDao;
    }


    /**
     * 开启shiro aop 注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
