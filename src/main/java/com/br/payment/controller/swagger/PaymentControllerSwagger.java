package com.br.payment.controller.swagger;

import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.ResponseEntity;

import com.br.payment.dto.response.LoanResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Controller de Pagamento")
public interface PaymentControllerSwagger  {

	@Operation(summary ="Realiza o pagamento de um emprestimo", method = "POST")
	@ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", 
		schema = @Schema(implementation = LoanResponse.class)) }, description = "Requisição com sucesso"),
			@ApiResponse(responseCode = "404", content = { @Content(mediaType = "application/json", 
		schema = @Schema(implementation = Problem.class)) }, description = "O recurso não foi encontrado") })
	ResponseEntity<LoanResponse> payLoan(Long loanId);
	
}

