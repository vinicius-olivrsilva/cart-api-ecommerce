package br.com.eyeker.cart.service.impl;

import br.com.eyeker.cart.enumeration.FormaPagamento;
import br.com.eyeker.cart.model.Carrinho;
import br.com.eyeker.cart.model.Item;
import br.com.eyeker.cart.model.Restaurante;
import br.com.eyeker.cart.repository.CarrinhoRepository;
import br.com.eyeker.cart.repository.ItemRepository;
import br.com.eyeker.cart.repository.ProdutoRepository;
import br.com.eyeker.cart.resource.dto.ItemDto;
import br.com.eyeker.cart.service.CarrinhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CarrinhoServiceImpl implements CarrinhoService {
    private final CarrinhoRepository carrinhoRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemRepository itemRepository;

    @Override
    public Item incluirItemNoCarrinho(ItemDto itemDto) {
        Carrinho carrinho = verCarrinho(itemDto.getIdCarrinho());

        if (carrinho.isFechada()) {
            throw new RuntimeException("Este carrinho esta fechado.");
        }

        Item itemParaSerInserido = Item.builder()
                .quantidade(itemDto.getQuantidade())
                .carrinho(carrinho)
                .produto(produtoRepository.findById(itemDto.getIdProduto()).orElseThrow(
                        () -> {
                            throw new RuntimeException("Esse produto não existe!");
                        }))
                .build();

        List<Item> itensDoCarrinho = carrinho.getItens();
        if (itensDoCarrinho.isEmpty()) {
            itensDoCarrinho.add(itemParaSerInserido);
        } else {
            Restaurante restauranteAtual = itensDoCarrinho.get(0).getProduto().getRestaurante();
            Restaurante restauranteDoItemParaAdcionar = itemParaSerInserido.getProduto().getRestaurante();

            if (restauranteAtual.equals(restauranteDoItemParaAdcionar)) {
                itensDoCarrinho.add(itemParaSerInserido);
            } else {
                throw new RuntimeException("Não é possível adicionar produtos de restaurantes diferentes. Finalize o pedido ou esvazie o carrinho.");
            }
        }

        // Calculando o valor total do carrinho
        List<Double> valorDosItens = new ArrayList<>();

        for (Item itemDoCarrinho : itensDoCarrinho) {
            double valorTotalItem = itemDoCarrinho.getProduto().getValorUnitario() * itemDoCarrinho.getQuantidade();
            valorDosItens.add(valorTotalItem);
        }

        //Estudar mais essa parte
        double valorTotalCarrinho = valorDosItens.stream()
                .mapToDouble(valorTotalDeCadaItem -> valorTotalDeCadaItem)
                .sum();
       /* Double valorTotalCarrinho = 0.0;
        for (Double valorDeCadaItem : valorDosItens){
            valorTotalCarrinho += valorDeCadaItem;
        }*/

        carrinho.setValorTotal(valorTotalCarrinho);
        carrinhoRepository.save(carrinho);
        return itemParaSerInserido;
    }

    @Override
    public Carrinho verCarrinho(Long id) {
        return carrinhoRepository.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("Esse carrinho não existe!");
                }
        );
    }

    @Override
    public Carrinho fecharCarrinho(Long id, int numeroFormaPagamento) {
        Carrinho carrinho = verCarrinho(id);
        if (carrinho.getItens().isEmpty()) {
            throw new RuntimeException("Inclua itens no carrinho!");
        }

        FormaPagamento formaPagamento = numeroFormaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;

        carrinho.setFormaPagamento(formaPagamento);
        carrinho.setFechada(true);
        return carrinhoRepository.save(carrinho);
    }
}