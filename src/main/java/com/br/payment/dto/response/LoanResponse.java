package com.br.payment.dto.response;


import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

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
public class LoanResponse {
	
	Long id;
	
	@JsonProperty("pessoa")
	private PersonResponse person;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonProperty("dataCriacao")
	private LocalDate creationDate;
	
	@JsonProperty("statusPagamento")
	private String paymentStatus;
	
	@JsonProperty("valorDeEmprestimo")
	private BigDecimal value;
	
	@JsonProperty("numeroDeParcelas")
	private Integer numberOfInstallments;

}
