package br.com.saudefood.application.service;

import java.time.LocalDateTime;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.com.saudefood.domain.pagamento.DadosCartao;
import br.com.saudefood.domain.pagamento.Pagamento;
import br.com.saudefood.domain.pagamento.PagamentoRepository;
import br.com.saudefood.domain.pagamento.StatusPagamento;
import br.com.saudefood.domain.pedido.Carrinho;
import br.com.saudefood.domain.pedido.ItemPedido;
import br.com.saudefood.domain.pedido.ItemPedidoPK;
import br.com.saudefood.domain.pedido.ItemPedidoRepository;
import br.com.saudefood.domain.pedido.Pedido;
import br.com.saudefood.domain.pedido.Pedido.Status;
import br.com.saudefood.domain.pedido.PedidoRepository;
import br.com.saudefood.util.SecurityUtils;


@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	PagamentoRepository pagamentoRepository;
	
	@Value("${saudefood.saupay.url}")
	private String sauPayUrl;
	
	@Value("${saudefood.saupay.token}")
	private String sauPayToken;
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = PagamentoException.class)
	public Pedido criarEPagar(Carrinho carrinho, String numCartao) throws PagamentoException {
		
		Pedido pedido = new Pedido();
		pedido.setData(LocalDateTime.now());
		pedido.setCliente(SecurityUtils.loggedCliente());
		pedido.setRestaurante(carrinho.getRestaurante());
		pedido.setStatus(Status.Producao);
		pedido.setTaxaEntrega(carrinho.getRestaurante().getTaxaEntrega());
		pedido.setTotalCaloria(carrinho.getCaloriaTotal());
		pedido.setSubTotal(carrinho.getPrecoTotal(false));
		pedido.setTotal(carrinho.getPrecoTotal(true));
		
		pedido = pedidoRepository.save(pedido);
		
		int ordem = 1;
		
		for(ItemPedido itemPedido : carrinho.getItens()) {
			itemPedido.setId(new ItemPedidoPK(pedido,ordem ++));
			itemPedidoRepository.save(itemPedido);
		}
		
		
		DadosCartao dadosCartao = new DadosCartao();
		dadosCartao.setNumCartao(numCartao);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Token", sauPayToken);
		
		HttpEntity<DadosCartao> requestEntity = new HttpEntity<>(dadosCartao, headers);
		
		RestTemplate restTemplate = new RestTemplate();
		
		Map<String, String> response;
		try {		
		 response = restTemplate.postForObject(sauPayUrl, requestEntity, Map.class);
		} catch(Exception e) {
			throw new PagamentoException("Erro no servidor de pagamento");
		}
			StatusPagamento statusPagamento = StatusPagamento.valueOf(response.get("status"));
			
			if(statusPagamento != StatusPagamento.Autorizado) {
				throw new PagamentoException(statusPagamento.getDescricao());
			}
			
			Pagamento pagamento = new Pagamento();
			pagamento.setData(LocalDateTime.now());
			pagamento.setPedido(pedido);
			pagamento.definirNumeroBandeira(numCartao);	
			pagamentoRepository.save(pagamento);
		
		
		return pedido;
		
	}
	
}
