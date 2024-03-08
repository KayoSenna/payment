package com.br.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.payment.model.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
	
}
