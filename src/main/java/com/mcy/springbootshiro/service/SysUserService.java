package com.mcy.springbootshiro.service;

import com.mcy.springbootshiro.entity.SysUser;
import com.mcy.springbootshiro.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserService {

    @Autowired
    private SysUserRepository userRepository;

    public SysUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(SysUser user) {
        userRepository.save(user);
    }
}
