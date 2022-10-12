package com.produtos.apirest.service;

import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.repository.TipoAnimalRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//TODO: **Alterar Nome da Classe para CamelCase
@Service
public class TipoAnimalService {

    @Autowired
    public TipoAnimalRepo repo;

    public static void verificaTipo_Animal(TipoAnimal tipo){
        if (tipo == null)
            throw new NullPointerException("Tipo de Animal não pode ser Nulo!");
        if (tipo.getNome() == null || tipo.getNome().equals(""))
            throw new RegraNegocioRunTime("Tipo de Animla deve ter um nome!");
    }

    public static void verificaId(TipoAnimal tipo){
        if(tipo == null || Long.valueOf(tipo.getTipoAnimalId()) == null)
            throw new RegraNegocioRunTime("Tipo de Animal deve ter um identificador!");
    }

    @Transactional
    public TipoAnimal salvar(TipoAnimal tipo){
        verificaTipo_Animal(tipo);
        return repo.save(tipo);
    }

    @Transactional
    public TipoAnimal atualizar(TipoAnimal tipo){
        verificaTipo_Animal(tipo);
        verificaId(tipo);
        return repo.save(tipo);
    }

    @Transactional
    public void remover(TipoAnimal tipo){
        verificaTipo_Animal(tipo);
        verificaId(tipo);
        repo.delete(tipo);
    }

    @Transactional
    public TipoAnimal removerFeedback(TipoAnimal tipo){
        verificaTipo_Animal(tipo);
        verificaId(tipo);
        Optional<TipoAnimal> tipoRemover = repo.findById(tipo.getTipoAnimalId());
        TipoAnimal tipoTemp = tipoRemover.get();
        repo.delete(tipoTemp);
        return tipoTemp;
    }

    @Transactional
    public void removerPorId(TipoAnimal tipo){
        verificaId(tipo);
        repo.deleteById(tipo.getTipoAnimalId());
    }

    @Transactional
    public TipoAnimal buscarTipo_animalPorId(TipoAnimal tipo){
        verificaId(tipo);
        return repo.findById(tipo.getTipoAnimalId()).get();
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
