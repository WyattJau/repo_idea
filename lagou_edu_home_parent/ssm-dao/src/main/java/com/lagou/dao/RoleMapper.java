package com.lagou.dao;

import com.lagou.domain.Role;
import com.lagou.domain.Role_menu_relation;

import java.util.List;

public interface RoleMapper {

    /*
    *   查询所有角色信息
    * */
    public List<Role> findAllRole(Role role);

    /*
    *   根据角色ID查询该角色关联的菜单信息ID [1, 2, 3, 5]
    * */
    public List<String> findMenuByRoleId(Integer roleId);

    /*
    *   根据roleid清空中间表的关联关系
    * */
    public void deleteRoleContextMenu(Integer rid);

    /*
    *   为角色分配菜单信息
    * */
    public void roleContextMenu(Role_menu_relation role_menu_relation);

    /*
    *   删除角色
    * */
    public void deleteRole(Integer roleid);

    /*
    *  添加角色
    * */
    public void saveRole(Role role);

    /*
    *   修改角色
    * */
    public void updateRole(Role role);

}
