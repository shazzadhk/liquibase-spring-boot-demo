package com.shazzad.auditdemo.controller;

import com.shazzad.auditdemo.dto.CountResponse;
import com.shazzad.auditdemo.dto.EmployeeRequest;
import com.shazzad.auditdemo.dto.EmployeeResponse;
import com.shazzad.auditdemo.dto.EmployeeSearchRequest;
import com.shazzad.auditdemo.dto.PaginatedResponse;
import com.shazzad.auditdemo.service.EmployeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public void saveEmployee(@RequestBody EmployeeRequest request){
        this.employeeService.saveEmp(request);
    }

    @GetMapping
    public List<EmployeeResponse> getAllEmployees(){
        return this.employeeService.getAllEmp();
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployee(@PathVariable("id") Long id){
        return this.employeeService.getAEmp(id);
    }

    @GetMapping("/count/{deptName}")
    public CountResponse countEmployeeByDept(@PathVariable("deptName") String deptName){
        return this.employeeService.getCountByDept(deptName);
    }

    @GetMapping("/countAll")
    public List<CountResponse> getAllCountByDept(){
        return this.employeeService.getAllCountByDepartment();
    }

    @PostMapping("/search")
    public List<EmployeeResponse> getEmployeeBySearch(@RequestBody EmployeeSearchRequest request){
        return this.employeeService.getAllEmployeeBySearch(request);
    }

    @GetMapping("/paginated/{size}/{page}")
    public PaginatedResponse<EmployeeResponse> getEmpPaginatedResponse(@PathVariable("size") int size,
                                                                       @PathVariable("page") int page){
        return this.employeeService.getAllEmpWithPaginatedData(size,page);
    }
}
