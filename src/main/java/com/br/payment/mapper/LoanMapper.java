package com.br.payment.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.payment.dto.response.LoanResponse;
import com.br.payment.model.Loan;

@Service
public class LoanMapper {
	
	@Autowired
	private PersonMapper personMapper;
	
	@Autowired
	private ModelMapper modelMapper;

	public LoanResponse response (Loan model) {
		
		LoanResponse response = modelMapper.map(model, LoanResponse.class);
		response.setPerson(personMapper.response(model.getPerson()));
		
		return response;
	}
	
	public List<LoanResponse> response(List<Loan> model){
		
		return model.stream().map(m -> this.response(m)).collect(Collectors.toList());
	}
	
}
