package com.me.techservices.repository;

import com.me.techservices.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {
    Operator findOperatorByLastName(String s);
}
