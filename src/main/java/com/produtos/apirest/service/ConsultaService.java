package com.produtos.apirest.service;

import com.produtos.apirest.models.*;
import com.produtos.apirest.repository.AnimalRepo;
import com.produtos.apirest.repository.ConsultaRepo;
import com.produtos.apirest.repository.TipoConsultaRepo;
import com.produtos.apirest.repository.VeterinarioRepo;
import com.produtos.apirest.service.excecoes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    @Autowired
    public ConsultaRepo repo;
    @Autowired
    public VeterinarioRepo veterinarioRepo;
    @Autowired
    public AnimalRepo animalRepo;
    @Autowired
    public TipoConsultaRepo tipo_consultaRepo;
    public static void verificaConsulta(Consulta consulta){
        if(consulta == null)
            throw new NullConsultaException();
        if(consulta.getTipoConsulta() == null)
            throw new NullManytoOneException(consulta.getTipoConsulta());
        if(consulta.getAnimal() == null)
            throw new NullManytoOneException(consulta.getAnimal());
        if (consulta.getVeterinario() == null)
            throw new NullManytoOneException(consulta.getVeterinario());
        if(consulta.getAnimal() == null)
            throw new NullManytoOneException(consulta.getAnimal());
        if(consulta.getData() == null)
            throw new NullDataException(consulta);
    }

    public static void verificaId(Consulta consulta){
        if (consulta == null || Long.valueOf(consulta.getConsultaId()) == null)
            throw new NotIdentifiableObject(consulta);
    }

    @Transactional
    public Consulta salvar(Consulta consulta){
        verificaConsulta(consulta);
        return repo.save(consulta);
    }

    @Transactional
    public Consulta atualizar(Consulta consulta){
        verificaConsulta(consulta);
        verificaId(consulta);
        return repo.save(consulta);
    }

    @Transactional
    public Consulta buscarComId(Consulta consulta){
        verificaId(consulta);
        return repo.findById(consulta.getConsultaId()).get();
    }

    @Transactional
    public List<Consulta> buscarTodos(){
        return repo.findAll();
    }
    @Transactional
    public void remover(Consulta consulta){
        verificaConsulta(consulta);
        verificaId(consulta);
        repo.delete(consulta);
    }

    @Transactional
    public Consulta removerFeedback(Consulta consulta){
        verificaConsulta(consulta);
        verificaId(consulta);
        Optional<Consulta> ConsultaRemover = repo.findById(consulta.getConsultaId());
        Consulta ConsultaTemp = ConsultaRemover.get();
        repo.delete(ConsultaTemp);
        return ConsultaTemp;
    }

    @Transactional
    public Consulta atualizarVeterinario(Veterinario veterinario, Consulta consulta){
        VeterinarioService.verificaVeterinario(veterinario);
        VeterinarioService.verificaId(veterinario);
        verificaConsulta(consulta);
        verificaId(consulta);

        Optional<Veterinario> veterinarioTemp = veterinarioRepo.findById(veterinario.getVeterinarioId());
        if(!veterinarioTemp.isPresent())
            throw new NotFindException(veterinario);

        Optional<Consulta> consultaTemp = repo.findById(consulta.getConsultaId());
        if(!consultaTemp.isPresent())
            throw new NotFindException(consulta);

        Consulta consultaAtualizar = consultaTemp.get();
        Veterinario novoVeterinario = veterinarioTemp.get();
        consultaAtualizar.setVeterinario(novoVeterinario);

        return repo.save(consultaAtualizar);
    }

    @Transactional
    public Consulta atualizarAnimal(Animal animal, Consulta consulta){
        AnimalService.verificaAnimal(animal);
        AnimalService.verificaId(animal);
        verificaConsulta(consulta);
        verificaId(consulta);

        Optional<Animal> animalTemp = animalRepo.findById(animal.getAnimalId());
        if(!animalTemp.isPresent())
            throw new NotFindException(animal);

        Optional<Consulta> consultaTemp = repo.findById(consulta.getConsultaId());
        if(!consultaTemp.isPresent())
            throw new NotFindException(consulta);

        Consulta consultaAtualizar = consultaTemp.get();
        Animal novoAnimal = animalTemp.get();
        consultaAtualizar.setAnimal(novoAnimal);

        return repo.save(consultaAtualizar);
    }

    @Transactional
    public Consulta atualizarTipoConsulta(TipoConsulta tipo, Consulta consulta){
        Tipo_ConsultaService.verificaTipo_consulta(tipo);
        Tipo_ConsultaService.verificaId(tipo);
        verificaConsulta(consulta);
        verificaId(consulta);

        Optional<TipoConsulta> tipo_consultaTemp = tipo_consultaRepo.findById(tipo.getTipoConsultaId());
        if (!tipo_consultaTemp.isPresent())
            throw new NotFindException(tipo);

        Optional<Consulta> consultaTemp = repo.findById(consulta.getConsultaId());
        if(!consultaTemp.isPresent())
            throw new NotFindException(consulta);

        Consulta consultaAtualizar = consultaTemp.get();
        TipoConsulta novoTipo = tipo_consultaTemp.get();
        consultaAtualizar.setTipoConsulta(novoTipo);

        return repo.save(consultaAtualizar);
    }

}
