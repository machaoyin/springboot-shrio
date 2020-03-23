package com.mcy.springbootshiro.web.controller;

import com.mcy.springbootshiro.entity.SysRole;
import com.mcy.springbootshiro.entity.SysUser;
import com.mcy.springbootshiro.service.SysRoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    private SysRoleService roleService;

    @RequestMapping({"/index", "/"})
    public String index(){
        return "login";
    }

    @RequestMapping(value = "/login")
    public String login(String username, String password, Model model){
        //1.获取subject
        Subject subject = SecurityUtils.getSubject();

        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            //3.执行登录方法
            subject.login(token);
            //登录成功
            return "redirect:/mian";
        } catch (UnknownAccountException e) {
            //e.printStackTrace();
            //登录失败：用户名不存在
            model.addAttribute("msg", "用户名不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            //e.printStackTrace();
            //登录失败：用户名不存在
            model.addAttribute("msg", "密码输入有误");
            return "login";
        }
    }

    @RequestMapping(value = "/mian")
    public String main(){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        System.out.println(user.getName());
        for(SysRole role: user.getRoles()){
            System.out.println(role.getName()+"=====角色");
            for(SysRole roles : role.getChildren()){
                System.out.println(roles.getName()+"===="+role.getName()+"角色对应的菜单");
            }
        }
        return "main";
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "redirect:/main";
    }

    @RequestMapping("/unAuth")
    public String unAuth(){
        return "unAuth";
    }

    @RequestMapping("/system")
    public String system(){
        return "system";
    }

    @RequestMapping("/user")
    public String user(){
        return "user";
    }

}
