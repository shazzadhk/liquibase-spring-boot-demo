package com.shazzad.auditdemo.controller;

import com.shazzad.auditdemo.dto.AddressRequest;
import com.shazzad.auditdemo.dto.AddressResponse;
import com.shazzad.auditdemo.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public void saveAddress(@RequestBody @Validated AddressRequest request){
        this.addressService.saveAddress(request);
    }

    @GetMapping("/get/{id}")
    public AddressResponse addressResponse(@PathVariable Long id){
        return this.addressService.getAddress(id);
    }
}
