package com.StackShop.project.Address;

import com.StackShop.project.user.User;
import jakarta.validation.Valid;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAddresses();

    AddressDTO getAddressesById(Integer addressId);

    List<AddressDTO> getUserAdresses(User user);

    AddressDTO updateAddress(Integer addressId, AddressDTO addressDTO);

    String deleteAddress(Integer addressId);
}
