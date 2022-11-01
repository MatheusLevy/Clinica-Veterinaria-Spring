package com.produtos.apirest.service;

import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.repository.TipoAnimalRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TipoAnimalService {

    private final TipoAnimalRepo repo;

    public TipoAnimalService(TipoAnimalRepo tipoAnimalRepo){
        this.repo = tipoAnimalRepo;
    }

    public static void verificaTipoAnimal(TipoAnimal tipo){
        if (tipo == null)
            throw new NullPointerException("Tipo de Animal não pode ser Nulo!");
        if (tipo.getName().equals(""))
            throw new RegraNegocioRunTime("Tipo de Animal deve ter um nome!");
    }

    public static void verificaId(TipoAnimal tipo){
        if(tipo == null || tipo.getAnimalTypeId() <= 0)
            throw new RegraNegocioRunTime("Tipo de Animal deve ter um identificador!");
    }

    public static void verificaId(Long id){
        if(id <= 0)
            throw new RegraNegocioRunTime("Tipo de Animal deve ter um identificador!");
    }

    @Transactional
    public TipoAnimal salvar(TipoAnimal tipo){
        verificaTipoAnimal(tipo);
        return repo.save(tipo);
    }

    @Transactional
    public TipoAnimal atualizar(TipoAnimal tipo){
        verificaTipoAnimal(tipo);
        verificaId(tipo);
        return repo.save(tipo);
    }

    @Transactional
    public void remover(TipoAnimal tipo){
        verificaTipoAnimal(tipo);
        verificaId(tipo);
        repo.delete(tipo);
    }

    @Transactional
    public TipoAnimal removerComFeedback(Long id){
        verificaId(id);
        Optional<TipoAnimal> tipoAnimalEncontrados = repo.findById(id);
        if (tipoAnimalEncontrados.isPresent()) {
            TipoAnimal tipoAnimalFeedback = tipoAnimalEncontrados.get();
            repo.delete(tipoAnimalFeedback);
            return tipoAnimalFeedback;
        }
        return null;
    }

    @Transactional
    public void removerPorId(Long id){
        verificaId(id);
        repo.deleteById(id);
    }

    @Transactional
    public TipoAnimal buscarPorId(Long id){
        verificaId(id);
        Optional<TipoAnimal> tipoAnimalEncontrados = repo.findById(id);
        return tipoAnimalEncontrados.orElse(null);
    }

    @Transactional
    public List<TipoAnimal> buscar(TipoAnimal filtro){
        if (filtro == null)
            throw new NullPointerException("Filtro não pode ser nulo");
        Example<TipoAnimal> example = Example.of(filtro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repo.findAll(example);
    }

    @Transactional
    public List<TipoAnimal> buscarTodos(){
        return repo.findAll();
    }
}