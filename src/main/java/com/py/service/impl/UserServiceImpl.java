package com.py.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.py.dao.UserDao;
import com.py.dto.UserDto;
import com.py.entity.User;
import com.py.service.UserService;
import com.py.utils.PwdUtil;
import com.py.utils.Result;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("userService")
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, User>
        implements UserService{

    @Override
    public Result login(User user, HttpServletRequest request, HttpServletResponse response) {
        log.info("用户登录：{}", user);
        String username = user.getUsername();
        User query = getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (query == null) {
            return Result.error("400", "用户名或密码错误");
        }
        String password = query.getPassword();
//        // 验证密码是否正确
        if (!PwdUtil.verify(user.getPassword(),password)) {
            return Result.error("400", "用户名或密码错误");
        }
        if (user.isKeepSession()) {
            Cookie cookie = new Cookie("remember-me", username);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);
        }
        query.setPassword(null);
        HttpSession session = request.getSession();
        session.setAttribute("user", query);
        session.setMaxInactiveInterval(60 * 60 * 24);
        User result = new User();
        result.setUserId(query.getUserId());
        result.setUsername(query.getUsername());
        result.setEmail(query.getEmail());
        result.setPhone(query.getPhone());
        return Result.success(result);
    }

    @Override
    public Result register(User user, HttpServletRequest request) {
        if (user.getCheckPassword() == null || !user.getCheckPassword().equals(user.getPassword())) {
            return Result.error("400", "两次密码输入不一致");
        }
        String username = user.getUsername();
        if (getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username)) != null) {
            return Result.error("400", "用户名已存在");
        }
        return save(user) ? Result.success() : Result.error("400", "注册失败");
    }

    @Override
    public Result updateUserInfo(HttpServletRequest request, User user) {
        return updateById(user)? Result.success() : Result.error("400", "修改失败");
    }

    @Override
    public Result getUserInfo(Long id, HttpServletRequest request) {
        User query = getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, id));
        if (query == null) {
            return Result.error("400", "用户不存在");
        }
        query.setPassword(null); // 移除密码
        return Result.success(query);
    }

    @Override
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Set-Cookie", "remember-me=; Max-Age=0");
        // 销毁 Session
        HttpSession session = request.getSession(false); // 避免创建新的 Session
        if (session != null) {
            session.invalidate();
        }
        return Result.success();
    }

    @Override
    public Result addUser(User user, HttpServletRequest request) {
        String username = user.getUsername();
        if (getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username)) != null) {
            return Result.error("400", "用户名已存在");
        }
        user.setPassword("123456");
        return save(user) ? Result.success() : Result.error("400", "新增用户失败");
    }

    @Override
    public Result deleteUser(Long id, HttpServletRequest request) {
        return removeById(id) ? Result.success() : Result.error("400", "删除用户失败");
    }

    @Override
    public Result updateUser(User user, HttpServletRequest request) {
        return updateById(user) ? Result.success() : Result.error("400", "更新用户失败");
    }

    @Override
    public Result getByIds(List<Integer> ids, HttpServletRequest request) {
        List<User> list = getBaseMapper().selectBatchIds(ids);
        return Result.success(list);
    }

    @Override
    public Result getUserList(UserDto dto, HttpServletRequest request) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        String keyword = dto.getKeyword();
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.like(User::getUsername, keyword)
                    .or().like(User::getEmail, keyword)
                    .or().like(User::getPhone, keyword);
        }
        Page<User> page = new Page<>(dto.getCurrentPage(), dto.getPageSize());
        Page<User> userPage = this.page(page, queryWrapper);
        List<User> users = userPage.getRecords();
        UserDto userDto = new UserDto();
        userDto.setList(users);
        userDto.setTotal(userPage.getTotal());
        return Result.success(userDto);
    }
}
