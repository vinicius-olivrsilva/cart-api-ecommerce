package br.com.eyeker.cart.repository;

import br.com.eyeker.cart.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoRepository extends JpaRepository <Carrinho, Long> {
}
