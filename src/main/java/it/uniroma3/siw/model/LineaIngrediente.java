package it.uniroma3.siw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class LineaIngrediente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long  id;
	
	@Column(nullable = false)
	private double quantita;
	
	@ManyToOne
	private Ingrediente ingrediente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getQuantita() {
		return quantita;
	}

	public void setQuantita(double d) {
		this.quantita = d;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}
	
	
}
