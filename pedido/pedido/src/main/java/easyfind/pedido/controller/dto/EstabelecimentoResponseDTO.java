package easyfind.pedido.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class EstabelecimentoResponseDTO {
    private Long id;
    private List<Long> metodoPagamento;
}
