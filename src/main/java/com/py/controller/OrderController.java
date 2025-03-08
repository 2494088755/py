package com.py.controller;

import com.py.dto.OrderDto;
import com.py.entity.Order;
import com.py.service.OrderService;
import com.py.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/page")
    public Result getOrdersByPage(@RequestBody OrderDto dto) {
        return orderService.getOrdersByPage(dto);
    }

    @GetMapping("/u/{userId}")
    public Result getOrdersByUserId(@PathVariable Long userId, @RequestParam int currentPage, @RequestParam int pageSize) {
        return orderService.getOrderByUserId(userId, currentPage, pageSize);
    }

    @PostMapping
    public Result createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    public Result updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> status) {
        return orderService.updateOrderStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public Result deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }

    @GetMapping("/checkBorrowStatus")
    public Result checkBorrowStatus(HttpServletRequest request) {
        return orderService.checkBorrowStatus(request);
    }

    @PostMapping("/borrow")
    public Result createBorrowOrder(@RequestBody Map<String, Object> params,HttpServletRequest request) {
        Long bookId = Long.parseLong(params.get("bookId").toString());
        Integer borrowTime = Integer.parseInt(params.get("borrowTime").toString());
        return orderService.createBorrowOrder(bookId, borrowTime,request);
    }

    @PostMapping("/{orderId}/return")
    public Result handleReturn(@PathVariable Long orderId) {
        return orderService.handleReturn(orderId);
    }

    @PostMapping("/{orderId}/confirmReturn")
    public Result confirmReturn(@PathVariable Long orderId) {
        return orderService.confirmReturn(orderId);
    }
}