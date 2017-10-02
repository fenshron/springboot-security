package com.us.example.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.us.example.dao.mapping.UserMapping;
import com.us.example.domain.SysUser;

@Service
public class TestService {

	@Autowired
	UserMapping usermapping;
	
	public SysUser getUserByName(String username){
		SysUser findByUserName = usermapping.findByUserName(username);
		return findByUserName;
	}
}
