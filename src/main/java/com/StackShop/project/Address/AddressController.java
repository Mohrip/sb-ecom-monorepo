package com.StackShop.project.Address;


import com.StackShop.project.user.User;
import com.StackShop.project.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AddressController {
   // private final AuthUtil authUtil;

    public AddressController(AuthUtil authUtil) {
        this.authUtil = authUtil;
    }

    @Autowired
    AuthUtil authUtil;

    @Autowired
    AddressService addressService;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> creatAddress(@Valid @RequestBody AddressDTO addressDTO) {
       User user = authUtil.loggedInUser();
       AddressDTO savedAddressDTO = addressService.createAddress(addressDTO, user);
         return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);
    }
}
