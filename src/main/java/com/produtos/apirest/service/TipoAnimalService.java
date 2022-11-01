package com.produtos.apirest.service;

import com.produtos.apirest.models.AnimalType;
import com.produtos.apirest.repository.AnimalTypeRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TipoAnimalService {

    private final AnimalTypeRepo repo;

    public TipoAnimalService(AnimalTypeRepo animalTypeRepo){
        this.repo = animalTypeRepo;
    }

    public static void verificaTipoAnimal(AnimalType tipo){
        if (tipo == null)
            throw new NullPointerException("Tipo de Animal não pode ser Nulo!");
        if (tipo.getName().equals(""))
            throw new RegraNegocioRunTime("Tipo de Animal deve ter um nome!");
    }

    public static void verificaId(AnimalType tipo){
        if(tipo == null || tipo.getAnimalTypeId() <= 0)
            throw new RegraNegocioRunTime("Tipo de Animal deve ter um identificador!");
    }

    public static void verificaId(Long id){
        if(id <= 0)
            throw new RegraNegocioRunTime("Tipo de Animal deve ter um identificador!");
    }

    @Transactional
    public AnimalType salvar(AnimalType tipo){
        verificaTipoAnimal(tipo);
        return repo.save(tipo);
    }

    @Transactional
    public AnimalType atualizar(AnimalType tipo){
        verificaTipoAnimal(tipo);
        verificaId(tipo);
        return repo.save(tipo);
    }

    @Transactional
    public void remover(AnimalType tipo){
        verificaTipoAnimal(tipo);
        verificaId(tipo);
        repo.delete(tipo);
    }

    @Transactional
    public AnimalType removerComFeedback(Long id){
        verificaId(id);
        Optional<AnimalType> tipoAnimalEncontrados = repo.findById(id);
        if (tipoAnimalEncontrados.isPresent()) {
            AnimalType tipoAnimalFeedback = tipoAnimalEncontrados.get();
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
    public AnimalType buscarPorId(Long id){
        verificaId(id);
        Optional<AnimalType> tipoAnimalEncontrados = repo.findById(id);
        return tipoAnimalEncontrados.orElse(null);
    }

    @Transactional
    public List<AnimalType> buscar(AnimalType filtro){
        if (filtro == null)
            throw new NullPointerException("Filtro não pode ser nulo");
        Example<AnimalType> example = Example.of(filtro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repo.findAll(example);
    }

    @Transactional
    public List<AnimalType> buscarTodos(){
        return repo.findAll();
    }
}