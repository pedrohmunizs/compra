package easyfind.pedido.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MetodoDTO {
    @NotNull
    private Long idMetodoPagamento;
    @NotNull
    private Boolean isPagamentoOnline;
}
