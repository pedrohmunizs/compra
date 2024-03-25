package easyfind.pedido.controller.dto;

import lombok.Data;

import java.util.List;
@Data
public class PedidoCreateDTO {
    private Long idConsumidor;
    private Long idEstabelecimento;
    private List<ItemVendaCreateDTO> itens;
    private MetodoDTO metodo;
    private String origem;
}
