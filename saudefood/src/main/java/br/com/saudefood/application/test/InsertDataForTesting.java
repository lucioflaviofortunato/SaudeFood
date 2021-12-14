package br.com.saudefood.application.test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.saudefood.domain.cliente.Cliente;
import br.com.saudefood.domain.cliente.ClienteRepository;
import br.com.saudefood.domain.pedido.Pedido;
import br.com.saudefood.domain.pedido.Pedido.Status;
import br.com.saudefood.domain.pedido.PedidoRepository;
import br.com.saudefood.domain.restaurante.CategoriaRestaurante;
import br.com.saudefood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.saudefood.domain.restaurante.ItemCardapio;
import br.com.saudefood.domain.restaurante.ItemCardapioRepository;
import br.com.saudefood.domain.restaurante.Restaurante;
import br.com.saudefood.domain.restaurante.RestauranteRepository;
import br.com.saudefood.util.StringUtils;

@Component
public class InsertDataForTesting {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@Autowired
	private ItemCardapioRepository itemCardapioRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Cliente[] clientes = clientes();
		Restaurante[] restaurantes = restaurantes();
		itensCardapio(restaurantes);
		
		
		Pedido p = new Pedido();
		p.setData(LocalDateTime.now());
		p.setCliente(clientes[0]);
		p.setRestaurante(restaurantes[0]);
		p.setStatus(Status.Producao);
		p.setSubTotal(BigDecimal.valueOf(10));
		p.setTotalCaloria(BigDecimal.valueOf(400));
		p.setTaxaEntrega(BigDecimal.valueOf(2));
		p.setTotal(BigDecimal.valueOf(12));
		
		pedidoRepository.save(p);
	}
	
	private Restaurante[] restaurantes(){
	
		List<Restaurante> restaurantes = new ArrayList<>();
		
		CategoriaRestaurante categoriaPizza = categoriaRestauranteRepository.findById(1).orElseThrow();
		CategoriaRestaurante categoriaSanduiche = categoriaRestauranteRepository.findById(2).orElseThrow();
		CategoriaRestaurante categoriaSobremesa = categoriaRestauranteRepository.findById(5).orElseThrow();
		CategoriaRestaurante categoriaJapones = categoriaRestauranteRepository.findById(6).orElseThrow();
		
		Restaurante r = new Restaurante();
		r.setNome("Bubger King");
		r.setEmail("r1@saudefood.com.br");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("00000000000101");
		r.setTaxaEntrega(BigDecimal.valueOf(3.2));
		r.setTelefone("99876671010");
		r.getCategorias().add(categoriaSanduiche);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0001-logo.png");
		r.setTempoEntregaBase(30);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Mc Nald's");
		r.setEmail("r2@saudefood.com.br");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("00000000000102");
		r.setTaxaEntrega(BigDecimal.valueOf(4.5));
		r.setTelefone("99876671012");
		r.getCategorias().add(categoriaSanduiche);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0002-logo.png");
		r.setTempoEntregaBase(25);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Sbubby");
		r.setEmail("r3@saudefood.com.br");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("00000000000103");
		r.setTaxaEntrega(BigDecimal.valueOf(12.2));
		r.setTelefone("99876671013");
		r.getCategorias().add(categoriaSanduiche);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0003-logo.png");
		r.setTempoEntregaBase(38);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Pizza Brut");
		r.setEmail("r4@saudefood.com.br");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("00000000000104");
		r.setTaxaEntrega(BigDecimal.valueOf(9.8));
		r.setTelefone("99876671014");
		r.getCategorias().add(categoriaPizza);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0004-logo.png");
		r.setTempoEntregaBase(22);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Wiki Japa");
		r.setEmail("r5@saudefood.com.br");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("00000000000105");
		r.setTaxaEntrega(BigDecimal.valueOf(14.9));
		r.setTelefone("99876671015");
		r.getCategorias().add(categoriaJapones);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0005-logo.png");
		r.setTempoEntregaBase(19);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		Restaurante[] array = new Restaurante[restaurantes.size()];
		return restaurantes.toArray(array);
		
	}
	private Cliente[] clientes() {
		List<Cliente> clientes = new ArrayList<>();
			
		Cliente c = new Cliente();
		c.setNome("João Silva");
		c.setEmail("joao@saudefood.com.br");
		c.setSenha(StringUtils.encrypt("c"));
		c.setCep("89300100");
		c.setMetaCalorias(BigDecimal.valueOf(1000));
		c.setCpf("03099887666");
		c.setTelefone("99355430001");
		clientes.add(c);
		clienteRepository.save(c);
		
		 c = new Cliente();
		c.setNome("Maria Torres");
		c.setEmail("maria@saudefood.com.br");
		c.setSenha(StringUtils.encrypt("c"));
		c.setCep("89300101");
		c.setMetaCalorias(BigDecimal.valueOf(1000));
		c.setCpf("03099887667");
		c.setTelefone("99355430002");
		clientes.add(c);
		clienteRepository.save(c);
		
		Cliente[] array = new Cliente[clientes.size()];
		return clientes.toArray(array);
			
		}
	
	private void itensCardapio(Restaurante[] restaurantes) {
		
		ItemCardapio ic = new ItemCardapio();
		ic.setCategoria("Sanduiche");
		ic.setDescricao("Delicioso sanduiche com molho");
		ic.setCalorias(BigDecimal.valueOf(250));
		ic.setNome("Double cheese Burguer Special");
		ic.setPreco(BigDecimal.valueOf(23.8));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(true);
		ic.setImagem("0001-comida.png");
		itemCardapioRepository.save(ic);
		
		 ic = new ItemCardapio();
		ic.setCategoria("Sanduiche");
		ic.setDescricao("Sanduiche padrão que mata a fome");
		ic.setCalorias(BigDecimal.valueOf(200));
		ic.setNome("Double cheese Burguer Simples");
		ic.setPreco(BigDecimal.valueOf(17.8));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0006-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Sanduiche");
		ic.setDescricao("Sanduiche de peru");
		ic.setCalorias(BigDecimal.valueOf(220));
		ic.setNome("Sanduiche natural da casa");
		ic.setPreco(BigDecimal.valueOf(11.8));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0007-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Bebida");
		ic.setDescricao("Refrigerante com gas");
		ic.setCalorias(BigDecimal.valueOf(137));
		ic.setNome("Refrigerante Tradicional");
		ic.setPreco(BigDecimal.valueOf(9));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0004-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Bebida");
		ic.setDescricao("Suco natural");
		ic.setCalorias(BigDecimal.valueOf(75));
		ic.setNome("Suco de Laranja");
		ic.setPreco(BigDecimal.valueOf(9));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0005-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Pizza");
		ic.setDescricao("Pizza saborosa com cebola");
		ic.setCalorias(BigDecimal.valueOf(200));
		ic.setNome("Pizza de calabresa");
		ic.setPreco(BigDecimal.valueOf(38.9));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0002-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Japonesa");
		ic.setDescricao("Delicioso Uramaki tradicional");
		ic.setCalorias(BigDecimal.valueOf(180));
		ic.setNome("Uramaki");
		ic.setPreco(BigDecimal.valueOf(16.80));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0003-comida.png");
		itemCardapioRepository.save(ic);
		
		ic.setCategoria("Sanduiche");
		ic.setDescricao("Delicioso sanduiche com molho");
		ic.setCalorias(BigDecimal.valueOf(250));
		ic.setNome("Double cheese Burguer Special");
		ic.setPreco(BigDecimal.valueOf(23.8));
		ic.setRestaurante(restaurantes[1]);
		ic.setDestaque(true);
		ic.setImagem("0001-comida.png");
		itemCardapioRepository.save(ic);
		
		 ic = new ItemCardapio();
		ic.setCategoria("Sanduiche");
		ic.setDescricao("Sanduiche padrão que mata a fome");
		ic.setCalorias(BigDecimal.valueOf(200));
		ic.setNome("Double cheese Burguer Simples");
		ic.setPreco(BigDecimal.valueOf(17.8));
		ic.setRestaurante(restaurantes[1]);
		ic.setDestaque(false);
		ic.setImagem("0006-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Sanduiche");
		ic.setDescricao("Sanduiche de peru");
		ic.setCalorias(BigDecimal.valueOf(220));
		ic.setNome("Sanduiche natural da casa");
		ic.setPreco(BigDecimal.valueOf(11.8));
		ic.setRestaurante(restaurantes[1]);
		ic.setDestaque(false);
		ic.setImagem("0007-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Bebida");
		ic.setDescricao("Refrigerante com gas");
		ic.setCalorias(BigDecimal.valueOf(137));
		ic.setNome("Refrigerante Tradicional");
		ic.setPreco(BigDecimal.valueOf(9));
		ic.setRestaurante(restaurantes[1]);
		ic.setDestaque(false);
		ic.setImagem("0004-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Bebida");
		ic.setDescricao("Suco natural");
		ic.setCalorias(BigDecimal.valueOf(75));
		ic.setNome("Suco de Laranja");
		ic.setPreco(BigDecimal.valueOf(9));
		ic.setRestaurante(restaurantes[1]);
		ic.setDestaque(false);
		ic.setImagem("0005-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Pizza");
		ic.setDescricao("Pizza saborosa com cebola");
		ic.setCalorias(BigDecimal.valueOf(200));
		ic.setNome("Pizza de calabresa");
		ic.setPreco(BigDecimal.valueOf(38.9));
		ic.setRestaurante(restaurantes[1]);
		ic.setDestaque(false);
		ic.setImagem("0002-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Japonesa");
		ic.setDescricao("Delicioso Uramaki tradicional");
		ic.setCalorias(BigDecimal.valueOf(180));
		ic.setNome("Uramaki");
		ic.setPreco(BigDecimal.valueOf(16.80));
		ic.setRestaurante(restaurantes[1]);
		ic.setDestaque(false);
		ic.setImagem("0003-comida.png");
		itemCardapioRepository.save(ic);
		
		ic.setCategoria("Sanduiche");
		ic.setDescricao("Delicioso sanduiche com molho");
		ic.setCalorias(BigDecimal.valueOf(250));
		ic.setNome("Double cheese Burguer Special");
		ic.setPreco(BigDecimal.valueOf(23.8));
		ic.setRestaurante(restaurantes[2]);
		ic.setDestaque(true);
		ic.setImagem("0001-comida.png");
		itemCardapioRepository.save(ic);
		
		 ic = new ItemCardapio();
		ic.setCategoria("Sanduiche");
		ic.setDescricao("Sanduiche padrão que mata a fome");
		ic.setCalorias(BigDecimal.valueOf(200));
		ic.setNome("Double cheese Burguer Simples");
		ic.setPreco(BigDecimal.valueOf(17.8));
		ic.setRestaurante(restaurantes[2]);
		ic.setDestaque(false);
		ic.setImagem("0006-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Sanduiche");
		ic.setDescricao("Sanduiche de peru");
		ic.setCalorias(BigDecimal.valueOf(220));
		ic.setNome("Sanduiche natural da casa");
		ic.setPreco(BigDecimal.valueOf(11.8));
		ic.setRestaurante(restaurantes[2]);
		ic.setDestaque(false);
		ic.setImagem("0007-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Bebida");
		ic.setDescricao("Refrigerante com gas");
		ic.setCalorias(BigDecimal.valueOf(137));
		ic.setNome("Refrigerante Tradicional");
		ic.setPreco(BigDecimal.valueOf(9));
		ic.setRestaurante(restaurantes[2]);
		ic.setDestaque(false);
		ic.setImagem("0004-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Bebida");
		ic.setDescricao("Suco natural");
		ic.setCalorias(BigDecimal.valueOf(75));
		ic.setNome("Suco de Laranja");
		ic.setPreco(BigDecimal.valueOf(9));
		ic.setRestaurante(restaurantes[2]);
		ic.setDestaque(false);
		ic.setImagem("0005-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Pizza");
		ic.setDescricao("Pizza saborosa com cebola");
		ic.setCalorias(BigDecimal.valueOf(200));
		ic.setNome("Pizza de calabresa");
		ic.setPreco(BigDecimal.valueOf(38.9));
		ic.setRestaurante(restaurantes[2]);
		ic.setDestaque(false);
		ic.setImagem("0002-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Japonesa");
		ic.setDescricao("Delicioso Uramaki tradicional");
		ic.setCalorias(BigDecimal.valueOf(180));
		ic.setNome("Uramaki");
		ic.setPreco(BigDecimal.valueOf(16.80));
		ic.setRestaurante(restaurantes[2]);
		ic.setDestaque(false);
		ic.setImagem("0003-comida.png");
		itemCardapioRepository.save(ic);
		
		ic.setCategoria("Sanduiche");
		ic.setDescricao("Delicioso sanduiche com molho");
		ic.setCalorias(BigDecimal.valueOf(250));
		ic.setNome("Double cheese Burguer Special");
		ic.setPreco(BigDecimal.valueOf(23.8));
		ic.setRestaurante(restaurantes[3]);
		ic.setDestaque(true);
		ic.setImagem("0001-comida.png");
		itemCardapioRepository.save(ic);
		
		 ic = new ItemCardapio();
		ic.setCategoria("Sanduiche");
		ic.setDescricao("Sanduiche padrão que mata a fome");
		ic.setCalorias(BigDecimal.valueOf(200));
		ic.setNome("Double cheese Burguer Simples");
		ic.setPreco(BigDecimal.valueOf(17.8));
		ic.setRestaurante(restaurantes[3]);
		ic.setDestaque(false);
		ic.setImagem("0006-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Sanduiche");
		ic.setDescricao("Sanduiche de peru");
		ic.setCalorias(BigDecimal.valueOf(220));
		ic.setNome("Sanduiche natural da casa");
		ic.setPreco(BigDecimal.valueOf(11.8));
		ic.setRestaurante(restaurantes[3]);
		ic.setDestaque(false);
		ic.setImagem("0007-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Bebida");
		ic.setDescricao("Refrigerante com gas");
		ic.setCalorias(BigDecimal.valueOf(137));
		ic.setNome("Refrigerante Tradicional");
		ic.setPreco(BigDecimal.valueOf(9));
		ic.setRestaurante(restaurantes[3]);
		ic.setDestaque(false);
		ic.setImagem("0004-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Bebida");
		ic.setDescricao("Suco natural");
		ic.setCalorias(BigDecimal.valueOf(75));
		ic.setNome("Suco de Laranja");
		ic.setPreco(BigDecimal.valueOf(9));
		ic.setRestaurante(restaurantes[3]);
		ic.setDestaque(false);
		ic.setImagem("0005-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Pizza");
		ic.setDescricao("Pizza saborosa com cebola");
		ic.setCalorias(BigDecimal.valueOf(200));
		ic.setNome("Pizza de calabresa");
		ic.setPreco(BigDecimal.valueOf(38.9));
		ic.setRestaurante(restaurantes[3]);
		ic.setDestaque(false);
		ic.setImagem("0002-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Japonesa");
		ic.setDescricao("Delicioso Uramaki tradicional");
		ic.setCalorias(BigDecimal.valueOf(180));
		ic.setNome("Uramaki");
		ic.setPreco(BigDecimal.valueOf(16.80));
		ic.setRestaurante(restaurantes[3]);
		ic.setDestaque(false);
		ic.setImagem("0003-comida.png");
		itemCardapioRepository.save(ic);
		
		ic.setCategoria("Sanduiche");
		ic.setDescricao("Delicioso sanduiche com molho");
		ic.setCalorias(BigDecimal.valueOf(250));
		ic.setNome("Double cheese Burguer Special");
		ic.setPreco(BigDecimal.valueOf(23.8));
		ic.setRestaurante(restaurantes[4]);
		ic.setDestaque(true);
		ic.setImagem("0001-comida.png");
		itemCardapioRepository.save(ic);
		
		 ic = new ItemCardapio();
		ic.setCategoria("Sanduiche");
		ic.setDescricao("Sanduiche padrão que mata a fome");
		ic.setCalorias(BigDecimal.valueOf(200));
		ic.setNome("Double cheese Burguer Simples");
		ic.setPreco(BigDecimal.valueOf(17.8));
		ic.setRestaurante(restaurantes[4]);
		ic.setDestaque(false);
		ic.setImagem("0006-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Sanduiche");
		ic.setDescricao("Sanduiche de peru");
		ic.setCalorias(BigDecimal.valueOf(220));
		ic.setNome("Sanduiche natural da casa");
		ic.setPreco(BigDecimal.valueOf(11.8));
		ic.setRestaurante(restaurantes[4]);
		ic.setDestaque(false);
		ic.setImagem("0007-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Bebida");
		ic.setDescricao("Refrigerante com gas");
		ic.setCalorias(BigDecimal.valueOf(137));
		ic.setNome("Refrigerante Tradicional");
		ic.setPreco(BigDecimal.valueOf(9));
		ic.setRestaurante(restaurantes[4]);
		ic.setDestaque(false);
		ic.setImagem("0004-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Bebida");
		ic.setDescricao("Suco natural");
		ic.setCalorias(BigDecimal.valueOf(75));
		ic.setNome("Suco de Laranja");
		ic.setPreco(BigDecimal.valueOf(9));
		ic.setRestaurante(restaurantes[4]);
		ic.setDestaque(false);
		ic.setImagem("0005-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Pizza");
		ic.setDescricao("Pizza saborosa com cebola");
		ic.setCalorias(BigDecimal.valueOf(200));
		ic.setNome("Pizza de calabresa");
		ic.setPreco(BigDecimal.valueOf(38.9));
		ic.setRestaurante(restaurantes[4]);
		ic.setDestaque(false);
		ic.setImagem("0002-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Japonesa");
		ic.setDescricao("Delicioso Uramaki tradicional");
		ic.setCalorias(BigDecimal.valueOf(180));
		ic.setNome("Uramaki");
		ic.setPreco(BigDecimal.valueOf(16.80));
		ic.setRestaurante(restaurantes[4]);
		ic.setDestaque(false);
		ic.setImagem("0003-comida.png");
		itemCardapioRepository.save(ic);
	}
		
	}	
	

