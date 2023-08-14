package com.shazzad.auditdemo.repository;

import com.shazzad.auditdemo.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
