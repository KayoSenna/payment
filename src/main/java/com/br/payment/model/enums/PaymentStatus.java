package com.br.payment.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {

    PAGO("Pago"),
    PENDENTE("Pendente"),
    CANCELADO("Cancelado");
	
	String name;

}
