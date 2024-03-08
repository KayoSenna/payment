package com.br.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.payment.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
