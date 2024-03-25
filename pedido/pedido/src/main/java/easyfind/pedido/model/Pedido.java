package easyfind.pedido.model;

import easyfind.pedido.util.StatusPedido;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nf;
    private LocalDateTime dataHoraPedido;

    @Enumerated(EnumType.STRING)
    private StatusPedido statusDescricao;
    private Boolean isPagamentoOnline;
    private LocalDateTime dataHoraRetirada;
    private Long metodoPagamentoAceito;
    @OneToMany(mappedBy = "pedido")
    private List<ItemVenda> itens;
    private List<Long> transacoes;
}