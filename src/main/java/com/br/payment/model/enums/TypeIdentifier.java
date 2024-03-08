package com.br.payment.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeIdentifier {

    PF(11L,"Pessoa Física"),
	PJ(14L,"Pessoa Jurídica"),
	EU(8L,"Estudante Universitário"),
	AP(10L,"Pessoa Jurídica");
	
	Long limit;
	String name;

}
