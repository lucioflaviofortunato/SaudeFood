package br.com.saudefood.infrastructure.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.saudefood.application.service.ClienteService;
import br.com.saudefood.application.service.RestauranteService;
import br.com.saudefood.domain.cliente.Cliente;
import br.com.saudefood.domain.cliente.ClienteRepository;
import br.com.saudefood.domain.pedido.Pedido;
import br.com.saudefood.domain.pedido.PedidoRepository;
import br.com.saudefood.domain.restaurante.CategoriaRestaurante;
import br.com.saudefood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.saudefood.domain.restaurante.ItemCardapio;
import br.com.saudefood.domain.restaurante.ItemCardapioRepository;
import br.com.saudefood.domain.restaurante.Restaurante;
import br.com.saudefood.domain.restaurante.RestauranteRepository;
import br.com.saudefood.domain.restaurante.SearchFilter;
import br.com.saudefood.util.SecurityUtils;

@Controller
@RequestMapping(path = "/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private ItemCardapioRepository itemCardapioRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@GetMapping(path = "/home")
	public String home(Model model) {
		List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome"));
		model.addAttribute("categorias", categorias);
		model.addAttribute("searchFilter", new SearchFilter());		
		
		List<Pedido> pedidos = pedidoRepository.listPedidosByCliente(SecurityUtils.loggedCliente().getId());
		model.addAttribute("pedidos", pedidos);
		
		
		
		return "cliente-home";
	}
	
	//metodo para colocar os graficos na tela posteriormente!
	@GetMapping("/edit")
	public String edit(Model model) {
		
		Integer clienteId = SecurityUtils.loggedCliente().getId();
		Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
		model.addAttribute("cliente",cliente);
		controllerHelper.setEditMode(model, true);
		return "cliente-cadastro";
	}
	
	@PostMapping("/save")
	public String saveCliente(@ModelAttribute("cliente") @Valid Cliente cliente,
			Errors errors,
			Model model ) {
		
		if(!errors.hasErrors()) {
			
			try {
				clienteService.saveCliente(cliente);
				model.addAttribute("msg", "Cliente gravado com sucesso");
			} catch (br.com.saudefood.application.service.ValidationException e) {
				errors.rejectValue("email", null, e.getMessage());
				
			}
			
			
			
		}
		
		controllerHelper.setEditMode(model,true);		
		return "cliente-cadastro";
	}
	
	@GetMapping(path= "/search")
		public String search(
				@ModelAttribute("searchFilter")SearchFilter filter,
				@RequestParam(value="cmd", required = false) String command,
				Model model) {
		
		filter.processFilter(command);
		
		
		List<Restaurante> restaurantes = restauranteService.search(filter);
		model.addAttribute("restaurantes",restaurantes);
		
		controllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);	
		
		model.addAttribute("searchFilter", filter);
		model.addAttribute("cep", SecurityUtils.loggedCliente().getCep());
			return "cliente-busca";
		}
	
	@GetMapping(path = "/restaurante")
	public String viewRestaurante(
			@RequestParam("restauranteId") Integer restaurantId,
			@RequestParam(value = "categoria", required = false) String categoria,
			Model model) {
		
		
		Restaurante restaurante = restauranteRepository.findById(restaurantId).orElseThrow();
		model.addAttribute("restaurante", restaurante);		
		model.addAttribute("cep", SecurityUtils.loggedCliente().getCep());
		
		List<String> categorias = itemCardapioRepository.findCategorias(restaurantId);
		model.addAttribute("categorias", categorias);
		
		List<ItemCardapio> itensCardapioDestaque;
		List<ItemCardapio> itensCardapioNaoDestaque;
		
		
		if(categoria == null) {
			itensCardapioDestaque = itemCardapioRepository.findByRestaurante_IdAndDestaqueOrderByNome(restaurantId, true);		
			itensCardapioNaoDestaque = itemCardapioRepository.findByRestaurante_IdAndDestaqueOrderByNome(restaurantId, false);				
			}else {
			itensCardapioDestaque = itemCardapioRepository.findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(restaurantId, true,categoria);		
			itensCardapioNaoDestaque = itemCardapioRepository.findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(restaurantId, false,categoria);				
			}
		model.addAttribute("itensCardapioDestaque", itensCardapioDestaque);
		model.addAttribute("itensCardapioNaoDestaque", itensCardapioNaoDestaque);
		model.addAttribute("categoriaSelecionada", categoria);
		
		return "cliente-restaurante";
	}
	
	}
	

