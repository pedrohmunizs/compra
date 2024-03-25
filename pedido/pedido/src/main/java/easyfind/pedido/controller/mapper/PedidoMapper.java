package easyfind.pedido.controller.mapper;

import easyfind.pedido.controller.dto.EstabelecimentoResponsePedido;
import easyfind.pedido.controller.dto.MetodoDTO;
import easyfind.pedido.controller.dto.ResponsePedidoConsumidorDTO;
import easyfind.pedido.controller.dto.ResponsePedidoDTO;
import easyfind.pedido.model.Pedido;
import easyfind.pedido.util.StatusPedido;

import java.time.LocalDateTime;

public class PedidoMapper {
    public static Pedido of(MetodoDTO metodoDto){
        Pedido pedido = new Pedido();
        pedido.setIsPagamentoOnline(metodoDto.getIsPagamentoOnline());
        pedido.setDataHoraPedido(LocalDateTime.now());
        pedido.setStatusDescricao(StatusPedido.PENDENTE);
        return pedido;
    }

    public static ResponsePedidoDTO of(Pedido pedido) {
        ResponsePedidoDTO responsePedidoDTO = new ResponsePedidoDTO(pedido.getId(), pedido.getDataHoraPedido(), pedido.getStatusDescricao(), pedido.getIsPagamentoOnline(), pedido.getMetodoPagamentoAceito().getMetodoPagamento().getDescricao(), pedido.getItens().stream().map(ItemVendaMapper::of).toList());
        return responsePedidoDTO;
    }
    public static ResponsePedidoConsumidorDTO ofResponseUsuario(Pedido pedido) {
        return new ResponsePedidoConsumidorDTO(pedido.getId(), pedido.getDataHoraPedido(), pedido.getStatusDescricao(), pedido.getIsPagamentoOnline(), pedido.getMetodoPagamentoAceito().getMetodoPagamento().getDescricao(), pedido.getItens().stream().map(ItemVendaMapper::of).toList(), new EstabelecimentoResponsePedido(pedido.getMetodoPagamentoAceito().getEstabelecimento().getNome(), new EnderecoResponsePedido(pedido.getMetodoPagamentoAceito().getEstabelecimento().getEndereco().getRua(), pedido.getMetodoPagamentoAceito().getEstabelecimento().getEndereco().getNumero(), pedido.getMetodoPagamentoAceito().getEstabelecimento().getEndereco().getBairro())));
    }
}
