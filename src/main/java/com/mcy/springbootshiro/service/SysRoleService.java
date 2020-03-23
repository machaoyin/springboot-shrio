package com.mcy.springbootshiro.service;

import com.mcy.springbootshiro.entity.SysRole;
import com.mcy.springbootshiro.repository.SysRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleService {

    @Autowired
    private SysRoleRepository roleRepository;

}
