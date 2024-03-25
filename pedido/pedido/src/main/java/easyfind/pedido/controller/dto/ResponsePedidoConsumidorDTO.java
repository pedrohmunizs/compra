package easyfind.pedido.controller.dto;

import easyfind.pedido.util.StatusPedido;

import java.time.LocalDateTime;
import java.util.List;

public record ResponsePedidoConsumidorDTO(Long id, LocalDateTime dataPedido, StatusPedido statusPedido, Boolean isPagamentoOnline ,
                                          String metodoPagamento, List<ResponseItemVendaDTO> itens, EstabelecimentoResponsePedido estabelecimento) { }
