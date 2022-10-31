package com.produtos.apirest.service;

import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.models.Veterinario;
import com.produtos.apirest.repository.EspecialidadeRepo;
import com.produtos.apirest.repository.VeterinarioRepo;
import com.produtos.apirest.service.excecoes.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VeterinarioService {

    private final VeterinarioRepo repo;
    private final EspecialidadeRepo especialidadeRepo;

    public VeterinarioService(VeterinarioRepo veterinarioRepo, EspecialidadeRepo especialidadeRepo){
        this.repo = veterinarioRepo;
        this.especialidadeRepo = especialidadeRepo;
    }

    public static void verificaVeterinario(Veterinario veterinario){
        if (veterinario == null)
            throw new NullPointerException("Veterinario não pode ser Nulo!");
        if(veterinario.getNome().equals(""))
            throw new RegraNegocioRunTime("Veterinario deve ter um Nome!");
        if (veterinario.getCpf().equals(""))
            throw new RegraNegocioRunTime("Veterinario deve ter um CPF");
        if (veterinario.getEspecialidade() == null)
            throw new RegraNegocioRunTime("Veterinario deve ter uma Especialidade");
    }

    public static void verificaId(Veterinario veterinario){
        if (veterinario.getVeterinarioId() <= 0)
            throw new RegraNegocioRunTime("Veterinario deve ter um identificador");
    }

    public static void verificaId(Long id){
        if (id <= 0)
            throw new RegraNegocioRunTime("Veterinario deve ter um identificador");
    }

    @Transactional
    public Veterinario salvar(Veterinario veterinario){
        verificaVeterinario(veterinario);
        return repo.save(veterinario);
    }

    @Transactional
    public Veterinario atualizar(Veterinario veterinario){
        verificaVeterinario(veterinario);
        verificaId(veterinario);
        return repo.save(veterinario);
    }

    @Transactional
    public void remover(Long id){
        verificaId(id);
        repo.deleteById(id);
    }

    @Transactional
    public Veterinario removerComFeedback(Long id){
        verificaId(id);
        Optional<Veterinario> veterinariosEncontrados = repo.findById(id);
        if (veterinariosEncontrados.isPresent()) {
            Veterinario veterinarioFeedback = veterinariosEncontrados.get();
            repo.delete(veterinarioFeedback);
            return veterinarioFeedback;
        }
        return null;
    }

    @Transactional
    public Veterinario buscarPorId(Long id){
        verificaId(id);
        Optional<Veterinario> veterinariosEncontrados = repo.findById(id);
        return veterinariosEncontrados.orElse(null);
    }

    @Transactional
    public List<Veterinario> buscar(Veterinario filtro){
        if (filtro == null)
            throw new NullPointerException("Filtro não pode ser nulo");
        Example<Veterinario> example = Example.of(filtro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repo.findAll(example);
    }

    @Transactional
    public List<Veterinario> buscarTodos(){
        return repo.findAll();
    }

    @Transactional
    public Especialidade buscarEspecialidade(Veterinario veterinario){
        verificaVeterinario(veterinario);
        verificaId(veterinario);
        Long id = veterinario.getVeterinarioId();
        Optional<Veterinario> veterinariosEncontrados = repo.findById(id);
        return veterinariosEncontrados.map(Veterinario::getEspecialidade).orElse(null);
    }

    @Transactional
    public Veterinario atualizarEspecialidade(Veterinario destino, Especialidade especialidadeNova){
        EspecialidadeService.verificaEspecialidade(especialidadeNova);
        EspecialidadeService.verificaId(especialidadeNova);
        verificaVeterinario(destino);
        verificaId(destino);
        destino.setEspecialidade(especialidadeNova);
        return  repo.save(destino);
    }
}