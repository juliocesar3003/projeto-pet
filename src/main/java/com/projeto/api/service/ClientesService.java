package com.projeto.api.service;
import java.util.ArrayList;
import java.util.List;
import com.projeto.api.DTO.Requests.Reponses.ClienteResponse;
import com.projeto.api.entidades.entidadesAnimais.Cachorro;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import com.projeto.api.entidades.Clientes;
import com.projeto.api.repository.ClienteRepository;
import com.projeto.api.infra.exception.exceptions.ResourceNotFoundException;



@Service
public class ClientesService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private EmpresaService empresaService;


	public List<ClienteResponse> findAll(JwtAuthenticationToken token){

		Empresa empresa = empresaService.autenticarToken(token);

			List<Clientes> listaClients = repository.findByEmpresaAssociadaId(empresa.getId());

			return converterListaEmDto(listaClients);


	}


	private List<ClienteResponse> converterListaEmDto(List<Clientes> lista) {
		List<ClienteResponse> listaDto = new ArrayList<>();
		for(Clientes i : lista) {
			ClienteResponse dto =  converterClienteEmDto(i);
			listaDto.add(dto);
		}
		return listaDto;

	}

	private ClienteResponse converterClienteEmDto(Clientes i) {
		List<Cachorro> pets = new ArrayList<>();
		if(i.getPet() instanceof Cachorro){
			 pets =  i.getPet();
		}
	   ClienteResponse dto = new ClienteResponse(i.getNome(), i.getCelular(),i.getEndereco(),pets,i.getAgendamento());
		return dto;

	}

	public ClienteResponse findByid(Long id,JwtAuthenticationToken token) {

		Empresa empresa = empresaService.autenticarToken(token);

		 Clientes cliente = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));

		 cliente = verificarClienteAssociado(cliente,empresa);
			return converterClienteEmDto(cliente);

	}
	
	public Clientes insert(Clientes obj, JwtAuthenticationToken token) {

		 Empresa empresa = empresaService.autenticarToken(token);

		 Clientes cliente = CriarData(obj,empresa);

		return repository.save(cliente);
	}



	public void delete(Long id,JwtAuthenticationToken token) {

			Empresa empresa = empresaService.autenticarToken(token);

			Clientes cliente = repository.findById(id).orElseThrow(
					()-> new ResourceNotFoundException(id));

			cliente = verificarClienteAssociado(cliente,empresa);

				repository.deleteById(cliente.getId());
	}

	public ClienteResponse update (Long id ,Clientes obj, JwtAuthenticationToken token) {

			Empresa empresa = empresaService.autenticarToken(token);

			Clientes cliente = repository.getReferenceById(id);

			cliente = verificarClienteAssociado(cliente,empresa);

			cliente = updateData(obj,cliente);

			repository.save(cliente);

			ClienteResponse response = converterClienteEmDto(cliente);

			return response;
	}

	private Clientes CriarData(Clientes obj,Empresa empresa ) {
		Clientes cliente = new Clientes();
		cliente.setNome(obj.getNome());
		cliente.setCelular(obj.getCelular());
		cliente.setEndereco(obj.getEndereco());
		cliente.setEmpresaAssociada(empresa);
		return cliente;
	}

	private Clientes updateData(Clientes obj, Clientes cliente) {
		cliente.setNome(obj.getNome());
		cliente.setCelular(obj.getCelular());
		cliente.setEndereco(obj.getEndereco());

		return cliente;
	}

	public Clientes verificarClienteAssociado(Clientes clientes, Empresa empresa) {
		return empresaService.verificarAssociacaoComEmpresa(clientes, empresa, Clientes::getEmpresaAssociada);
	}
}
