package com.mcy.springbootshiro;

import com.mcy.springbootshiro.entity.SysUser;
import com.mcy.springbootshiro.service.SysUserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootShiroApplicationTests {

	@Autowired
	private SysUserService userService;

	@Test
	void contextLoads() {
		SysUser user = userService.findByUsername("admin");
		String hashAlgorithName = "MD5";
		String password = "123456";
		//加密次数
		int hashIterations = 1024;
		ByteSource credentialsSalt = ByteSource.Util.bytes("admin");
		Object obj = new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations);
		System.out.println(obj);
		user.setPassword(obj.toString());
		userService.save(user);
	}

}
