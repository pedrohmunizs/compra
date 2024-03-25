package easyfind.pedido.controller.dto;

import com.be.two.c.apibetwoc.controller.produto.dto.ProdutoDetalhamentoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseItemVendaDTO {
    private Long id;
    private int quantidade;
    ProdutoDetalhamentoDTO produto;



}
