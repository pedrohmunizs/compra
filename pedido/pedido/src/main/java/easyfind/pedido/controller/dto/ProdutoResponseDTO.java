package easyfind.pedido.controller.dto;

import lombok.Data;

@Data
public class ProdutoResponseDTO {
    private Long idProduto;
    private Boolean isPromocaoAtiva;
}
