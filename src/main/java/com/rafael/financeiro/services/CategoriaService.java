package com.rafael.financeiro.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafael.financeiro.model.Categoria;
import com.rafael.financeiro.repository.CategoriaRepository;
import com.rafael.financeiro.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	public CategoriaRepository categoriaRepository;
	
	public Categoria find(Long id) {
		Optional<Categoria> cat = categoriaRepository.findById(id);
		
		return cat.orElseThrow(() -> new ObjectNotFoundException("Acesso não encontrato!" + Categoria.class.getName()));
	}
}
