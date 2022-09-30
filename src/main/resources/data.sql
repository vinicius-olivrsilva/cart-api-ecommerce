INSERT INTO restaurante (id, cep, complemento, nome) VALUES
(1L, '65000000', 'Complemento Endereço Restaurante 1', 'Sal Grosso'),
(2L, '65000000', 'Complemento Endereço Restaurante 2', 'Maní');

INSERT INTO cliente (id, cep, complemento, nome) VALUES
(1L, '65000000', 'Complemento Endereço Cliente 1', 'Cliente 1'),
(2L, '65000000', 'Perto da parada, do lado do negocio', 'Vinicius');

INSERT INTO produto (id, disponivel, nome, valor_unitario, restaurante_id) VALUES
(1L, true, 'Produto 1', 5.0, 1L),
(2L, true, 'Produto 2', 6.0, 1L),
(3L, true, 'Produto 3', 7.0, 2L);

INSERT INTO carrinho (id, forma_pagamento, fechada, valor_total, cliente_id) VALUES
(1L, 0, false, 0.0, 1L);