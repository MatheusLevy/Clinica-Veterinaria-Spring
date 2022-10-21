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
            throw new NullPointerException("O Animal não pode ser Nulo!");
        if(animal.getNome() == null || animal.getNome().equals(""))
            throw new RegraNegocioRunTime("Animal deve ter nome!");
    }

    public static void verificaId(Animal animal){
        if (Long.valueOf(animal.getAnimalId()) == null || animal == null)
            throw new RegraNegocioRunTime("O Animal deve possuir um identificador!");
    }

    public static void verificaId(Long id){
        if (Long.valueOf(id) == null || id < 0)
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
        Animal animalFeedback = repo.findById(id).get();
        repo.delete(animalFeedback);
        return animalFeedback;
    }

    @Transactional
    public Animal buscarPorId(Long id){
        verificaId(id);
        return repo.findById(id).get();
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
        Animal animalEncontrado = repo.findById(id).get();
        return animalEncontrado.getDono();
    }

    @Transactional
    public Animal atualizarDono(Animal destino, Dono donoNovo){
        DonoService.verificaDono(donoNovo);
        DonoService.verificaId(donoNovo);
        verificaAnimal(destino);
        verificaId(destino);

        Optional<Dono> donoOptional = donoRepo.findById(donoNovo.getDonoId());
        if (!donoOptional.isPresent())
            throw new RegraNegocioRunTime("Não foi possivel encontrar o Dono");

        Optional<Animal> animalOptional = repo.findById(destino.getAnimalId());
        if(!animalOptional.isPresent())
            throw new RegraNegocioRunTime("Não foi possivel encontrar o Animal");

        Animal animalDestinoEncontrado = animalOptional.get();
        Dono donoNovoEncontrado = donoOptional.get();
        animalDestinoEncontrado.setDono(donoNovoEncontrado);

        return repo.save(animalDestinoEncontrado);
    }
}