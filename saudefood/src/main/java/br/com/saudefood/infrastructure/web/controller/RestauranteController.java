package br.com.saudefood.infrastructure.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.saudefood.application.service.RelatorioService;
import br.com.saudefood.application.service.RestauranteService;
import br.com.saudefood.domain.pedido.Pedido;
import br.com.saudefood.domain.pedido.PedidoRepository;
import br.com.saudefood.domain.pedido.RelatorioItemFaturamento;
import br.com.saudefood.domain.pedido.RelatorioItemFilter;
import br.com.saudefood.domain.pedido.RelatorioPedidoFilter;
import br.com.saudefood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.saudefood.domain.restaurante.ItemCardapio;
import br.com.saudefood.domain.restaurante.ItemCardapioRepository;
import br.com.saudefood.domain.restaurante.Restaurante;
import br.com.saudefood.domain.restaurante.RestauranteRepository;
import br.com.saudefood.util.SecurityUtils;

@Controller()
@RequestMapping(path = "/restaurante")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private ItemCardapioRepository itemCardapioRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private RelatorioService relatorioService;
	

	@GetMapping(path = "/home")
	public String home(Model model) {
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		List<Pedido> pedidos = pedidoRepository.findByRestaurante_IdOrderByDataDesc(restauranteId);
		model.addAttribute("pedidos", pedidos);
		
		
		return "restaurante-home";
	}
	
	@GetMapping("/edit")
	public String edit(Model model) {
		
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
		model.addAttribute("restaurante",restaurante);
		
		controllerHelper.setEditMode(model, true);
		controllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		return "restaurante-cadastro";
	}	
	
	@PostMapping("/save")
	public String saveRestaurante(@ModelAttribute("restaurante") @Valid Restaurante restaurante,
			Errors errors,
			Model model ) {
		
		if(!errors.hasErrors()) {
			
			try {
				restauranteService.saveRestaurante(restaurante);
				model.addAttribute("msg", "Resturante gravado com sucesso");
			} catch (br.com.saudefood.application.service.ValidationException e) {
				errors.rejectValue("email", null, e.getMessage());
				
			}
			
			
			
		}
		
		controllerHelper.setEditMode(model,true);		
		controllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		return "restaurante-cadastro";
	}
	
	@GetMapping("/comidas")
	public String viewComidas(Model model) {
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
		model.addAttribute("restaurante",restaurante);
		
		List<ItemCardapio> itensCardapio =  itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
		
		model.addAttribute("itensCardapio", itensCardapio);
		
		model.addAttribute("itemCardapio", new ItemCardapio());
		
		return "restaurante-comidas";		
		}
		
		@RequestMapping("/comidas/remover")
		public String remover(@RequestParam("itemId") Integer itemId, Model model) {
			
			itemCardapioRepository.deleteById(itemId);
			return "redirect:/restaurante/comidas";
		}
		
		@PostMapping("/comidas/cadastrar")
		public String cadastrar(
				@Valid @ModelAttribute("itemCardapio") ItemCardapio itemCardapio,
				Errors errors,
				Model model) {
			 
			if(errors.hasErrors()) {
				Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
				Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
				model.addAttribute("restaurante",restaurante);
				
				List<ItemCardapio> itensCardapio =  itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
				
				model.addAttribute("itensCardapio", itensCardapio);
				
				return "restaurante-comidas";		
				
			}
			
			restauranteService.saveItemCardapio(itemCardapio);
			return "redirect:/restaurante/comidas";
		}
		
		@GetMapping("/pedido")
		public String viewPedido(
			@RequestParam("pedidoId") Integer pedidoId,
			Model model) {
			
			Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
			model.addAttribute("pedido", pedido);
			
			return "restaurante-pedido";
		}
		
		@PostMapping("/pedido/proximoStatus")
		public String proximoStatus(@RequestParam("pedidoId") Integer pedidoId, Model model) {
			
			Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();			
			pedido.definirProximoStatus();
			pedidoRepository.save(pedido);
			model.addAttribute("pedido", pedido);
			model.addAttribute("msg", "Status alterado com sucesso!" );
			
			return "restaurante-pedido";
		}
		
		@GetMapping("/relatorio/pedidos")
		public String relatorioPedidos(
				@ModelAttribute("relatorioPedidoFilter") RelatorioPedidoFilter filter,
				Model model) {
			Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
			List<Pedido> pedidos = relatorioService.listPedidos(restauranteId, filter);
			model.addAttribute("pedidos", pedidos);
			
			
			model.addAttribute("filter", filter);
			
			return "restaurante-relatorio-pedidos";
		}
		@GetMapping("/relatorio/itens")
		public String relatorioItens(
				@ModelAttribute("relatorioItemFilter") RelatorioItemFilter filter,
				Model model) {
			Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
			 List<ItemCardapio> itensCardapio =  itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
		     model.addAttribute("itenscardapio", itensCardapio);
		     
		     List<RelatorioItemFaturamento> itensCalculados = relatorioService.calcularFaturamentoItens(restauranteId, filter);
		     model.addAttribute("itensCalculados", itensCalculados);
			 
			model.addAttribute("relatorioItemFilter", filter);
			return "restaurante-relatorios-itens";
		}

}
