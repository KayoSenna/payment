CREATE TABLE pessoa (
    id bigserial NOT NULL,
    nome varchar(50) NOT NULL,
    identificador varchar(50) NOT NULL,
    data_nascimento date NOT NULL,
    tipo_identificador varchar(50) NOT NULL,
    valor_min_mensal decimal(18, 4) NOT NULL,
    valor_max_emprestimo decimal(18, 4) NOT NULL,
    CONSTRAINT pessoa_pk PRIMARY KEY (id),
    CONSTRAINT pessoa_akey UNIQUE (identificador)
);

CREATE TABLE emprestimo (
    id bigserial NOT NULL,
    id_pessoa bigint NOT NULL,
    valor_emprestimo decimal(18, 4) NOT NULL,
    numero_parcelas int4 NOT NULL,
    status_pagamento varchar(50) NOT NULL,
    data_criacao date NOT NULL,
    CONSTRAINT emprestimo_pk PRIMARY KEY (id),
    FOREIGN KEY (id_pessoa) REFERENCES pessoa(id)
);