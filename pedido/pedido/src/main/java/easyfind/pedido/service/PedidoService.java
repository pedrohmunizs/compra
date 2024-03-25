package easyfind.pedido.service;

import easyfind.pedido.controller.dto.MetodoDTO;
import easyfind.pedido.controller.dto.PedidoStatusDTO;
import easyfind.pedido.controller.dto.ResponsePedidoConsumidorDTO;
import easyfind.pedido.controller.dto.ResponsePedidoDTO;
import easyfind.pedido.controller.mapper.PedidoMapper;
import easyfind.pedido.exception.EntidadeNaoEncontradaException;
import easyfind.pedido.model.Pedido;
import easyfind.pedido.repository.PedidoRepository;
import easyfind.pedido.util.ListaObj;
import easyfind.pedido.util.StatusPedido;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public Pedido cadastrar(@Valid MetodoDTO metodoDto){
        Pedido pedido = PedidoMapper.of(metodoDto);
        pedido.setMetodoPagamentoAceito(metodoDto.getIdMetodoPagamento());
        return pedidoRepository.save(pedido);
    }

    public ListaObj<ResponsePedidoDTO> listarPorEstabelecimento(Long idEstabelecimento) {

        List<Pedido> pedidos = pedidoRepository.searchByEstabelecimento(idEstabelecimento);
        ListaObj<ResponsePedidoDTO> listaPedidos = new ListaObj<>(pedidos.size());
        for (Pedido pedido : pedidos) {
            listaPedidos.adiciona(PedidoMapper.of(pedido));
        }

        return listaPedidos;
    }
    public List<Pedido> listarPorConsumidor(StatusPedido status, Long idConsumidor) {

        if(status != null){
            return pedidoRepository.searchByConsumidorEStatus(idConsumidor, status);
        }

        List <Pedido> pedidos = pedidoRepository.searchByConsumidor(idConsumidor);
        return pedidos;
    }
    public void alterarStatusPedido(Long idPedido, PedidoStatusDTO novoStatus) {
        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(() -> new EntidadeNaoEncontradaException("O pedido informado não existe"));
        pedido.setStatusDescricao(novoStatus.getStatus());
        pedidoRepository.save(pedido);
    }
    public List<ResponsePedidoDTO> listarPorEstabelecimentoEStatus(Long idEstabelecimento, StatusPedido status) {
        return  pedidoRepository.searchByEstabelecimentoEStatus(idEstabelecimento, status).stream().map(PedidoMapper::of).toList();
    }
    public void deletar(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pedido não encontrado"));
        pedido.setStatusDescricao(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }
}
