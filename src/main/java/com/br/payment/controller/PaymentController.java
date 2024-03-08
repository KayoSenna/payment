package com.br.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.payment.controller.swagger.PaymentControllerSwagger;
import com.br.payment.dto.response.LoanResponse;
import com.br.payment.service.LoanService;

@RestController
public class PaymentController implements PaymentControllerSwagger{
	
	@Autowired
	private LoanService service;
	
	@PutMapping(path="/loan/{emprestimoId}/payment")
	@Override
	public ResponseEntity<LoanResponse> payLoan(@PathVariable("emprestimoId") Long id) {
		
		return ResponseEntity.ok(service.payLoan(id));
	}
	
}
