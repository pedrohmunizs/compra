package easyfind.pedido.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PedidoCriacaoDTO(
        @NotNull
        Long idMetodoPagamento,
        @NotBlank
        String nf,
        boolean isPagamentoOnline
) {
}
