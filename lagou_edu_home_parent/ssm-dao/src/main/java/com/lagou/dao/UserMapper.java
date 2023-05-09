package com.lagou.dao;

import com.lagou.domain.*;

import java.util.List;

public interface UserMapper {

    /*
    *   用户分页&多条件组合查询
    * */
    public List<User> findAllUserByPage(UserVO userVO);

    /*
    *   修改用户状态
    * */
    public void updateUserStatus(User user);

    /*
    *   用户登录（根据用户名查询具体的用户信息）
    * */
    public User login(User user);

    /*
    *   根据用户ID查询关联的角色信息
    * */
    public List<Role> findUserRelationRoleById(Integer id);

    /*
    *   根据用户ID清空中间表
    * */
    public void deleteUserContextRole(Integer userId);

    /*
    *   分配角色(向user_role_relation中间表添加记录)
    * */
    public void userContextRole(User_Role_relation user_role_relation);

    /*
    *   根据角色id查询所拥有的顶级菜单(parent_id = -1)
    * */
    public List<Menu> findParentMenuByRoleId(List<Integer> ids);

    /*
    *   根据pid查询子菜单信息
    * */
    public List<Menu> findSubMenuByPid(Integer pid);

    /*
    *   获取用户拥有的资源权限信息
    * */
    public List<Resource> findResourceByRoleId(List<Integer> ids);

    /*
    *   注册用户
    * */
    public void register(User user);

}
