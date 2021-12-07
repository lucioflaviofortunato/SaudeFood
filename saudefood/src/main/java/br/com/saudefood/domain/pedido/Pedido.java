package br.com.saudefood.domain.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.saudefood.domain.cliente.Cliente;
import br.com.saudefood.domain.restaurante.Restaurante;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable{
	
	public enum Status{
		Producao(1, "Em produção", false),
		Entrega(2,"Saiu para Entrega", false),
		Concluido(3, "Concluido", true);
		
		Status(int ordem, String descricao, boolean ultimo){
			this.ordem = ordem;
			this.descricao = descricao;
			this.ultimo = ultimo;
		}
		
		int ordem;
		String descricao;
		boolean ultimo;
	}
	
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	@NotNull
	private LocalDateTime data;
	
	@NotNull
	private Status status;
	
	@NotNull
	@ManyToOne
	private Cliente cliente;
	
	@NotNull
	@ManyToOne
	private Restaurante restaurante;
	
	@NotNull
	private BigDecimal subTotal;
	
	@NotNull
	@Column(name = "taxa_entrega")
	private BigDecimal taxaEntrega;
	
	@NotNull
	private BigDecimal total;
	
	@OneToMany(mappedBy = "id.pedido")
	private Set<ItemPedido> itens;
	
	

}
