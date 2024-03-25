package easyfind.pedido.controller.dto;

import easyfind.pedido.util.StatusPedido;

import java.time.LocalDateTime;
import java.util.List;

public record ResponsePedidoDTO(Long id, LocalDateTime dataPedido, StatusPedido statusPedido, Boolean isPagamentoOnline ,
                                String metodoPagamento, List<ResponseItemVendaDTO> itens){

}



