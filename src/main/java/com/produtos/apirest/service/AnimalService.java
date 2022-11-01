package com.produtos.apirest.service;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.repository.AnimalRepo;
import com.produtos.apirest.repository.DonoRepo;
import com.produtos.apirest.service.excecoes.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalRepo repo;
    private final DonoRepo donoRepo;

    public AnimalService(AnimalRepo animalRepo, DonoRepo donoRepo){
        this.repo = animalRepo;
        this.donoRepo = donoRepo;
    }

    public static void verificaAnimal(Animal animal){
        if (animal == null)
            throw new NullPointerException("O Animal não pode ser Nulo!");
        if(animal.getName() == null || animal.getName().equals(""))
            throw new RegraNegocioRunTime("Animal deve ter nome!");
    }

    public static void verificaId(Animal animal){
        if (animal.getAnimalId() <= 0)
            throw new RegraNegocioRunTime("O Animal deve possuir um identificador!");
    }

    public static void verificaId(Long id){
        if (id <= 0)
            throw new RegraNegocioRunTime("O Animal deve possuir um identificador!");
    }

    @Transactional
    public Animal salvar(Animal animal){
        verificaAnimal(animal);
        return repo.save(animal);
    }

    @Transactional
    public Animal atualizar(Animal animal){
        verificaAnimal(animal);
        verificaId(animal);
        return repo.save(animal);
    }

    @Transactional
    public Animal atualizarDono(Animal destino, Dono donoNovo){
        DonoService.verificaDono(donoNovo);
        DonoService.verificaId(donoNovo);
        verificaAnimal(destino);
        verificaId(destino);
        destino.setOwner(donoNovo);
        return repo.save(destino);
    }

    @Transactional
    public void remover(Animal animal){
        verificaAnimal(animal);
        verificaId(animal);
        repo.delete(animal);
    }

    @Transactional
    public void removerPorId(Long id){
        verificaId(id);
        repo.deleteById(id);
    }

    @Transactional
    public Animal removerComFeedback(Long id){
        verificaId(id);
        Optional<Animal> animaisEncontrados = repo.findById(id);
        if (animaisEncontrados.isPresent()) {
            Animal animalFeedback = animaisEncontrados.get();
            repo.delete(animalFeedback);
            return animalFeedback;
        }
        return null;
    }

    @Transactional
    public Animal buscarPorId(Long id){
        verificaId(id);
        Optional<Animal> animaisEncontrados = repo.findById(id);
        return animaisEncontrados.orElse(null);
    }

    @Transactional
    public List<Animal> buscar(Animal filtro){
        if (filtro == null)
            throw new NullPointerException("Filtro não pode ser nulo");
        Example<Animal> example = Example.of(filtro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repo.findAll(example);
    }

    @Transactional
    public List<Animal> buscarTodos(){
        return repo.findAll();
    }

    @Transactional
    public Dono buscarDonoPorId(Long id){
        verificaId(id);
        Optional<Animal> animaisEncontrados = repo.findById(id);
        return animaisEncontrados.map(Animal::getOwner).orElse(null);
    }
}