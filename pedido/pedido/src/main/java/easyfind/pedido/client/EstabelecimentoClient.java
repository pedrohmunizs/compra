package easyfind.pedido.client;

import easyfind.pedido.controller.dto.EstabelecimentoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "estabelecimento", url = "https://localhost:8081/estabelecimentos")
public interface EstabelecimentoClient {
    @GetMapping("/{id}")
    EstabelecimentoResponseDTO buscarPorId(@PathVariable Long id);
}
