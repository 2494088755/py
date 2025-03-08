package com.py.service;

import com.py.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;
import com.py.utils.Result;

/**
* @author 24940
* @description 针对表【address】的数据库操作Service
* @createDate 2025-02-27 22:39:51
*/
public interface AddressService extends IService<Address> {

    Result getAllAddresses();

    Result getAddressById(Long id);

    Result createAddress(Address address);

    Result updateAddress(Long id, Address address);

    Result deleteAddress(Long id);
}
