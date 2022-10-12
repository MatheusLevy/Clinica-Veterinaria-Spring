package com.produtos.apirest.service;

import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.repository.EspecialidadeRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadeService {

    @Autowired
    public EspecialidadeRepo repo;

    public static void verificaEspecialidade(Especialidade especialidade){
        if(especialidade == null)
            throw new NullPointerException("Especialidade não pode ser Nula!");
        if(especialidade.getNome() == null || especialidade.getNome().equals(""))
            throw new RegraNegocioRunTime("Especialidade deve ter um nome");
        if(especialidade.getArea() == null)
            throw new RegraNegocioRunTime("Especialidade deve ter uma Area!");
    }

    public static void verificaId(Especialidade especialidade){
        if(especialidade == null || Long.valueOf(especialidade.getEspecialidadeId()) == null)
            throw new RegraNegocioRunTime("Especialidade deve ter um identificador!s");
    }

    @Transactional
    public Especialidade salvar(Especialidade especialidade){
        verificaEspecialidade(especialidade);
        return repo.save(especialidade);
    }

    @Transactional
    public Especialidade atualizar(Especialidade especialidade){
        verificaEspecialidade(especialidade);
        verificaId(especialidade);
        return repo.save(especialidade);
    }

    @Transactional
    public void remover(Especialidade especialidade){
        verificaEspecialidade(especialidade);
        verificaId(especialidade);
        repo.delete(especialidade);
    }

    @Transactional
    public Especialidade removerFeedback(Especialidade especialidade){
        verificaEspecialidade(especialidade);
        verificaId(especialidade);
        Optional<Especialidade> especialidadeRemover = repo.findById(especialidade.getEspecialidadeId());
        Especialidade especialidadeTemp = especialidadeRemover.get();
        repo.delete(especialidadeTemp);
        return especialidadeTemp;
    }

    @Transactional
    public Especialidade buscarEspecialidadePorId(Especialidade especialidade){
        verificaId(especialidade);
        return repo.findById(especialidade.getEspecialidadeId()).get();
    }

    @Transactional
    public List<Especialidade> buscar(Especialidade filtro){
        if (filtro == null)
            throw new NullPointerException("Filtro não pode ser nulo");
        Example<Especialidade> example = Example.of(filtro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repo.findAll(example);
    }

    @Transactional
    public List<Especialidade> buscarTodos(){
        return repo.findAll();
    }

}
