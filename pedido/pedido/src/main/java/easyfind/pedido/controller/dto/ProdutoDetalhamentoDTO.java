package easyfind.pedido.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDetalhamentoDTO {
    private Long id;
    private String nome;
    private String codigoSku;
    private Double preco;
    private String descricao;
    private Double precoOferta;
    private String codigoBarras;
    private Integer quantidade;
    private String categoria;
    private Boolean isPromocaoAtiva;
    private List<String> imagens;

}
