package com.produtos.apirest.service;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.Appointment;
import com.produtos.apirest.models.AppointmentType;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.repository.AppointmentRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    private final AppointmentRepo repo;

    public ConsultaService(AppointmentRepo consultaRepo){
        this.repo = consultaRepo;
    }

    public static void verificaConsulta(Appointment consulta){
        if(consulta == null)
            throw new NullPointerException("A Consulta não pode ser Nula!");
        if(consulta.getAppointmentType() == null)
            throw new RegraNegocioRunTime("Consulta deve possuir um Tipo de Consulta!");
        if(consulta.getAnimal() == null)
            throw new RegraNegocioRunTime("Consulta deve possuir um Animal!");
        if (consulta.getVeterinary() == null)
            throw new RegraNegocioRunTime("Consulta deve possuir um Veterinario!");
        if(consulta.getDate() == null)
            throw new RegraNegocioRunTime("Consulta deve ter uma Data!");
    }

    public static void verificaId(Appointment consulta){
        if (consulta == null || consulta.getAppointmentId() <= 0)
            throw new RegraNegocioRunTime("Consulta deve ter um identificador!");
    }

    public static void verificaId(Long id){
        if (id <= 0)
            throw new RegraNegocioRunTime("Consulta deve ter um identificador!");
    }

    @Transactional
    public Appointment salvar(Appointment consulta){
        verificaConsulta(consulta);
        return repo.save(consulta);
    }

    @Transactional
    public Appointment atualizar(Appointment consulta){
        verificaConsulta(consulta);
        verificaId(consulta);
        return repo.save(consulta);
    }

    @Transactional
    public Appointment atualizarVeterinario(Appointment destino, Veterinary veterinarioNovo){
        VeterinarioService.verificaVeterinario(veterinarioNovo);
        VeterinarioService.verificaId(veterinarioNovo);
        verificaConsulta(destino);
        verificaId(destino);
        destino.setVeterinary(veterinarioNovo);
        return repo.save(destino);
    }

    @Transactional
    public Appointment atualizarAnimal(Appointment destino, Animal animalNovo){
        AnimalService.verificaAnimal(animalNovo);
        AnimalService.verificaId(animalNovo);
        verificaConsulta(destino);
        verificaId(destino);
        destino.setAnimal(animalNovo);
        return repo.save(destino);
    }

    @Transactional
    public Appointment atualizarTipoConsulta(Appointment destino, AppointmentType tipoConsultaNovo){
        TipoConsultaService.verificaTipoConsulta(tipoConsultaNovo);
        TipoConsultaService.verificaId(tipoConsultaNovo);
        verificaConsulta(destino);
        verificaId(destino);
        destino.setAppointmentType(tipoConsultaNovo);
        return repo.save(destino);
    }

    @Transactional
    public void remover(Appointment consulta){
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
    public Appointment removerComFeedback(Long id){
        verificaId(id);
        Optional<Appointment> consultasEncontradas = repo.findById(id);
        if (consultasEncontradas.isPresent()) {
            Appointment consultaFeedback = consultasEncontradas.get();
            repo.delete(consultaFeedback);
            return consultaFeedback;
        }
        return null;
    }

    @Transactional
    public Appointment buscarPorId(Long id){
        verificaId(id);
        Optional<Appointment> consultasEncotradas = repo.findById(id);
        return consultasEncotradas.orElse(null);
    }

    @Transactional
    public List<Appointment> buscarTodos(){
        return repo.findAll();
    }
}