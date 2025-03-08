package com.py.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.py.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2025-02-15 14:43:36
 */
@Mapper
public interface UserDao  extends BaseMapper<User> {

}

