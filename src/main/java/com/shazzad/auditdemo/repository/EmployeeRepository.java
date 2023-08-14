package com.shazzad.auditdemo.repository;

import com.shazzad.auditdemo.entity.Employee;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRepository extends JpaRepository<Employee,Long>, JpaSpecificationExecutor<Employee> {

    long countHabiJabiByDepartmentIgnoreCase(String deptName);

    Stream<Employee> findEmployeeByName(String name);
}
