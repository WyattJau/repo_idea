package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVO;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    
    /*
    *   用户分页&多条件查询
    * */
    @RequestMapping(path = "findAllUserByPage")
    public ResponseResult findAllUserByPage(@RequestBody UserVO userVO) {

        PageInfo allUserByPage = userService.findAllUserByPage(userVO);

        ResponseResult responseResult = new ResponseResult(true, 200, "用户分页查询成功", allUserByPage);

        return responseResult;

    }

    /*
    *   修改用户状态
    * */
    @RequestMapping(path = "/updateUserStatus")
    public ResponseResult updateUserStatus(Integer id, String status) {

        userService.updateUserStatus(id, status);

        Map<Object, Object> map = new HashMap<>();
        map.put("status", status);

        return new ResponseResult(true, 200, "修改用户状态成功", map);
    }

    /*
    *   用户登录
    * */
    @RequestMapping(path = "/login")
    public ResponseResult login(User user, HttpServletRequest request) throws Exception {    // get请求，所以不需要加@RequestBody

        User user1 = userService.login(user);

        if (user1 != null) {

            // 保存用户id以及access_token到session中
            HttpSession session = request.getSession();
            String access_token = UUID.randomUUID().toString();
            session.setAttribute("access_token", access_token);
            session.setAttribute("user_id", user1.getId());

            Map<String, Object> map = new HashMap<>();
            map.put("access_token", access_token);
            map.put("user_id", user1.getId());

            // 将查询出来的user1也存到map中
            map.put("user", user1);

            return new ResponseResult(true, 1, "登陆成功", map);

        } else {
            return new ResponseResult(true, 400, "用户名密码错误", null);
        }

    }

    /*
    *   分配角色（回显）
    * */
    @RequestMapping(path = "findUserRelationRoleById")
    public ResponseResult findUserRelationRoleById(Integer id) {

        List<Role> roleList = userService.findUserRelationRoleById(id);

        return new ResponseResult(true, 200, "分配角色回显成功", roleList);

    }

    /*
    *   分配角色
    * */
    @RequestMapping(path = "/userContextRole")
    public ResponseResult userContextRole(@RequestBody UserVO userVO) {

        userService.userContextRole(userVO);

        return new ResponseResult(true, 200, "分配角色成功", null);

    }

    /*
    *   获取用户权限，进行菜单动态展示
    * */
    @RequestMapping(path = "/getUserPermissions")
    public ResponseResult getUserPermissions(HttpServletRequest request) {

        // 1、获取请求头中的token
        String header_token = request.getHeader("Authorization");

        // 2、获取session中的token
        HttpSession session = request.getSession();
        String session_token = (String) session.getAttribute("access_token");

        // 3、判断token是否一致
        if (header_token.equals(session_token)) {
            // 1、获取用户ID
            Integer user_id = (Integer) request.getSession().getAttribute("user_id");

            // 2、调用service，进行菜单查询
            ResponseResult responseResult = userService.getUserPermissions(user_id);

            return responseResult;
        } else {
            ResponseResult responseResult = new ResponseResult(false, 400, "获取菜单信息失败", null);

            return responseResult;
        }

    }

    // 注册用户
    @RequestMapping(path = "register")
    public ResponseResult register(@RequestBody User user) throws Exception {

        userService.register(user);

        return new ResponseResult(true, 200, "注册成功", null);

    }

}
