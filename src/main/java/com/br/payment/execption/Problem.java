package com.br.payment.execption;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem
{

	@Schema(example = "400")
	private final Integer status;

	@Schema(example = "2019-12-01T18:09:02.70844Z")
	private final OffsetDateTime timestamp;

	@Schema(example = "https://oab.com.br/dados-invalidos")
	private final String type;

	@Schema(example = "Dados inválidos")
	private final String title;

	@Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
	private final String detail;

	@Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
	private final String userMessage;

	@Schema(description = "Lista de objetos ou campos que geraram o erro (opcional)")
	private final List<Object> objects;

	@Schema(name = "ObjetoProblema")
	@Getter
	@Builder
	public static class Object
	{

		@Schema(example = "nome")
		private final String name;

		@Schema(example = "O nome é obrigatório")
		private final String userMessage;

	}

}
