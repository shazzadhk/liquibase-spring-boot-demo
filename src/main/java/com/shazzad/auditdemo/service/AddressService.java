package com.shazzad.auditdemo.service;

import com.shazzad.auditdemo.dto.AddressRequest;
import com.shazzad.auditdemo.dto.AddressResponse;
import com.shazzad.auditdemo.dto.EmployeeResponse;
import com.shazzad.auditdemo.entity.Address;
import com.shazzad.auditdemo.entity.Employee;
import com.shazzad.auditdemo.repository.AddressRepository;
import com.shazzad.auditdemo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final EmployeeRepository employeeRepository;

    public void saveAddress(AddressRequest request){

        Employee employee = employeeRepository.findById(request.employeeId()).get();

        Address address = Address.builder()
            .id(request.id())
            .city(request.city())
            .roadNumber(request.roadNumber())
            .build();

        address.setEmployee(employee);
        addressRepository.save(address);
    }

    public AddressResponse getAddress(Long id){
        Address address = addressRepository.findById(id).get();
        Employee employees = address.getEmployee();

        return AddressResponse.builder()
            .id(address.getId())
            .city(address.getCity())
            .roadNumber(address.getRoadNumber())
            .employeeResponse(
                EmployeeResponse.builder()
                    .id(employees.getId())
                    .name(employees.getName())
                    .department(employees.getDepartment())
                    .build()
            ).build();
    }
}
