package com.mcy.springbootshiro.repository;

import com.mcy.springbootshiro.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleRepository  extends JpaRepository<SysRole, Integer>, JpaSpecificationExecutor<SysRole> {

}
