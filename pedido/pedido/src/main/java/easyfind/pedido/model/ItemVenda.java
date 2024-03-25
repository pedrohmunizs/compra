package easyfind.pedido.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    private Long consumidor;
    @ManyToOne
    @JoinColumn(name = "fk_pedido")
    private Pedido pedido;
    private Long produto;
    private boolean isPromocaoAtiva;
}
