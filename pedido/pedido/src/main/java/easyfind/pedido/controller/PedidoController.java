package easyfind.pedido.controller;

import easyfind.pedido.client.AutenticacaoClient;
import easyfind.pedido.client.ConsumidorClient;
import easyfind.pedido.client.EstabelecimentoClient;
import easyfind.pedido.client.ProdutoClient;
import easyfind.pedido.controller.dto.*;
import easyfind.pedido.controller.mapper.PedidoMapper;
import easyfind.pedido.model.Pedido;
import easyfind.pedido.service.ItemVendaService;
import easyfind.pedido.service.PedidoService;
import easyfind.pedido.util.FilaRequisicao;
import easyfind.pedido.util.ListaObj;
import easyfind.pedido.util.StatusPedido;
import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;
    private final ItemVendaService itemVendaService;
    private final FilaRequisicao filaRequisicao;
    @Autowired
    private ConsumidorClient consumidorClient;
    @Autowired
    private ProdutoClient produtoClient;
    @Autowired
    private EstabelecimentoClient estabelecimentoClient;
    @Autowired
    private AutenticacaoClient autenticacaoClient;
    @PostMapping
    public ResponseEntity<ResponsePedidoDTO> cadastrar(@RequestBody @Valid PedidoCreateDTO pedidoCreate){

        try{
            ConsumidorResponseDTO consumidor = consumidorClient.buscarPorId(pedidoCreate.getIdConsumidor());
        }catch (FeignException e){

            int status = e.status();

            if (status == 404){
                return ResponseEntity.notFound().build();
            } else if (status == 400){
                return ResponseEntity.badRequest().build();
            } else {
                return ResponseEntity.status(503).build();
            }
        }

        List<ProdutoResponseDTO> produtos = new ArrayList<>();

        for (ItemVendaCreateDTO i : pedidoCreate.getItens()){
            try{
                ProdutoResponseDTO produto = produtoClient.buscarPorId(i.getIdProduto());
                produtos.add(produto);
            }catch (FeignException e){

                int status = e.status();

                if (status == 404){
                    return ResponseEntity.notFound().build();
                } else if (status == 400){
                    return ResponseEntity.badRequest().build();
                } else {
                    return ResponseEntity.status(503).build();
                }
            }
        }
        filaRequisicao.entrarFila();
        Pedido pedido = itemVendaService.cadastrar(pedidoCreate, produtos);
        filaRequisicao.sairFila();
        return ResponseEntity.ok(PedidoMapper.of(pedido));
    }

    @GetMapping("/estabelecimento/{idEstabelecimento}")
    public ResponseEntity<ListaObj<ResponsePedidoDTO>> buscarPedidosPorEstabelecimento(@PathVariable Long idEstabelecimento) {
        try{
            EstabelecimentoResponseDTO estabelecimento = estabelecimentoClient.buscarPorId(idEstabelecimento);
        }catch (FeignException e){

            int status = e.status();

            if (status == 404){
                return ResponseEntity.notFound().build();
            } else if (status == 400){
                return ResponseEntity.badRequest().build();
            } else {
                return ResponseEntity.status(503).build();
            }
        }

        Boolean autenticacao = autenticacaoClient.validarPedidosEstabelecimento(idEstabelecimento);
        if (!autenticacao){
            ResponseEntity.notFound().build();
        }

        ListaObj<ResponsePedidoDTO> pedidos = pedidoService.listarPorEstabelecimento(idEstabelecimento);
        return ResponseEntity.ok(pedidos);
    }
    @GetMapping("/consumidor/{idConsumidor}")
    public ResponseEntity<List<ResponsePedidoConsumidorDTO>> buscarPedidosPorConsumidor(@PathVariable Long idConsumidor, @RequestParam(required = false) StatusPedido status) {
        ConsumidorResponseDTO consumidor = consumidorClient.buscarPorId(idConsumidor);
        if (consumidor==null){
            ResponseEntity.notFound().build();
        }
        List<Pedido> pedidos = pedidoService.listarPorConsumidor(status, idConsumidor);
        List<ResponsePedidoConsumidorDTO>pedidosDto = pedidos.stream().map(PedidoMapper::ofResponseUsuario).toList();

        if (pedidosDto.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidosDto);
    }
    @PatchMapping("{idPedido}/status")
    public ResponseEntity<Void> atualizarStatusPedido(@PathVariable Long idPedido, @RequestBody PedidoStatusDTO dto) {
        Boolean autenticacao = autenticacaoClient.validarPedidosEstabelecimento(dto.getIdEstabelecimento());
        if (!autenticacao){
            ResponseEntity.notFound().build();
        }

        pedidoService.alterarStatusPedido(idPedido, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estabelecimento/{idEstabelecimento}/status")
    public ResponseEntity<List<ResponsePedidoDTO>> buscarPedidosPorEstabelecimentoEStatus(@PathVariable Long idEstabelecimento, @RequestParam StatusPedido status) {
        try{
            EstabelecimentoResponseDTO estabelecimento = estabelecimentoClient.buscarPorId(idEstabelecimento);
        }catch (FeignException e){

            int status1 = e.status();

            if (status1 == 404){
                return ResponseEntity.notFound().build();
            } else if (status1 == 400){
                return ResponseEntity.badRequest().build();
            } else {
                return ResponseEntity.status(503).build();
            }
        }
        Boolean autenticacao = autenticacaoClient.validarPedidosEstabelecimento(idEstabelecimento);
        if (!autenticacao){
            ResponseEntity.notFound().build();
        }

        List<ResponsePedidoDTO> pedidos = pedidoService.listarPorEstabelecimentoEStatus(idEstabelecimento, status);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pedidos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
