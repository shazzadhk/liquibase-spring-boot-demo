package com.shazzad.auditdemo.service;

import static com.shazzad.auditdemo.others.ApplicationContents.DEFAULT_ORDER_BY;

import com.shazzad.auditdemo.dto.AddressResponse;
import com.shazzad.auditdemo.dto.CountResponse;
import com.shazzad.auditdemo.dto.EmployeeRequest;
import com.shazzad.auditdemo.dto.EmployeeResponse;
import com.shazzad.auditdemo.dto.EmployeeSearchRequest;
import com.shazzad.auditdemo.dto.PaginatedResponse;
import com.shazzad.auditdemo.entity.Address;
import com.shazzad.auditdemo.entity.Employee;
import com.shazzad.auditdemo.repository.AddressRepository;
import com.shazzad.auditdemo.repository.EmployeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public void saveEmp(EmployeeRequest request) {

        List<Address> addressList = addressRepository.findAllById(request.addressIds());

        Employee employee = Employee.builder()
            .id(request.id())
            .name(request.name())
            .department(request.department())
            .addressList(addressList)
            .companyName(request.companyName())
            .build();

        for (Address address : addressList) {
            address.setEmployee(employee);
        }
        this.employeeRepository.save(employee);
    }

    public List<EmployeeResponse> getAllEmp() {

        Sort sortByName = Sort.by(Sort.Direction.ASC, "name");
        Sort sortByDept = Sort.by(Sort.Direction.DESC, "department");

        Sort sortGroup = sortByName.and(sortByDept);

        Pageable pageable = PageRequest.of(0, 10, sortGroup);

        Page<Employee> employeePages = this.employeeRepository.findAll(pageable);
        List<Employee> employeeList = employeePages.getContent();

        return convertEmployeeToResponse(employeeList);
    }

    private List<EmployeeResponse> convertEmployeeToResponse(List<Employee> employeeList){
        return employeeList.stream().map(
            employee -> getEmployeeResponse(employee)
                .addressList(
                    employee.getAddressList().stream().map(
                        this::getAddressResponse
                    ).toList()
                ).build()
        ).toList();
    }

    private EmployeeResponse.EmployeeResponseBuilder getEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
            .id(employee.getId())
            .name(employee.getName())
            .companyName(employee.getCompanyName())
            .department(employee.getDepartment());
    }

    private AddressResponse getAddressResponse(Address address) {
        return AddressResponse.builder()
            .id(address.getId())
            .city(address.getCity())
            .roadNumber(address.getRoadNumber())
            .build();
    }

    public EmployeeResponse getAEmp(Long id) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee is not available with this id"));
        return modelMapper.map(employee, EmployeeResponse.class);
    }

    public CountResponse getCountByDept(String deptName) {
        return prepareCountResponse(deptName, employeeRepository.countHabiJabiByDepartmentIgnoreCase(deptName));
    }

    private CountResponse prepareCountResponse(String department, long count) {
        return CountResponse.builder()
            .departmentName(department)
            .employeeCount(count)
            .build();
    }

    public List<CountResponse> getAllCountByDepartment() {

        return getAllEmp().stream()
            .map(EmployeeResponse::getDepartment)
            .distinct()
            .map(
                department -> prepareCountResponse(department, employeeRepository.countHabiJabiByDepartmentIgnoreCase(department))
            )
            .toList();
    }

//    public List<EmployeeResponse> getAllEmployeeBySearch(EmployeeSearchRequest request){
//        Employee employee = new Employee();
//        employee.setName(request.employeeSearchText());
//        employee.setDepartment(request.employeeSearchText());
//        try{
//            employee.setSalary(Integer.valueOf(request.employeeSearchText()));
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny()
//            .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
//            .withMatcher("department", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
//            .withMatcher("salary", ExampleMatcher.GenericPropertyMatchers.exact());
//
//        Example<Employee> employeeExample = Example.of(employee,customExampleMatcher);
//
//        Pageable pageable = PageRequest.of(
//            request.currentPage(),
//            request.elementSize(),
//            Sort.by(
//                (request.orderDir().equalsIgnoreCase("asc"))
//                    ? Sort.Direction.ASC
//                    : Sort.Direction.DESC,
//                request.orderFieldName()
//            )
//        );
//
//        Page<Employee> employeeListPage = employeeRepository.findAll(employeeExample,pageable);
//        List<Employee> employeeList = employeeListPage.getContent();
//
//        return convertEmployeeToResponse(employeeList);
//    }

    private Specification<Employee> getEmpSpecification(EmployeeSearchRequest request){
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            return criteriaBuilder.or(
                criteriaBuilder.like(root.get("name"), "%" + request.employeeSearchText() + "%"),
                criteriaBuilder.like(root.get("department"), "%" + request.employeeSearchText() + "%"));

        };

    }

    public List<EmployeeResponse> getAllEmployeeBySearch(EmployeeSearchRequest request){


        Pageable pageable = PageRequest.of(
            request.currentPage(),
            request.elementSize(),
            Sort.by(
                (request.orderDir().equalsIgnoreCase("asc"))
                    ? Sort.Direction.ASC
                    : Sort.Direction.DESC,
                request.orderFieldName()
            )
        );

        Page<Employee> employeeListPage = employeeRepository.findAll(getEmpSpecification(request),pageable);
        List<Employee> employeeList = employeeListPage.getContent();

        return convertEmployeeToResponse(employeeList);
    }


    public PaginatedResponse<EmployeeResponse> getAllEmpWithPaginatedData(int size,int page) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.ASC,DEFAULT_ORDER_BY));

        Page<Employee> employeePages = employeeRepository.findAll(pageable);
        List<EmployeeResponse> employeeResponseList = employeePages.getContent()
                                                    .stream()
                                                    .map(element -> modelMapper.map(element, EmployeeResponse.class))
                                                    .toList();

        return PaginatedResponse.<EmployeeResponse>builder()
            .size(employeePages.getSize())
            .totalElements(employeePages.getTotalElements())
            .totalPages(employeePages.getTotalPages())
            .currentPages(employeePages.getPageable().getPageNumber())
            .contents(employeeResponseList)
            .build();
    }
}
