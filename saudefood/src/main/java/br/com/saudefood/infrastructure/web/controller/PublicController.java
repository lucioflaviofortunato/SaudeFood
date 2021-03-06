package br.com.saudefood.infrastructure.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.saudefood.application.service.ClienteService;
import br.com.saudefood.application.service.RestauranteService;
import br.com.saudefood.domain.cliente.Cliente;
import br.com.saudefood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.saudefood.domain.restaurante.Restaurante;

@Controller
@RequestMapping(path ="/public")
public class PublicController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@GetMapping("/cliente/new")
	public String newCliente(Model model) {				
		model.addAttribute("cliente",new Cliente());
		controllerHelper.setEditMode(model, false);
		return "cliente-cadastro";
		
	}@GetMapping("/restaurante/new")
	public String newRestaurante(Model model) {				
		model.addAttribute("restaurante",new Restaurante());
		controllerHelper.setEditMode(model, false);
		controllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		return "restaurante-cadastro";
	}
	
	@PostMapping(path="/cliente/save")
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
		
		controllerHelper.setEditMode(model, false);		
		return "cliente-cadastro";
	}
	
	@PostMapping(path="/restaurante/save")
	public String saveRestaurante(@ModelAttribute("restaurante") @Valid Restaurante restaurante,
			Errors errors,
			Model model ) {
		
		if(!errors.hasErrors()) {
			
			try {
				restauranteService.saveRestaurante(restaurante);
				model.addAttribute("msg", "Restaurante gravado com sucesso");
			} catch (br.com.saudefood.application.service.ValidationException e) {
				errors.rejectValue("email", null, e.getMessage());
				
			}
			
			
			
		}
		
		controllerHelper.setEditMode(model, false);		
		controllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		return "restaurante-cadastro";
	}
	


}
