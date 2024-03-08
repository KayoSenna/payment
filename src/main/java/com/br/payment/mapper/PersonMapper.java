package com.br.payment.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.payment.dto.response.PersonResponse;
import com.br.payment.model.Person;

@Service
public class PersonMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PersonResponse response (Person model) {
	
		PersonResponse response = modelMapper.map(model, PersonResponse.class);
		
		return response;
	}


}
