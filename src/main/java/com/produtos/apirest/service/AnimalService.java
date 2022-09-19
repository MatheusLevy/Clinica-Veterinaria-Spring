package com.produtos.apirest.service;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.repository.AnimalRepo;
import com.produtos.apirest.repository.DonoRepo;
import com.produtos.apirest.service.excecoes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    @Autowired
    public AnimalRepo repo;

    @Autowired
    public DonoRepo donoRepo;
    public static void verificaAnimal(Animal animal){
        if (animal == null)
            throw new NullAnimalException();
        if(animal.getNome() == null || animal.getNome().equals(""))
            throw new NotNamedAnimalException();
    }

    public static void verificaId(Animal animal){
        if (Long.valueOf(animal.getAnimalId()) == null || animal == null)
            throw new NotIdentifiableObject(animal);
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
    public void remover(Animal animal){
        verificaAnimal(animal);
        verificaId(animal);
        repo.delete(animal);
    }

    @Transactional
    public void removerPorId(Animal animal){
        verificaId(animal);
        repo.deleteById(animal.getAnimalId());
    }

    @Transactional
    public Animal removerFeedback(Animal animal){
        verificaAnimal(animal);
        verificaId(animal);
        Optional<Animal> AnimalRemover = repo.findById(animal.getAnimalId());
        Animal AnimalTemp = AnimalRemover.get();
        repo.delete(AnimalTemp);
        return AnimalTemp;
    }

    @Transactional
    public Animal buscarDonoPorId(Animal animal){
        verificaId(animal);
        return repo.findById(animal.getAnimalId()).get();
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
    public Animal buscarPorId(Animal animal){
        verificaId(animal);
        Optional<Animal> animalTemp = repo.findById(animal.getAnimalId());
        return animalTemp.get();
    }
    @Transactional
    public Dono buscarDono(Animal animal){
        verificaId(animal);
        Optional<Animal> animals = repo.findById(animal.getAnimalId());
        return animals.get().getDono();
    }

    @Transactional
    public Animal atualizarDono(Dono dono, Animal animal){
        DonoService.verificaDono(dono);
        DonoService.verificaId(dono);
        verificaAnimal(animal);
        verificaId(animal);

        Optional<Dono> donoTemp = donoRepo.findById(dono.getDonoId());
        if (!donoTemp.isPresent())
            throw new NotFindException(dono);

        Optional<Animal> animalTemp = repo.findById(animal.getAnimalId());
        if(!animalTemp.isPresent())
            throw new NotFindException(animal);

        Animal animalAtualizar = animalTemp.get();
        Dono novoDono = donoTemp.get();
        animalAtualizar.setDono(novoDono);

        return repo.save(animalAtualizar);
    }
}
