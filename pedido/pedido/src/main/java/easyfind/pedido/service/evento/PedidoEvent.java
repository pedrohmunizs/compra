package easyfind.pedido.service.evento;

import easyfind.pedido.model.ItemVenda;
import lombok.Data;

import java.util.List;
@Data
public class PedidoEvent {
    private Long idPedido;
    private List<ItemVenda>itens;
}
