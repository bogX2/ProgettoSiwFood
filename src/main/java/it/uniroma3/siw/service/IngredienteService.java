package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.repository.IngredienteRepository;

@Service
public class IngredienteService {
	
	@Autowired
	IngredienteRepository ingredienteRepository;
	
	@Transactional
	public Ingrediente findById(Long id) {
		return ingredienteRepository.findById(id).get();
	}

	@Transactional
	public Iterable<Ingrediente> findAll() {
		return ingredienteRepository.findAll();
	}

	@Transactional
	public void save(Ingrediente ingrediente) {
		// TODO Auto-generated method stub
		ingredienteRepository.save(ingrediente);
	}
	
	@Transactional
	public Ingrediente findByNome(String nome) {
		return this.ingredienteRepository.findByNome(nome);
	}


}
