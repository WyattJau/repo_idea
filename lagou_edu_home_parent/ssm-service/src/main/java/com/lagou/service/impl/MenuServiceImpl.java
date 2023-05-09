package com.lagou.service.impl;

import com.lagou.dao.MenuMapper;
import com.lagou.domain.Menu;
import com.lagou.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findSubMenuListByPid(Integer pid) {

        List<Menu> subMenuListByPid = menuMapper.findSubMenuListByPid(pid);

        return subMenuListByPid;

    }

    @Override
    public List<Menu> findAllMenu() {

        List<Menu> allMenu = menuMapper.findAllMenu();

        return allMenu;

    }

    @Override
    public Menu findMenuById(Integer id) {

        return menuMapper.findMenuById(id);

    }

    /*
    *   添加菜单
    * */
    @Override
    public void saveMenu(Menu menu) {

        // 封装数据
        Date date = new Date();
        menu.setCreatedTime(date);
        menu.setUpdatedTime(date);
        menu.setCreatedBy("system");
        menu.setUpdatedBy("system");

        // 调用dao
        menuMapper.saveMenu(menu);

    }

    /*
    *   修改菜单
    * */
    @Override
    public void updateMenu(Menu menu) {

        // 封装数据
        menu.setUpdatedTime(new Date());
        menu.setUpdatedBy("system");

        // 调用dao
        menuMapper.updateMenu(menu);

    }
}
