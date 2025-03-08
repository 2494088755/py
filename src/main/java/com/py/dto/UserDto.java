package com.py.dto;

import com.py.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long total;
    private Integer pageSize;
    private Integer currentPage;
    private String keyword;
    private List<User> list;
}
