package easyfind.pedido.controller.mapper;

import com.be.two.c.apibetwoc.controller.produto.mapper.ProdutoMapper;
import com.be.two.c.apibetwoc.model.ItemVenda;
import easyfind.pedido.controller.dto.ItemVendaCreateDTO;
import easyfind.pedido.controller.dto.ItemVendaCriacaoDTO;
import easyfind.pedido.controller.dto.ResponseItemVendaDTO;
import easyfind.pedido.model.ItemVenda;

public class ItemVendaMapper {

    public static ItemVenda of(ItemVendaCriacaoDTO itemVendaCriacaoDto){
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setQuantidade(itemVendaCriacaoDto.quantidade());
        return itemVenda;
    }
    public static ItemVenda of(ItemVendaCreateDTO dto){
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setQuantidade(dto.getQuantidade());
        return itemVenda;
    }
    public static ResponseItemVendaDTO of(ItemVenda itemVenda){
        ResponseItemVendaDTO responseItemVendaDto = new ResponseItemVendaDTO(itemVenda.getProduto(),itemVenda.getQuantidade(), ProdutoMapper.toProdutoDetalhamento(itemVenda.getProduto()));
        return responseItemVendaDto;
    }
}
