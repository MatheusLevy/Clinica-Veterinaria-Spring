package com.produtos.apirest.service;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.repository.DonoRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DonoService {

    @Autowired
    public DonoRepo repo;

    public static void verificaDono(Dono dono){
        if(dono == null)
            throw new NullPointerException("Dono não pode ser Nulo!");
        if(dono.getNome() == null || dono.getNome().equals(""))
            throw new RegraNegocioRunTime("Dono deve ter um nome!");
        if(dono.getCpf() == null || dono.getCpf().equals(""))
            throw new RegraNegocioRunTime("Dono deve ter um CPF!");
    }

    public static void verificaId(Dono dono){
        if(dono == null || Long.valueOf(dono.getDonoId()) == null)
            throw new RegraNegocioRunTime("Dono deve ter um indentificador!");
    }

    public static void verificaId(Long id){
        if(Long.valueOf(id) == null)
            throw new RegraNegocioRunTime("Dono deve ter um indentificador!");
    }

    @Transactional
    public Dono salvar(Dono Dono){
        verificaDono(Dono);
        return repo.save(Dono);
    }

    @Transactional
    public Dono atualizar(Dono Dono){
        verificaDono(Dono);
        verificaId(Dono);
        return repo.save(Dono);
    }

    @Transactional
    public void remover(Dono Dono){
        verificaDono(Dono);
        verificaId(Dono);
        repo.delete(Dono);
    }

    @Transactional
    public void removerPorId(Long id){
        verificaId(id);
        repo.deleteById(id);
    }

    @Transactional
    public Dono removerComFeedback(Long id){
        verificaId(id);
        Dono donoFeedback = repo.findById(id).get();
        repo.delete(donoFeedback);
        return donoFeedback;
    }

    @Transactional
    public Dono buscarPorId(Long id){
        verificaId(id);
        return repo.findById(id).get();
    }

    @Transactional
    public List<Dono> buscar(Dono filtro){
        if (filtro == null)
            throw new NullPointerException("Filtro não pode ser nulo");
        Example<Dono> example = Example.of(filtro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repo.findAll(example);
    }

    @Transactional
    public List<Dono> buscarTodos(){
        return repo.findAll();
    }

    @Transactional
    public List<Animal> buscarTodosAnimais(Long id){
        verificaId(id);
        try{
            Dono donoEncontrado = repo.findById(id).get();
            Hibernate.initialize(donoEncontrado.getAnimais());
            return donoEncontrado.getAnimais();
        } catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }
}