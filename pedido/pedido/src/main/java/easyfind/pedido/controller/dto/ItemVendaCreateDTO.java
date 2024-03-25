package easyfind.pedido.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemVendaCreateDTO {
    @NotNull
    private Long idProduto;
    @Min(value = 1)
    private Integer quantidade;
}
