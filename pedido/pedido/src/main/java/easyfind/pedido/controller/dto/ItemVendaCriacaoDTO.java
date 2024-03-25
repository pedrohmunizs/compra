package easyfind.pedido.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemVendaCriacaoDTO(
        @NotNull
        Long idConsumidor,
        @NotNull
        Long idProduto,
        @Min(value = 1)
        int quantidade,
        @NotNull
        PedidoCriacaoDTO pedido
) {
}
