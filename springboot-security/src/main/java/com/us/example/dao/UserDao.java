package com.us.example.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.us.example.domain.SysUser;


@Repository
public interface UserDao extends CrudRepository<SysUser, Long> {
	@Query(value="select u.*,r.name from Sys_User u LEFT JOIN sys_role_user sru on u.id= sru.Sys_User_id"+
        " LEFT JOIN Sys_Role r on sru.Sys_Role_id=r.id"+
        " where username= ?1",nativeQuery=true)
    public SysUser findByUserName(String username);
}
