package com.lagou.service;

import com.lagou.domain.Role;
import com.lagou.domain.RoleMenuVo;

import java.util.List;

public interface RoleService {

    /*
    *   根据条件查询所有角色
    * */
    public List<Role> findAllRole(Role role);

    /*
     *   根据角色ID查询该角色关联的菜单信息ID [1, 2, 3, 5]
     * */
    public List<String> findMenuByRoleId(Integer roleId);

    /*
    *   为角色分配菜单
    * */
    public void roleContextMenu(RoleMenuVo roleMenuVo);

    /*
    *   删除角色
    * */
    public void deleteRole(Integer roleid);

    /*
    *   添加角色
    * */
    public void saveRole(Role role);

    /*
    *   修改角色
    * */
    public void updateRole(Role role);

}
