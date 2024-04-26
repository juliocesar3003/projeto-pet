package com.projeto.api.service;

import java.io.Console;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projeto.api.entidades.Agendamentos;
import com.projeto.api.entidades.Animais;
import com.projeto.api.entidades.Clientes;
import com.projeto.api.entidades.TipoServicos;
import com.projeto.api.repository.AgendamentosRepository;
import com.projeto.api.resource.dto.AgendamentosDTO;
import com.projeto.api.resource.dto.ExibirAgendamentoDTO;
import com.projeto.api.resource.dto.ExibirServicosDto;
import com.projeto.api.resource.dto.InforServicosDto;
import com.projeto.api.service.exception.RegraNegocioExceptions;
import com.projeto.api.service.exceptions.DataBaseException;
import com.projeto.api.service.exceptions.ResourceNotFoundException;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;

@Service
public class AgendamentosService {

	@Autowired
	private AgendamentosRepository repository;
	
	@Autowired
	private AnimaisService animaisService;
	
	@Autowired
	private ClientesService clienteService;
	
	@Autowired
	private TipoServicoService ServicoService;
	
	@PersistenceContext
    private EntityManager entityManager;

    public Agendamentos findByIdWithServicos(Long id) {
        return entityManager.createQuery(
                "SELECT DISTINCT a FROM Agendamentos a " +
                "LEFT JOIN FETCH a.servicos " +
                "WHERE a.id = :id", Agendamentos.class)
                .setParameter("id", id)
                .getSingleResult();
    }

	
	public List<Agendamentos> findAll(){
		return repository.findAll();
	}
	
	public Agendamentos findByid(Long id) {
		Optional<Agendamentos> obj = repository.findById(id);
		return obj.get();
		
	}
	public ExibirAgendamentoDTO findById(Long id) {
	    Optional<Agendamentos> objOptional = repository.findById(id);
	    Agendamentos obj = objOptional.orElseThrow(() -> new ResourceNotFoundException(id));
	    return converterobj(obj);
	    
	}
	
	private ExibirAgendamentoDTO converterobj(Agendamentos obj) {
		
	ExibirAgendamentoDTO Dto = new ExibirAgendamentoDTO();
	
	return	Dto.builder()
		.NumeroCelular(obj.getCliente().getCelular())
		  .nomeAnimal(obj.getAnimal()
			.getNome())
		     .nomeCliente(obj.getCliente().getNome())
		      .servicos(converter(obj.getServicos()))
		       .date(obj.getDate())
		        .ValorTotal(obj.getValorTotal()).build();
		
	}

	

	private List<ExibirServicosDto> converter(List<TipoServicos> servicos) {
		return servicos.stream().map(
				servico -> ExibirServicosDto.builder()
				.NomeServico(servico.getServico())
				.Valor(servico.getValor())
				.build()
				).collect(Collectors.toList());
				
	}

	public Agendamentos insert(AgendamentosDTO obj){
		Agendamentos agendamento = new Agendamentos();
		Animais objAnimal =  animaisService.findById(obj.getIdAnimal());
		agendamento.setDate(obj.getData());
		agendamento.setAnimal(objAnimal);
		agendamento.setCliente(objAnimal.getCliente());
		List<TipoServicos> lista = addservico(obj.getServicos());
	    agendamento.getServicos().addAll(lista);
		repository.save(agendamento);
		return agendamento;
		}
	 
	


	private List<TipoServicos> addservico( List<InforServicosDto> servicos) {
		List<TipoServicos> lista = new ArrayList<>();
		
		for(InforServicosDto i : servicos) {
			TipoServicos servico = ServicoService.findById(i.getNumeroServico());
			lista.add(servico);
		}
		
		return lista;
	}


	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}		
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
		
	}
	
	public Agendamentos update(Long id, Agendamentos obj) {
		try {
			Agendamentos agendamento = repository.getReferenceById(id);
			updateData(agendamento,obj);
			return repository.save(agendamento);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Agendamentos agendamento, Agendamentos obj) {
		agendamento.setDate(obj.getDate());
		agendamento.setServicos(obj.getServicos());
		
	}

	}

