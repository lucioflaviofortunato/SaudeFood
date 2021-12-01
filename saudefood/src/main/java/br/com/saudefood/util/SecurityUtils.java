package br.com.saudefood.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.saudefood.domain.cliente.Cliente;
import br.com.saudefood.domain.restaurante.Restaurante;
import br.com.saudefood.infrastructure.web.security.LoggedUser;

public class SecurityUtils {
	
	public static LoggedUser loggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication instanceof AnonymousAuthenticationToken) {
			return null;
		}
		
		return (LoggedUser) authentication.getPrincipal();
	}
	
	public static Cliente loggedCliente() {
		LoggedUser loggedUser = loggedUser();
		
		if(loggedUser == null) {
			throw new IllegalStateException("Não existe um usuario logado");
		}
		
		if(!(loggedUser.getUsuario() instanceof Cliente)) {
			throw new IllegalStateException("O usuario logado não é um cliente");
		}
		
		return (Cliente) loggedUser.getUsuario();
	}
	
	public static Restaurante loggedRestaurante() {
		LoggedUser loggedUser = loggedUser();
		
		if(loggedUser == null) {
			throw new IllegalStateException("Não existe um usuario logado");
		}
		
		if(!(loggedUser.getUsuario() instanceof Restaurante)) {
			throw new IllegalStateException("O usuario logado não é um Restaurante");
		}
		
		return (Restaurante) loggedUser.getUsuario();
	}

}
