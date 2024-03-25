package easyfind.pedido.controller.dto;


import easyfind.pedido.util.StatusPedido;
import lombok.Data;

@Data
public class PedidoStatusDTO {
    private StatusPedido status;
    private Long idEstabelecimento;
}
