package com.bdt.queswer.controller;

import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.model.Address;
import com.bdt.queswer.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    AddressService addressService;

    @GetMapping("/all")
    public List<Address> getListAddress() throws CustomException {
        return addressService.getListAddress();
    }
}
