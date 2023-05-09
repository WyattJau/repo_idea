package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo findAllUserByPage(UserVO userVO) {

        PageHelper.startPage(userVO.getCurrentPage(), userVO.getPageSize());

        List<User> list = userMapper.findAllUserByPage(userVO);

        PageInfo<User> pageInfo = new PageInfo<>(list);

        return pageInfo;

    }

    /*
    *   修改用户状态
    * */
    @Override
    public void updateUserStatus(Integer id, String status) {

        User user = new User();
        user.setId(id);
        user.setStatus(status);
        user.setUpdate_time(new Date());

        userMapper.updateUserStatus(user);

    }


    /*
    *   用户登录
    * */
    @Override
    public User login(User user) throws Exception {

        // 1、调用dao的方法   user1中包含了密文密码
        User user1 = userMapper.login(user);

        if(user1 != null && Md5.verify(user.getPassword(), "lagou", user1.getPassword())) {
            return user1;
        } else {
            return null;
        }

    }


    /*
    *   分配角色（回显）
    * */
    @Override
    public List<Role> findUserRelationRoleById(Integer id) {

        List<Role> list = userMapper.findUserRelationRoleById(id);

        return list;

    }

    /*
    *   用户关联角色
    * */
    @Override
    public void userContextRole(UserVO userVO) {

        // 清空中间表关联关系
        userMapper.deleteUserContextRole(userVO.getUserId());

        // 重新建立关联关系
        for (Integer roleId : userVO.getRoleIdList()) {

            // 封装数据
            User_Role_relation user_role_relation = new User_Role_relation();
            user_role_relation.setRoleId(roleId);
            user_role_relation.setUserId(userVO.getUserId());

            Date date = new Date();
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);
            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");

            userMapper.userContextRole(user_role_relation);

        }

    }

    /*
    *   获取用户权限，进行菜单动态展示
    * */
    @Override
    public ResponseResult getUserPermissions(Integer userId) {

        // 1、获取当前用户所拥有的角色
        List<Role> roleList = userMapper.findUserRelationRoleById(userId);

        // 2、获取角色Id保存到list集合中
        ArrayList<Integer> roleIds = new ArrayList<>();
        for (Role role : roleList) {
            roleIds.add(role.getId());
        }
        
        // 3、根据角色id查询父菜单
        List<Menu> parentMenu = userMapper.findParentMenuByRoleId(roleIds);

        // 4、查询封装父菜单关联的子菜单
        for (Menu menu : parentMenu) {
            List<Menu> subMenu = userMapper.findSubMenuByPid(menu.getId());
            menu.setSubMenuList(subMenu);
        }

        // 5、获取资源信息
        List<Resource> resourceList = userMapper.findResourceByRoleId(roleIds);

        // 6、封装数据并返回
        Map<String, Object> map = new HashMap<>();
        map.put("menuList", parentMenu); // 父菜单里边的subMenuList已经封装，所以直接传递父菜单集合对象即可
        map.put("resourceList", resourceList);

        return new ResponseResult(true, 200, "获取用户权限信息成功", map);

    }

    /*
    *   注册用户
    * */
    @Override
    public void register(User user) throws Exception {

        // 将前台传递过来的密码转换成密文密码
        String encode_password = Md5.md5(user.getPassword(), "lagou");

        // 封装数据
        user.setPassword(encode_password);
        user.setStatus("ENABLE");
        user.setIs_del(0);
        Date date = new Date();
        user.setCreate_time(date);
        user.setUpdate_time(date);

        userMapper.register(user);
    }
}
