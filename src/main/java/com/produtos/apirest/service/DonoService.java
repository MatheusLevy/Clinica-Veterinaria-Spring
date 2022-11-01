package com.produtos.apirest.service;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Owner;
import com.produtos.apirest.repository.OwnerRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DonoService {

    private final OwnerRepo repo;

    public DonoService(OwnerRepo ownerRepo){
        this.repo = ownerRepo;
    }

    public static void verificaDono(Owner dono){
        if (dono == null)
            throw new NullPointerException("Dono não pode ser Nulo!");
        if (dono.getName().equals(""))
            throw new RegraNegocioRunTime("Dono deve ter um nome!");
        if (dono.getCpf().equals(""))
            throw new RegraNegocioRunTime("Dono deve ter um CPF!");
    }

    public static void verificaId(Owner dono){
        if (dono == null || dono.getOwnerId() <= 0)
            throw new RegraNegocioRunTime("Dono deve ter um indentificador!");
    }

    public static void verificaId(Long id){
        if (id <= 0)
            throw new RegraNegocioRunTime("Dono deve ter um indentificador!");
    }

    @Transactional
    public Owner salvar(Owner Dono){
        verificaDono(Dono);
        return repo.save(Dono);
    }

    @Transactional
    public Owner atualizar(Owner Dono){
        verificaDono(Dono);
        verificaId(Dono);
        return repo.save(Dono);
    }

    @Transactional
    public void remover(Owner dono){
        verificaDono(dono);
        verificaId(dono);
        repo.delete(dono);
    }

    @Transactional
    public void removerPorId(Long id){
        verificaId(id);
        repo.deleteById(id);
    }

    @Transactional
    public Owner removerComFeedback(Long id){
        verificaId(id);
        Optional<Owner> donosEncontrados = repo.findById(id);
        if (donosEncontrados.isPresent()) {
            Owner donoFeedback = donosEncontrados.get();
            repo.delete(donoFeedback);
            return donoFeedback;
        }
        return null;
    }

    @Transactional
    public Owner buscarPorId(Long id){
        verificaId(id);
        Optional<Owner> donosEncontrados = repo.findById(id);
        return donosEncontrados.orElse(null);
    }

    @Transactional
    public List<Owner> buscar(Owner filtro){
        if (filtro == null)
            throw new NullPointerException("Filtro não pode ser nulo");
        Example<Owner> example = Example.of(filtro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repo.findAll(example);
    }

    @Transactional
    public List<Owner> buscarTodos(){
        return repo.findAll();
    }

    @Transactional
    public List<Animal> buscarTodosAnimais(Long id){
        verificaId(id);
        Optional<Owner> donosEncontrados = repo.findById(id);
        if (donosEncontrados.isPresent()) {
            Owner donoEncontrado = donosEncontrados.get();
            Hibernate.initialize(donoEncontrado.getAnimals());
            return donoEncontrado.getAnimals();
        }
        return null;
    }
}