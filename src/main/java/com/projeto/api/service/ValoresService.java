package com.projeto.api.service;

import com.projeto.api.DTO.Reponses.ValoresResponse;
import com.projeto.api.DTO.Request.ValoresServicoRequest;
import com.projeto.api.entidades.Servicos.Servico;
import com.projeto.api.entidades.Servicos.ValoresPorServico;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import com.projeto.api.infra.exception.exceptions.DataBaseException;
import com.projeto.api.infra.exception.exceptions.ResourceNotFoundException;
import com.projeto.api.repository.ValoresServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValoresService {

    @Autowired
    private ValoresServicoRepository repository;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private ServicoService serivcoService;

    public List<ValoresResponse> findAllResponse(JwtAuthenticationToken token){

        Empresa empresa = empresaService.autenticarToken(token);

        List<ValoresPorServico> lista = repository.findByEmpresaAssociadaId(empresa.getId());

        return transformeListResponse(lista);

    }

    public List<ValoresPorServico> findAll(JwtAuthenticationToken token){

        Empresa empresa = empresaService.autenticarToken(token);

        List<ValoresPorServico> lista = repository.findByEmpresaAssociadaId(empresa.getId());

        return lista;

    }

    public ValoresResponse findByIdResponse(Long id,JwtAuthenticationToken token) {

        Empresa empresa = empresaService.autenticarToken(token);

        ValoresPorServico obj = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        obj = verificarValorAssociado(obj,empresa);

        return trasformeResponse(obj);

    }

    public ValoresPorServico findById(Long id,JwtAuthenticationToken token) {

        Empresa empresa = empresaService.autenticarToken(token);

        ValoresPorServico obj = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        obj = verificarValorAssociado(obj,empresa);

        return obj;

    }

    public void insert(ValoresServicoRequest obj, JwtAuthenticationToken token){

        Empresa empresa = empresaService.autenticarToken(token);

        ValoresPorServico valor = criarObj(obj,empresa,token);

        repository.save(valor);
    }

    public void delete(Long id,JwtAuthenticationToken token) {
        try {
            Empresa empresa = empresaService.autenticarToken(token);

            ValoresPorServico valor = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));

            valor = verificarValorAssociado(valor,empresa);

            repository.delete(valor);

        }
        catch(DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public void update(Long id, ValoresPorServico obj,JwtAuthenticationToken token) {

        Empresa empresa = empresaService.autenticarToken(token);

        ValoresPorServico valor = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));

        valor = verificarValorAssociado(valor,empresa);

        valor = updateObj(valor,obj);

        repository.save(valor);


    }

    @NonNull
    private ValoresPorServico criarObj(@NonNull ValoresServicoRequest obj, Empresa empresa, JwtAuthenticationToken token) {

        ValoresPorServico objConcreto = new ValoresPorServico();

        Servico servico = serivcoService.findByIdInterno(obj.Idservico(),token);

        objConcreto.setPorte(obj.porte());
        objConcreto.setPelagem(obj.pelagem());
        objConcreto.setServico(servico);
        objConcreto.setValor(obj.valor());
        objConcreto.setEmpresaAssociada(empresa);

        return objConcreto;
    }

    private ValoresPorServico updateObj(ValoresPorServico valor, ValoresPorServico obj) {
        valor.setServico(obj.getServico());
        valor.setPorte(obj.getPorte());
        valor.setPelagem(obj.getPelagem());
        valor.setValor(obj.getValor());

        return valor;
    }

    public ValoresPorServico verificarValorAssociado(ValoresPorServico valor, Empresa empresa) {
        return empresaService.verificarAssociacaoComEmpresa(valor, empresa, ValoresPorServico::getEmpresaAssociada);
    }

    public List<ValoresResponse> transformeListResponse(List<ValoresPorServico> lista) {

        List<ValoresResponse> listResponse = new ArrayList<>();
        for(ValoresPorServico i : lista){
            ValoresResponse response = trasformeResponse(i);
            listResponse.add(response);
        }
        return listResponse;
    }

    private ValoresResponse trasformeResponse(ValoresPorServico i) {
        ValoresResponse response =
        new ValoresResponse(i.getPorte().toString(),
                            i.getPelagem().toString(),
                            i.getServico().getNome(),
                            i.getValor());

        return response;
    }


}
