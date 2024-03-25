package easyfind.pedido.service;


import easyfind.pedido.controller.dto.ItemVendaCreateDTO;
import easyfind.pedido.controller.dto.PedidoCreateDTO;
import easyfind.pedido.controller.dto.ProdutoResponseDTO;
import easyfind.pedido.controller.mapper.ItemVendaMapper;
import easyfind.pedido.model.ItemVenda;
import easyfind.pedido.model.Pedido;
import easyfind.pedido.repository.ItemVendaRepository;
import easyfind.pedido.service.evento.PedidoCarrinhoEvent;
import easyfind.pedido.service.evento.PedidoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemVendaService {

    private final ItemVendaRepository itemVendaRepository;
    private final PedidoService pedidoService;
    private final KafkaTemplate<String, PedidoEvent> kafkaTemplate;
    private final KafkaTemplate<String, PedidoCarrinhoEvent> kafkaTemplateCarrinho;
    public Pedido cadastrar(PedidoCreateDTO pedidoCreate, List<ProdutoResponseDTO> produtos ){
        String keyTransacao = UUID.randomUUID().toString();
        String keyCarrinho = UUID.randomUUID().toString();

        PedidoEvent transacaoEvent =new PedidoEvent();
        PedidoCarrinhoEvent carrinhoEvent =new PedidoCarrinhoEvent();

        List<ItemVenda> itensVendas = new ArrayList<>();

        Pedido pedido = pedidoService.cadastrar(pedidoCreate.getMetodo());
        for (ItemVendaCreateDTO i : pedidoCreate.getItens()){
            ItemVenda itemVenda = ItemVendaMapper.of(i);

            itemVenda.setConsumidor(pedidoCreate.getIdConsumidor());
            itemVenda.setProduto(i.getIdProduto());
            itemVenda.setPedido(pedido);
            for (ProdutoResponseDTO p : produtos){
                if (p.getIdProduto().equals(i.getIdProduto())){
                    if (p.getIsPromocaoAtiva()!=null){
                        itemVenda.setPromocaoAtiva(p.getIsPromocaoAtiva());
                    }
                }
            }
            itensVendas.add(itemVenda);
        }
        pedido.setItens(itensVendas);
        itemVendaRepository.saveAll(itensVendas);

        transacaoEvent.setIdPedido(pedido.getId());
        transacaoEvent.setItens(pedido.getItens());
        kafkaTemplate.send("criar-transacao", keyTransacao, transacaoEvent);

        if(pedidoCreate.getOrigem().equals("Carrinho")){
            carrinhoEvent.setIdPedido(pedido.getId());
            carrinhoEvent.setIdConsumidor(pedidoCreate.getIdConsumidor());
            kafkaTemplateCarrinho.send("compra-realizada", keyCarrinho, carrinhoEvent);
        }
        return pedido;
    }
}
