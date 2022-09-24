package com.produtos.apirest.service;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.repository.DonoRepo;
import com.produtos.apirest.service.excecoes.NotHasCpfException;
import com.produtos.apirest.service.excecoes.NotIdentifiableObject;
import com.produtos.apirest.service.excecoes.NotNamedDonoException;
import com.produtos.apirest.service.excecoes.NullDonoException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DonoService {

    @Autowired
    public DonoRepo repo;

    public static void verificaDono(Dono dono){
        if(dono == null)
            throw new NullDonoException();
        if(dono.getNome() == null || dono.getNome().equals(""))
            throw new NotNamedDonoException();
        if(dono.getCpf() == null || dono.getCpf().equals(""))
            throw new NotHasCpfException(dono);
    }

    public static void verificaId(Dono dono){
        if(dono == null || Long.valueOf(dono.getDonoId()) == null)
            throw new NotIdentifiableObject(dono);
    }

    @Transactional
    public List<Dono> buscarTodos(){
        return repo.findAll();
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
    public void removerPorId(Dono dono){
        verificaId(dono);
        repo.deleteById(dono.getDonoId());
    }

    @Transactional
    public Dono removerFeedback(Dono Dono){
        verificaDono(Dono);
        verificaId(Dono);
        Optional<Dono> DonoRemover = repo.findById(Dono.getDonoId());
        Dono DonoTemp = DonoRemover.get();
        repo.delete(DonoTemp);
        return DonoTemp;
    }

    @Transactional
    public Dono buscarDonoPorId(Dono Dono){
        verificaId(Dono);
        return repo.findById(Dono.getDonoId()).get();
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
    public List<Animal> buscarTodosAnimais(Dono dono){
        verificaDono(dono);
        verificaId(dono);
        try{
            Optional<Dono> donoTemp = repo.findById(dono.getDonoId());
            Dono donoBuscado = donoTemp.get();
            Hibernate.initialize(donoBuscado.getAnimais());
            return donoBuscado.getAnimais();
        } catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }

}
