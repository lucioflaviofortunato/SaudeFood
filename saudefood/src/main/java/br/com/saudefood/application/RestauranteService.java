package br.com.saudefood.application;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.saudefood.domain.cliente.Restaurante;
import br.com.saudefood.domain.cliente.ClienteRepository;

public class RestauranteService {
	@Autowired
	private ClienteRepository clienteRepository;
	
	public void saveRestaurante(Restaurante restaurante) throws ValidationException {
		if (!validateEmail(restaurante.getEmail(), restaurante.getId())) {
			throw new ValidationException("O e-mail está duplicado");
		}
		
		if(restaurante.getId() != null) {
			Restaurante clienteDB = clienteRepository.findById(restaurante.getId()).orElseThrow();
			restaurante.setSenha(clienteDB.getSenha());
		}else {
			restaurante.encryptPassword();
		}
		
		clienteRepository.save(restaurante);
	}
	
	private boolean validateEmail(String email, Integer id) {		
		Restaurante cliente = clienteRepository.findByEmail(email);
		
		if(cliente != null){
			if(id == null) {
				return false;
			}
			
			if(!cliente.getId().equals(id)) {
				return false;
			}
		}
		
		
		return true;
	}
}

