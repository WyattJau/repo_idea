package com.lagou.service.impl;

import com.lagou.dao.RoleMapper;
import com.lagou.domain.Role;
import com.lagou.domain.RoleMenuVo;
import com.lagou.domain.Role_menu_relation;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAllRole(Role role) {

        List<Role> allRole = roleMapper.findAllRole(role);

        return allRole;

    }

    @Override
    public List<String> findMenuByRoleId(Integer roleId) {

        List<String> menuByRoleId = roleMapper.findMenuByRoleId(roleId);

        return menuByRoleId;

    }

    /*
    *   为角色分配菜单
    * */
    @Override
    public void roleContextMenu(RoleMenuVo roleMenuVo) {

        // 1、清空中间表的关联关系
        roleMapper.deleteRoleContextMenu(roleMenuVo.getRoleId());

        // 2、为角色分配菜单
        for (Integer menuId : roleMenuVo.getMenuIdList()) {

            Role_menu_relation role_menu_relation = new Role_menu_relation();
            role_menu_relation.setMenuId(menuId);
            role_menu_relation.setRoleId(roleMenuVo.getRoleId());

            // 封装数据
            Date date = new Date();
            role_menu_relation.setCreatedTime(date);
            role_menu_relation.setUpdatedTime(date);
            role_menu_relation.setCreatedBy("system");
            role_menu_relation.setUpdatedby("system");

            roleMapper.roleContextMenu(role_menu_relation);
        }

    }

    @Override
    public void deleteRole(Integer roleid) {

        // 调用根据roleid清空中间表关联关系的方法
        roleMapper.deleteRoleContextMenu(roleid);

        // 调用删除角色方法
        roleMapper.deleteRole(roleid);

    }

    /*
    *   添加角色
    * */
    @Override
    public void saveRole(Role role) {

        // 封装数据
        Date date = new Date();
        role.setCreatedTime(date);
        role.setUpdatedTime(date);
        role.setCreatedBy("system");
        role.setUpdatedBy("system");

        roleMapper.saveRole(role);

    }

    @Override
    public void updateRole(Role role) {

        role.setUpdatedTime(new Date());
        role.setUpdatedBy("system");

        roleMapper.updateRole(role);

    }
}
