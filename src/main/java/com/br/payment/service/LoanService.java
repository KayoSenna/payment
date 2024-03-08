package com.br.payment.service;

import com.br.payment.dto.response.LoanResponse;

public interface LoanService {

	LoanResponse payLoan(Long loanId);

}