package br.com.saudefood.domain.pagamento;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.saudefood.domain.pedido.ItemPedido;
import br.com.saudefood.domain.pedido.ItemPedidoPK;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

}
