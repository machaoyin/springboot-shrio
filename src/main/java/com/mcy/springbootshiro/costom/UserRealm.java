package com.mcy.springbootshiro.costom;

import com.mcy.springbootshiro.entity.SysRole;
import com.mcy.springbootshiro.entity.SysUser;
import com.mcy.springbootshiro.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService userService;

    /**
     * 授权逻辑方法
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权");

        //获取当前登录对象
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser)subject.getPrincipal();
        if(user != null){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            // 角色与权限字符串集合
            Collection<String> rolesCollection = new HashSet<>();
           /* Collection<String> premissionCollection = new HashSet<>();*/

            //获得当前用户的角色
            List<SysRole> roles = user.getRoles();
            for(SysRole role : roles){
                rolesCollection.add(role.getName());
            }
            //添加当前用户的角色权限，用于判断可以访问那些功能
            info.addStringPermissions(rolesCollection);
            return info;
        }
        return null;
    }

    /**
     * 认证
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        System.out.println("执行认证");

        // 编写shiro判断逻辑，判断用户名和密码
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        // 判断用户名
        SysUser bean = userService.findByUsername(token.getUsername());

        //用户名不存在
        if(bean == null){
            throw new UnknownAccountException();
        }

        //以账号作为加密的盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(bean.getUsername());

        // 判断密码，
        return new SimpleAuthenticationInfo(bean, bean.getPassword(),
                credentialsSalt, getName());
    }

    //密码加密方法
    /*public static void main(String[] args){
        String hashAlgorithName = "MD5";
        String password = "123456";
        //加密次数
        int hashIterations = 1024;
        ByteSource credentialsSalt = ByteSource.Util.bytes("user");
        Object obj = new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations);
        System.out.println(obj);
    }*/
}
