package com.br.payment.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.br.payment.model.enums.TypeIdentifier;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonResponse {
	
	private Long id;

	@JsonProperty("nome")
	private String name;

	@JsonProperty("identificador")
	private String identifier;
	
	@JsonProperty("dataAniversario")
	private LocalDate birthDate;
	
	@JsonProperty("tipoIdentificador")
	private TypeIdentifier typeIdentifier;
	
	@JsonProperty("valorMinimoMensal")
	private BigDecimal minimumMonthlyAmount;
	
	@JsonProperty("valorMaximoEmprestimo")
	private BigDecimal maximumLoanAmount;
	
}
