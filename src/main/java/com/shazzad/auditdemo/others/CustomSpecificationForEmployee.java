package com.shazzad.auditdemo.others;


import org.springframework.data.jpa.domain.Specification;

public class CustomSpecificationForEmployee<T> {

    public Specification<T> equalAtRoot(String value, String columnName){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(columnName), value);
    }

    public Specification<T> likeAtPrefixAndSuffix(String value, String columnName){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(columnName), "%" + value + "%");
    }
}
