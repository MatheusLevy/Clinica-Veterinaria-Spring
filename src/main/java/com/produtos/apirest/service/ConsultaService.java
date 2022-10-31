package com.produtos.apirest.service;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Consulta;
import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.models.Veterinario;
import com.produtos.apirest.repository.ConsultaRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    private final ConsultaRepo repo;

    public ConsultaService(ConsultaRepo consultaRepo){
        this.repo = consultaRepo;
    }

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
        if (consulta == null || consulta.getConsultaId() <= 0)
            throw new RegraNegocioRunTime("Consulta deve ter um identificador!");
    }

    public static void verificaId(Long id){
        if (id <= 0)
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
        destino.setVeterinario(veterinarioNovo);
        return repo.save(destino);
    }

    @Transactional
    public Consulta atualizarAnimal(Consulta destino, Animal animalNovo){
        AnimalService.verificaAnimal(animalNovo);
        AnimalService.verificaId(animalNovo);
        verificaConsulta(destino);
        verificaId(destino);
        destino.setAnimal(animalNovo);
        return repo.save(destino);
    }

    @Transactional
    public Consulta atualizarTipoConsulta(Consulta destino, TipoConsulta tipoConsultaNovo){
        TipoConsultaService.verificaTipoConsulta(tipoConsultaNovo);
        TipoConsultaService.verificaId(tipoConsultaNovo);
        verificaConsulta(destino);
        verificaId(destino);
        destino.setTipoConsulta(tipoConsultaNovo);
        return repo.save(destino);
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
        Optional<Consulta> consultasEncontradas = repo.findById(id);
        if (consultasEncontradas.isPresent()) {
            Consulta consultaFeedback = consultasEncontradas.get();
            repo.delete(consultaFeedback);
            return consultaFeedback;
        }
        return null;
    }

    @Transactional
    public Consulta buscarPorId(Long id){
        verificaId(id);
        Optional<Consulta> consultasEncotradas = repo.findById(id);
        return consultasEncotradas.orElse(null);
    }

    @Transactional
    public List<Consulta> buscarTodos(){
        return repo.findAll();
    }
}