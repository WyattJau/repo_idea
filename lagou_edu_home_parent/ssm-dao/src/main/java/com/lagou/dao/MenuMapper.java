package com.lagou.dao;

import com.lagou.domain.Menu;

import java.util.List;

public interface MenuMapper {

    /*
    *   查询所有父子菜单信息
    * */
    public List<Menu> findSubMenuListByPid(Integer pid);

    /*
    *   查询所有菜单信息
    * */
    public List<Menu> findAllMenu();

    /*
    *   根据id查询menu
    * */
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
