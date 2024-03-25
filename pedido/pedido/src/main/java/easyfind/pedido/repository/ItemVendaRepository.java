package easyfind.pedido.repository;

import easyfind.pedido.model.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

    void deleteByIdIn(List<Long> idItemVenda);

    List<ItemVenda> findByIdIn(List<Long> ids);

}
