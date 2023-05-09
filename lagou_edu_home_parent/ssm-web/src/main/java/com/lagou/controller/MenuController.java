package com.lagou.controller;

import com.lagou.domain.Menu;
import com.lagou.domain.ResponseResult;
import com.lagou.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /*
    *   查询所有菜单信息
    * */
    @RequestMapping(path = "/findAllMenu")
    public ResponseResult findAllManu() {

        List<Menu> allMenu = menuService.findAllMenu();
        ResponseResult responseResult = new ResponseResult(true, 200, "查询所有菜单信息成功", allMenu);

        return responseResult;

    }

    /*
    *   回显菜单信息
    * */
    @RequestMapping(path = "/findMenuInfoById")
    public ResponseResult findMenuInfoById(Integer id) {

        // 根据id的值判断当前是更新还是添加操作 判断id的值是否为-1
        if (id == -1) {
            // 添加 回显信息中不需要查询menu的信息
            List<Menu> subMenuListByPid = menuService.findSubMenuListByPid(id);

            // 封装数据
            Map<Object, Object> map = new HashMap<>();
            map.put("menuInfo", null);
            map.put("parentMenuList", subMenuListByPid);

            return new ResponseResult(true, 200, "添加回显成功", map);
        } else {
            // 修改操作

            Menu menu = menuService.findMenuById(id);

            List<Menu> subMenuListByPid = menuService.findSubMenuListByPid(id);

            Map<Object, Object> map = new HashMap<>();
            map.put("menuInfo", menu);
            map.put("parentMenuList", subMenuListByPid);

            return new ResponseResult(true, 200, "修改回显成功", map);

        }

    }

    /*
    *   添加或修改菜单
    * */
    @RequestMapping(path = "/saveOrUpdateMenu")
    public ResponseResult saveOrUpdateMenu(@RequestBody Menu menu) {

        if(menu.getId() == null) {
            menuService.saveMenu(menu);
            return new ResponseResult(true, 200, "添加菜单成功", null);
        } else {
            menuService.updateMenu(menu);
            return new ResponseResult(true, 200, "修改菜单成功", null);
        }
    }

}
