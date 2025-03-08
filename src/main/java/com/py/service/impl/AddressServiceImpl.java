package com.py.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.py.entity.Address;
import com.py.service.AddressService;
import com.py.dao.AddressMapper;
import com.py.utils.Result;
import org.springframework.stereotype.Service;

/**
* @author 24940
* @description 针对表【address】的数据库操作Service实现
* @createDate 2025-02-27 22:39:51
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
    implements AddressService{

    @Override
    public Result getAllAddresses() {
        return Result.success(list());
    }

    @Override
    public Result getAddressById(Long id) {
        Address address = getById(id);
        return address != null ? Result.success(address) : Result.error("400","地址不存在");
    }

    @Override
    public Result createAddress(Address address) {
        boolean saved = save(address);
        return saved ? Result.success(address) : Result.error("400","创建地址失败");
    }

    @Override
    public Result updateAddress(Long id, Address address) {
        address.setId(id);
        boolean updated = updateById(address);
        return updated ? Result.success(address) : Result.error("400","更新地址失败");
    }

    @Override
    public Result deleteAddress(Long id) {
        boolean removed = removeById(id);
        return removed ? Result.success() : Result.error("400","删除地址失败");
    }
}




