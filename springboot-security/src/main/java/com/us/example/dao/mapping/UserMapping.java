package com.us.example.dao.mapping;

import com.us.example.domain.SysUser;
public interface UserMapping {
    public SysUser findByUserName(String username);
}
