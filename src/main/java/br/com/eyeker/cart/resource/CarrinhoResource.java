package br.com.eyeker.cart.resource;

import br.com.eyeker.cart.model.Carrinho;
import br.com.eyeker.cart.model.Item;
import br.com.eyeker.cart.resource.dto.ItemDto;
import br.com.eyeker.cart.service.CarrinhoService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(value="/ifood-devweek/carrinhos")
@RestController
@RequestMapping("/ifood-devweek/carrinhos")
@RequiredArgsConstructor
public class CarrinhoResource {
    private final CarrinhoService carrinhoService;

    @PostMapping
    public Item incluirItemNoCarrinho(@RequestBody ItemDto itemDto) {
        return carrinhoService.incluirItemNoCarrinho(itemDto);
    }

    @GetMapping("/{id}")
    public Carrinho verCarrinho(@PathVariable("id") Long id) {
        return carrinhoService.verCarrinho(id);
    }

    @PatchMapping("/fecharCarrinho/{idCarrinho}")
    public Carrinho fecharCarrinho(@PathVariable("idCarrinho") Long idCarrinho,
                                   @RequestParam("formaPagamento") int formaPagamento){
        return carrinhoService.fecharCarrinho(idCarrinho, formaPagamento);
    }
}
