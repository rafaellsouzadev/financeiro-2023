package com.rafael.financeiro.services;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafael.financeiro.model.Categoria;
import com.rafael.financeiro.model.Lancamento;
import com.rafael.financeiro.model.Pessoa;
import com.rafael.financeiro.repository.CategoriaRepository;
import com.rafael.financeiro.repository.LancamentoRepository;
import com.rafael.financeiro.repository.PessoaRepository;
import com.rafael.financeiro.services.exceptions.ObjectNotFoundException;
import com.rafael.financeiro.services.exceptions.PessoaInexistenteOuInativaException;


@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
	public Lancamento atualizar(Long id, Lancamento lancamento) {
		Lancamento lancamentoSalva = buscarLancamentoPeloId(id);
		
		BeanUtils.copyProperties(lancamento, lancamentoSalva, "id");
		return lancamentoRepository.save(lancamentoSalva);
	}
	
	
	public Lancamento buscarLancamentoPeloId(Long id) {
		Lancamento lancamentoSalva = this.lancamentoRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Lancamento não encontrato!" + Lancamento.class.getName()));
		return lancamentoSalva;
	}
	
	public Lancamento salvar(Lancamento lancamento) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getId());
		if (pessoa.isEmpty() || pessoa.get().isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		Optional<Categoria> categoria = categoriaRepository.findById(lancamento.getCategoria().getId());
		if (categoria.isEmpty()) {
			throw new ObjectNotFoundException("Categoria não encontrato!" + Categoria.class.getName());
		}
		
		return lancamentoRepository.save(lancamento);
	}

}
