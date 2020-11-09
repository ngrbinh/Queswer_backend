package com.bdt.queswer.service;

import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.model.Address;
import com.bdt.queswer.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    public List<Address> getListAddress() throws CustomException {
        try {
            return addressRepository.findAll();
        } catch (Error e) {
            throw  new CustomException("Tải dữ liệu không thành công");
        }
    }
}
