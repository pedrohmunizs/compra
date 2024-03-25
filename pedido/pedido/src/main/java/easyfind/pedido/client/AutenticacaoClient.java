package easyfind.pedido.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "autenticacao", url = "https://localhost:8084/autenticacoes")
public interface AutenticacaoClient {
    @GetMapping("/estabalecimento/{id}")
    Boolean validarPedidosEstabelecimento(Long id);
}
