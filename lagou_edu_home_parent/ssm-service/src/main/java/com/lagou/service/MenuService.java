package com.lagou.service;

import com.lagou.domain.Menu;

import java.util.List;

public interface MenuService {

    /*
    *   查询所有父子菜单
    * */
    public List<Menu> findSubMenuListByPid(Integer pid);

    /*
    *   查询所有菜单信息
    * */
    public List<Menu> findAllMenu();

    public Menu findMenuById(Integer id);

    /*
    *   添加菜单
    * */
    public void saveMenu(Menu menu);

    /*
    *   修改菜单
    * */
    public void updateMenu(Menu menu);
}
