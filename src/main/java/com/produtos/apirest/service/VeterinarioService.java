package com.produtos.apirest.service;

import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.models.Veterinario;
import com.produtos.apirest.repository.EspecialidadeRepo;
import com.produtos.apirest.repository.VeterinarioRepo;
import com.produtos.apirest.service.excecoes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VeterinarioService {

    @Autowired
    public VeterinarioRepo repo;

    @Autowired
    public EspecialidadeRepo especialidadeRepo;

    public static void verificaVeterinario(Veterinario veterinario){
        if (veterinario == null)
            throw new NullPointerException("Veterinario não pode ser Nulo!");
        if(veterinario.getNome() == null || veterinario.getNome().equals(""))
            throw new RegraNegocioRunTime("Veterinario deve ter um Nome!");
        if (veterinario.getCpf() == null || veterinario.getCpf().equals(""))
            throw new RegraNegocioRunTime("Veterinario deve ter um CPF");
        if (veterinario.getEspecialidade() == null)
            throw new RegraNegocioRunTime("Veterinario deve ter uma Especialidade");
    }

    public static void verificaId(Veterinario veterinario){
        if (Long.valueOf(veterinario.getVeterinarioId()) == null || veterinario == null)
            throw new RegraNegocioRunTime("Veterinario deve ter um identificador");
    }

    public static void verificaId(Long id){
        if (Long.valueOf(id) == null )
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
        Optional<Veterinario> VeterinarioRemover = repo.findById(id);
        Veterinario VeterinarioTemp = VeterinarioRemover.get();
        repo.delete(VeterinarioTemp);
        return VeterinarioTemp;
    }


    @Transactional
    public Veterinario buscarPorId(Long id){
        verificaId(id);
        return  repo.findById(id).get();
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
        Optional<Veterinario> veterinarioTemp = repo.findById(veterinario.getVeterinarioId());
        return veterinarioTemp.get().getEspecialidade();
    }

    @Transactional
    public Veterinario atualizarEspecialidade(Especialidade especialidade, Veterinario veterinario){
        EspecialidadeService.verificaEspecialidade(especialidade);
        EspecialidadeService.verificaId(especialidade);
        verificaVeterinario(veterinario);
        verificaId(veterinario);

        Optional<Veterinario> veterinarioTemp = repo.findById(veterinario.getVeterinarioId());
        if (!veterinarioTemp.isPresent())
            throw new RegraNegocioRunTime("Veterinario não encontrado!");

        Optional<Especialidade> especialidadeTemp = especialidadeRepo.findById(especialidade.getEspecialidadeId());
        if(!especialidadeTemp.isPresent())
            throw new RegraNegocioRunTime("Especialidade não encontrada!");

        Veterinario veterinarioAtualizar = veterinarioTemp.get();
        Especialidade novaEspecialidade = especialidadeTemp.get();
        veterinarioAtualizar.setEspecialidade(novaEspecialidade);

        return  repo.save(veterinarioAtualizar);
    }


}
