package com.br.payment.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.br.payment.model.enums.TypeIdentifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pessoa", schema = "public")
public class Person {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", nullable = false)
	private String name;
	
	@Column(name = "identificador")
	private String identifier;
	
	@Column(name = "data_nascimento")
	private LocalDate birthDate;
	
	@Column(name = "tipo_identificador")
	@Enumerated(EnumType.STRING)
	private TypeIdentifier typeIdentifier;
	
	@Column(name = "valor_min_mensal")
	private BigDecimal minimumMonthlyAmount;

	@Column(name = "valor_max_emprestimo")
	private BigDecimal maximumLoanAmount;
	
	@OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Loan> loans;
}
