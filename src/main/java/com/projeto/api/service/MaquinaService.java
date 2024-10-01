package com.projeto.api.service;

import com.projeto.api.DTO.Reponses.MaquinaResponse;
import com.projeto.api.entidades.sobreAgendamento.MaquinaCartao;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import com.projeto.api.infra.exception.exceptions.ResourceNotFoundException;
import com.projeto.api.repository.MaquinaCartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaquinaService {

    @Autowired
    private MaquinaCartaoRepository repository;

    @Autowired
    private EmpresaService empresaService;


    public List<MaquinaResponse> findAll(JwtAuthenticationToken token){

        Empresa empresa = empresaService.autenticarToken(token);

        List<MaquinaCartao> listaMaquina = repository.findByEmpresaAssociadaId(empresa.getId());

        return converterListaEmDto(listaMaquina);


    }

    private List<MaquinaResponse> converterListaEmDto(List<MaquinaCartao> lista) {
        List<MaquinaResponse> listaDto = new ArrayList<>();
        for(MaquinaCartao i : lista) {
            MaquinaResponse dto =  converterMaquinaEmDto(i);
            listaDto.add(dto);
        }
        return listaDto;

    }

    private MaquinaResponse converterMaquinaEmDto(MaquinaCartao i) {
        MaquinaResponse dto = new MaquinaResponse(i.getNome(), i.getTaxaDamaquininha(), i.getValorPorTaxa());
        return dto;

    }

    public MaquinaResponse findByidResponse(Long id,JwtAuthenticationToken token) {

        Empresa empresa = empresaService.autenticarToken(token);

        MaquinaCartao maquinaCartao = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));

        maquinaCartao = verificarMaquinaAssociado(maquinaCartao,empresa);
        return converterMaquinaEmDto(maquinaCartao);

    }

    public MaquinaCartao findByid(Long id,JwtAuthenticationToken token) {

        Empresa empresa = empresaService.autenticarToken(token);

        MaquinaCartao maquinaCartao = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));

        maquinaCartao = verificarMaquinaAssociado(maquinaCartao,empresa);
        return maquinaCartao;

    }

    public MaquinaCartao insert(MaquinaCartao obj, JwtAuthenticationToken token) {

        Empresa empresa = empresaService.autenticarToken(token);

        MaquinaCartao maquina = CriarData(obj,empresa);

        return repository.save(maquina);
    }



    public void delete(Long id,JwtAuthenticationToken token) {

        Empresa empresa = empresaService.autenticarToken(token);

        MaquinaCartao maquina = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(id));

        maquina = verificarMaquinaAssociado(maquina,empresa);

        repository.deleteById(maquina.getId());
    }

    public void update (Long id ,MaquinaCartao obj, JwtAuthenticationToken token) {

        Empresa empresa = empresaService.autenticarToken(token);

        MaquinaCartao maquina = repository.getReferenceById(id);

        maquina = verificarMaquinaAssociado(maquina,empresa);

        maquina = updateData(obj,maquina);

        repository.save(maquina);


    }

    private MaquinaCartao CriarData(MaquinaCartao obj,Empresa empresa ) {
        MaquinaCartao maquina = new MaquinaCartao();
        maquina.setNome(obj.getNome());
        maquina.setTaxaDamaquininha(obj.getTaxaDamaquininha());
        maquina.setValorPorTaxa(obj.getValorPorTaxa());
        maquina.setEmpresaAssociada(empresa);
        return maquina;
    }

    private MaquinaCartao updateData(MaquinaCartao obj, MaquinaCartao maquina) {
        maquina.setNome(obj.getNome());
        maquina.setTaxaDamaquininha(obj.getTaxaDamaquininha());
        maquina.setValorPorTaxa(obj.getValorPorTaxa());

        return maquina;
    }

    public MaquinaCartao verificarMaquinaAssociado(MaquinaCartao maquina, Empresa empresa) {
        return empresaService.verificarAssociacaoComEmpresa(maquina, empresa, MaquinaCartao::getEmpresaAssociada);
    }
}
