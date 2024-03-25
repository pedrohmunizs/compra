package easyfind.pedido.service.evento;

import lombok.Data;

@Data
public class PedidoCarrinhoEvent {
    private Long idPedido;
    private Long idConsumidor;
}
