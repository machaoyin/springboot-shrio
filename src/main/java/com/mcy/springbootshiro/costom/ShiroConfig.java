package com.mcy.springbootshiro.costom;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description Shiro配置类
 */
@Configuration
public class ShiroConfig {

    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数
        credentialsMatcher.setHashIterations(1024);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    @Bean("userRealm")
    public UserRealm userRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(matcher);
        return userRealm;
    }

    @Bean
    public ShiroFilterFactoryBean shirFilter(@Qualifier("securityManager")DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置 SecurityManager，安全管理器
        bean.setSecurityManager(securityManager);

        // 设置登录跳转页面
        bean.setLoginUrl("/index");
        // 设置未授权提示页面
        bean.setUnauthorizedUrl("/unAuth");
        /**
         * Shiro内置过滤器，可以实现拦截器相关的拦截器
         *    常用的过滤器：
         *      anon：无需认证（登录）可以访问
         *      authc：必须认证才可以访问
         *      user：如果使用rememberMe的功能可以直接访问
         *      perms：该资源必须得到资源权限才可以访问
         *      role：该资源必须得到角色权限才可以访问
         **/
        Map<String, String> filterMap = new LinkedHashMap<>();

        filterMap.put("/login","anon");
        filterMap.put("/user","authc");
        filterMap.put("/system","perms[system]");
        filterMap.put("/static/**","anon");

        filterMap.put("/**","authc");
        filterMap.put("/logout", "logout");

        bean.setFilterChainDefinitionMap(filterMap);
        return bean;
    }

    /**
     * 注入 securityManager
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(HashedCredentialsMatcher hashedCredentialsMatcher) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联realm.
        securityManager.setRealm(userRealm(hashedCredentialsMatcher));
        return securityManager;
    }
}