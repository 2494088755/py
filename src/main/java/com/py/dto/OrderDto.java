package com.py.dto;

import lombok.Data;

@Data
public class OrderDto {
    int page;
    int size;
    String status;
    Long userId;
}
