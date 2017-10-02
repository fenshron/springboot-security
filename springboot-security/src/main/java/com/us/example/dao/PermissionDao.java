package com.us.example.dao;

import com.us.example.domain.Permission;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yangyibo on 17/1/20.
 */
@Repository
public interface PermissionDao extends CrudRepository<Permission, Long> {
    //public List<Permission> findAll();
    @Query(value="select p.*"+
		" from Sys_User u"+
        " LEFT JOIN sys_role_user sru on u.id= sru.Sys_User_id"+
        " LEFT JOIN Sys_Role r on sru.Sys_Role_id=r.id"+
        " LEFT JOIN Sys_permission_role spr on spr.role_id=r.id"+
        " LEFT JOIN Sys_permission p on p.id =spr.permission_id"+
        " where u.id=?1",nativeQuery =true)
    public List<Permission> findByAdminUserId(int userId);
}
