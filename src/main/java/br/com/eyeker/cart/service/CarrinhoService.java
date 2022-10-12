package br.com.eyeker.cart.service;

import br.com.eyeker.cart.model.Carrinho;
import br.com.eyeker.cart.model.Item;
import br.com.eyeker.cart.resource.dto.ItemDto;

public interface CarrinhoService {
    Item incluirItemNoCarrinho(ItemDto itemDto);
    Carrinho verCarrinho(Long id);
    Carrinho fecharCarrinho(Long id, int formaPagamento);
}
