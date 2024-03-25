package easyfind.pedido.client;

import easyfind.pedido.controller.dto.ProdutoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "produto", url = "https://localhost:8081/produtos")
public interface ProdutoClient {
    @GetMapping("/{id}")
    ProdutoResponseDTO buscarPorId(@PathVariable Long id);
}
