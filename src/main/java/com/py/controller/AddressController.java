package com.py.controller;

import com.py.entity.Address;
import com.py.service.AddressService;
import com.py.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // 获取所有地址
    @GetMapping
    public Result getAllAddresses() {
        return addressService.getAllAddresses();
    }

    // 根据ID获取地址
    @GetMapping("/{id}")
    public Result getAddressById(@PathVariable Long id) {
        return addressService.getAddressById(id);
    }

    // 创建新地址
    @PostMapping
    public Result createAddress(@RequestBody Address address) {
        return addressService.createAddress(address);
    }

    // 更新地址
    @PutMapping("/{id}")
    public Result updateAddress(@PathVariable Long id, @RequestBody Address address) {
        return addressService.updateAddress(id, address);
    }

    // 删除地址
    @DeleteMapping("/{id}")
    public Result deleteAddress(@PathVariable Long id) {
        return addressService.deleteAddress(id);
    }
}
