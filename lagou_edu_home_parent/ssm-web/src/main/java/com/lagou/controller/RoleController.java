package com.lagou.controller;

import com.lagou.domain.*;
import com.lagou.service.MenuService;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /*
    *   查询所有角色(条件)
    * */
    @RequestMapping(path = "/findAllRole")
    public ResponseResult findAllRole(@RequestBody Role role){

        List<Role> allRole = roleService.findAllRole(role);

        ResponseResult responseResult = new ResponseResult(true, 200, "查询角色信息成功", allRole);

        return responseResult;

    }

    /*
    *   查询所有父子菜单信息（分配菜单中的第一个接口）
    * */
    @Autowired
    private MenuService menuService;

    @RequestMapping(path = "/findAllMenu")
    public ResponseResult findSubMenuListByPid() {

        // -1 表示查询所有的父子级菜单
        List<Menu> menuList = menuService.findSubMenuListByPid(-1);

        Map<String, Object> map = new HashMap<>();
        map.put("parentMenuList", menuList);

        ResponseResult responseResult = new ResponseResult(true, 200, "查询所有父子菜单信息成功", map);

        return responseResult;
    }

    /*
    *   根据角色ID查询关联的菜单信息ID
    * */
    @RequestMapping(path = "findMenuByRoleId")
    public ResponseResult findMenuByRoleId(Integer roleId) {

        List<String> menuByRoleId = roleService.findMenuByRoleId(roleId);

        ResponseResult responseResult = new ResponseResult(true, 200, "查询角色关联的菜单信息成功", menuByRoleId);

        System.out.println(menuByRoleId);

        return responseResult;

    }

    /*
    *   为角色分配菜单
    * */
    @RequestMapping(path = "/RoleContextMenu")
    public ResponseResult RoleContextMenu(@RequestBody RoleMenuVo roleMenuVo) {

        roleService.roleContextMenu(roleMenuVo);

        return new ResponseResult(true, 200, "为角色分配菜单成功", null);
    }

    /*
    *   删除角色
    * */
    @RequestMapping(path = "/deleteRole")
    public ResponseResult deleteRole(Integer id) {

        roleService.deleteRole(id);

        return new ResponseResult(true, 200, "删除角色成功", null);

    }

    /*
    *   添加或修改角色
    * */
    @RequestMapping(path = "saveOrUpdateRole")
    public ResponseResult saveOrUpdateRole(@RequestBody Role role) {

        if (role.getId() == null) { // 如果id为null，则为添加角色
            roleService.saveRole(role);
            return new ResponseResult(true, 200, "添加角色成功", null);
        }
        else {
            roleService.updateRole(role);
            return new ResponseResult(true, 200, "修改角色成功", null);
        }
    }

}
