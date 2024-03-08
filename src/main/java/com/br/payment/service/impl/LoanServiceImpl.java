package com.br.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.br.payment.dto.response.LoanResponse;
import com.br.payment.execption.BaseException;
import com.br.payment.mapper.LoanMapper;
import com.br.payment.model.Loan;
import com.br.payment.model.enums.PaymentStatus;
import com.br.payment.repository.LoanRepository;
import com.br.payment.service.LoanService;

@Service
public class LoanServiceImpl implements LoanService {

	@Autowired
	private LoanRepository repository;

	@Autowired
	private LoanMapper mapper;

	@Override
	public LoanResponse payLoan(Long loanId) {
		
		Loan model = repository.findById(loanId)
				.orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "Empréstimo não encontrado!") {
				});
		
		model.setPaymentStatus(PaymentStatus.PAGO);
		
		model = repository.save(model);
		
		return mapper.response(model);
	}
	
}