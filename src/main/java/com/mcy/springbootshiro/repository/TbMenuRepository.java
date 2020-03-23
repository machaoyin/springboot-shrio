package com.mcy.springbootshiro.repository;

import com.mcy.springbootshiro.entity.SysUser;
import com.mcy.springbootshiro.entity.TbMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TbMenuRepository extends JpaRepository<TbMenu, Integer>, JpaSpecificationExecutor<TbMenu> {

}
