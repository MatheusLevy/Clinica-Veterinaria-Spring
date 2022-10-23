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
    public TipoConsultaRepo tipoConsultaRepo;

    public static void verificaConsulta(Consulta consulta){
        if(consulta == null)
            throw new NullPointerException("A Consulta não pode ser Nula!");
        if(consulta.getTipoConsulta() == null)
            throw new RegraNegocioRunTime("Consulta deve possuir um Tipo de Consulta!");
        if(consulta.getAnimal() == null)
            throw new RegraNegocioRunTime("Consulta deve possuir um Animal!");
        if (consulta.getVeterinario() == null)
            throw new RegraNegocioRunTime("Consulta deve possuir um Veterinario!");
        if(consulta.getData() == null)
            throw new RegraNegocioRunTime("Consulta deve ter uma Data!");
    }

    public static void verificaId(Consulta consulta){
        if (consulta == null || Long.valueOf(consulta.getConsultaId()) == null)
            throw new RegraNegocioRunTime("Consulta deve ter um identificador!");
    }

    public static void verificaId(Long id){
        if (Long.valueOf(id) == null)
            throw new RegraNegocioRunTime("Consulta deve ter um identificador!");
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
    public Consulta atualizarVeterinario(Consulta destino, Veterinario veterinarioNovo){
        VeterinarioService.verificaVeterinario(veterinarioNovo);
        VeterinarioService.verificaId(veterinarioNovo);
        verificaConsulta(destino);
        verificaId(destino);

        Optional<Veterinario> veterinarioOptional = veterinarioRepo.findById(veterinarioNovo.getVeterinarioId());
        if(!veterinarioOptional.isPresent())
            throw new RegraNegocioRunTime("Não foi possivel achar o Veterinário!");

        Optional<Consulta> consultaOptional = repo.findById(destino.getConsultaId());
        if(!consultaOptional.isPresent())
            throw new RegraNegocioRunTime("Não foi possivel achar a Consulta!");

        Consulta consultaDestinoEncontrada = consultaOptional.get();
        Veterinario novoVeterinario = veterinarioOptional.get();
        consultaDestinoEncontrada.setVeterinario(novoVeterinario);

        return repo.save(consultaDestinoEncontrada);
    }

    @Transactional
    public Consulta atualizarAnimal(Consulta destino, Animal animalNovo){
        AnimalService.verificaAnimal(animalNovo);
        AnimalService.verificaId(animalNovo);
        verificaConsulta(destino);
        verificaId(destino);

        Optional<Animal> animalOptional = animalRepo.findById(animalNovo.getAnimalId());
        if(!animalOptional.isPresent())
            throw new RegraNegocioRunTime("Não foi possivel achar o Animal!");

        Optional<Consulta> consultaOptional = repo.findById(destino.getConsultaId());
        if(!consultaOptional.isPresent())
            throw new RegraNegocioRunTime("Não foi possivel achar a Consulta");

        Consulta consultaDestinoEncontrada = consultaOptional.get();
        Animal novoAnimalEncontrado = animalOptional.get();
        consultaDestinoEncontrada.setAnimal(novoAnimalEncontrado);

        return repo.save(consultaDestinoEncontrada);
    }

    @Transactional
    public Consulta atualizarTipoConsulta(Consulta destino, TipoConsulta tipoConsultaNovo){
        TipoConsultaService.verificaTipoConsulta(tipoConsultaNovo);
        TipoConsultaService.verificaId(tipoConsultaNovo);
        verificaConsulta(destino);
        verificaId(destino);

        Optional<TipoConsulta> tipoConsultaOptional = tipoConsultaRepo.findById(tipoConsultaNovo.getTipoConsultaId());
        if (!tipoConsultaOptional.isPresent())
            throw new RegraNegocioRunTime("Não foi possivel achar o Tipo de Consulta!");

        Optional<Consulta> consultaOptional = repo.findById(destino.getConsultaId());
        if(!consultaOptional.isPresent())
            throw new RegraNegocioRunTime("Não foi possivel achar a Consulta!");

        Consulta consultaDestinoEncontrada = consultaOptional.get();
        TipoConsulta tipoConsultaNovoEncontrado = tipoConsultaOptional.get();
        consultaDestinoEncontrada.setTipoConsulta(tipoConsultaNovoEncontrado);

        return repo.save(consultaDestinoEncontrada);
    }

    @Transactional
    public void remover(Consulta consulta){
        verificaConsulta(consulta);
        verificaId(consulta);
        repo.delete(consulta);
    }

    @Transactional
    public void removerPorId(Long id){
        verificaId(id);
        repo.deleteById(id);
    }

    @Transactional
    public Consulta removerComFeedback(Long id){
        verificaId(id);
        Consulta consultaFeedback = repo.findById(id).get();
        repo.delete(consultaFeedback);
        return consultaFeedback;
    }

    @Transactional
    public Consulta buscarPorId(Long id){
        verificaId(id);
        return repo.findById(id).get();
    }

    @Transactional
    public List<Consulta> buscarTodos(){
        return repo.findAll();
    }
}