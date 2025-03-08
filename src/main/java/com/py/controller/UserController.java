package com.py.controller;

import com.py.dto.UserDto;
import com.py.entity.User;
import com.py.service.UserService;
import com.py.utils.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2025-02-15 14:43:36
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 登录接口
     * @param user 用户信息，包含登录所需的用户名和密码等
     * @param request HttpServletRequest对象，用于获取请求相关信息
     * @param response HttpServletResponse对象，用于响应请求
     * @return Result对象，包含登录结果信息
     */
    @PostMapping("login")
    public Result login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        return userService.login(user,request,response);
    }

    /**
     * 注册接口
     * 对用户提交的注册信息进行验证和处理
     * @param user 用户注册信息，需要通过验证的包括用户名、密码等
     * @param request HttpServletRequest对象，用于获取请求相关信息
     * @return Result对象，包含注册结果信息
     */
    @PostMapping("register")
    public Result register(@Valid @RequestBody User user, HttpServletRequest request) {
        return userService.register(user,request);
    }

    /**
     * 用户登出接口
     * @param request HttpServletRequest对象，用于获取请求相关信息
     * @param response HttpServletResponse对象，用于响应请求
     * @return Result对象，包含登出结果信息
     */
    @GetMapping("logout")
    public Result logout(HttpServletRequest request,HttpServletResponse response) {
        return userService.logout(request,response);
    }

    /**
     * 获取用户信息接口
     * @param id 用户ID，必须提供且不能为空
     * @param request HttpServletRequest对象，用于获取请求相关信息
     * @return Result对象，包含用户信息
     */
    @GetMapping("/info/{id}")
    public Result getUserInfo(@Valid @NotNull @PathVariable Long id, HttpServletRequest request) {
        return userService.getUserInfo(id,request);
    }

    /**
     * 更新用户信息接口
     * @param request HttpServletRequest对象，用于获取请求相关信息
     * @param user 包含更新后用户信息的数据对象
     * @return Result对象，包含更新结果信息
     */
    @PutMapping
    public Result updateUserInfo(HttpServletRequest request, @RequestBody User user) {
        return userService.updateUserInfo(request,user);
    }

    /**
     * 通过ID列表获取用户信息接口
     * @param ids 用户ID列表
     * @param request HttpServletRequest对象，用于获取请求相关信息
     * @return Result对象，包含指定ID列表的用户信息
     */
    @PostMapping("/ids")
    public Result getByIds(@RequestBody List<Integer> ids, HttpServletRequest request) {
        return userService.getByIds(ids, request);
    }

    /**
     * 获取用户列表接口
     * @param params 用户查询参数
     * @param request HttpServletRequest对象，用于获取请求相关信息
     * @return Result对象，包含用户列表信息
     */
    @PostMapping("list")
    public Result getUserList(@RequestBody UserDto params, HttpServletRequest request) {
        return userService.getUserList(params, request);
    }

    /**
     * 新增用户接口
     * @param user 用户信息
     * @param request HttpServletRequest对象
     * @return Result对象，包含新增结果信息
     */
    @PostMapping("add")
    public Result addUser(@Valid @RequestBody User user, HttpServletRequest request) {
        return userService.addUser(user, request);
    }

    /**
     * 删除用户接口
     * @param id 用户ID
     * @param request HttpServletRequest对象
     * @return Result对象，包含删除结果信息
     */
    @DeleteMapping("delete/{id}")
    public Result deleteUser(@Valid @NotNull @PathVariable Long id, HttpServletRequest request) {
        return userService.deleteUser(id, request);
    }

    /**
     * 更新用户接口
     * @param user 用户信息
     * @param request HttpServletRequest对象
     * @return Result对象，包含更新结果信息
     */
    @PutMapping("update")
    public Result updateUser(@Valid @RequestBody User user, HttpServletRequest request) {
        return userService.updateUser(user, request);
    }
}

