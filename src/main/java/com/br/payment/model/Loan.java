package com.br.payment.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.br.payment.model.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "emprestimo", schema = "public")
public class Loan {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "valor_emprestimo", nullable = false)
	private BigDecimal value;
	
	@Column(name = "numero_parcelas")
	private Integer numberOfInstallments;
	
	@Column(name = "status_pagamento")
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	
	@Column(name = "data_criacao")
	private LocalDate creationDate;
	
    @ManyToOne
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    public Person person;
	
}
