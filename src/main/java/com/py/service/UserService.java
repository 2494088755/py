package com.py.service;

import com.py.dto.UserDto;
import com.py.entity.User;
import com.py.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2025-02-15 14:43:37
 */
public interface UserService {

    Result login(User user, HttpServletRequest request, HttpServletResponse response);

    Result register(User user, HttpServletRequest request);

    Result logout(HttpServletRequest request, HttpServletResponse response);

    Result getUserInfo(Long id, HttpServletRequest request);

    Result getUserList(UserDto dto, HttpServletRequest request);

    Result updateUserInfo(HttpServletRequest request, User user);

    Result getByIds(List<Integer> ids, HttpServletRequest request);

    // 新增用户
    Result addUser(User user, HttpServletRequest request);

    // 删除用户
    Result deleteUser(Long id, HttpServletRequest request);

    // 更新用户
    Result updateUser(User user, HttpServletRequest request);
}