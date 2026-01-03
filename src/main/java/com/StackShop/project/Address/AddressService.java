package com.StackShop.project.Address;

import com.StackShop.project.user.User;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO, User user);
}
