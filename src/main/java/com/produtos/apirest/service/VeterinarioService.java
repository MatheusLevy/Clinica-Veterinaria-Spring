package com.produtos.apirest.service;

import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.repository.ExpertiseRepo;
import com.produtos.apirest.repository.VeterinaryRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VeterinarioService {

    private final VeterinaryRepo repo;
    private final ExpertiseRepo expertiseRepo;

    public VeterinarioService(VeterinaryRepo veterinaryRepo, ExpertiseRepo expertiseRepo){
        this.repo = veterinaryRepo;
        this.expertiseRepo = expertiseRepo;
    }

    public static void verificaVeterinario(Veterinary veterinario){
        if (veterinario == null)
            throw new NullPointerException("Veterinario não pode ser Nulo!");
        if(veterinario.getName().equals(""))
            throw new RegraNegocioRunTime("Veterinario deve ter um Nome!");
        if (veterinario.getCpf().equals(""))
            throw new RegraNegocioRunTime("Veterinario deve ter um CPF");
        if (veterinario.getExpertise() == null)
            throw new RegraNegocioRunTime("Veterinario deve ter uma Especialidade");
    }

    public static void verificaId(Veterinary veterinario){
        if (veterinario.getVeterinaryId() <= 0)
            throw new RegraNegocioRunTime("Veterinario deve ter um identificador");
    }

    public static void verificaId(Long id){
        if (id <= 0)
            throw new RegraNegocioRunTime("Veterinario deve ter um identificador");
    }

    @Transactional
    public Veterinary salvar(Veterinary veterinario){
        verificaVeterinario(veterinario);
        return repo.save(veterinario);
    }

    @Transactional
    public Veterinary atualizar(Veterinary veterinario){
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
    public Veterinary removerComFeedback(Long id){
        verificaId(id);
        Optional<Veterinary> veterinariosEncontrados = repo.findById(id);
        if (veterinariosEncontrados.isPresent()) {
            Veterinary veterinarioFeedback = veterinariosEncontrados.get();
            repo.delete(veterinarioFeedback);
            return veterinarioFeedback;
        }
        return null;
    }

    @Transactional
    public Veterinary buscarPorId(Long id){
        verificaId(id);
        Optional<Veterinary> veterinariosEncontrados = repo.findById(id);
        return veterinariosEncontrados.orElse(null);
    }

    @Transactional
    public List<Veterinary> buscar(Veterinary filtro){
        if (filtro == null)
            throw new NullPointerException("Filtro não pode ser nulo");
        Example<Veterinary> example = Example.of(filtro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repo.findAll(example);
    }

    @Transactional
    public List<Veterinary> buscarTodos(){
        return repo.findAll();
    }

    @Transactional
    public Expertise buscarEspecialidade(Veterinary veterinario){
        verificaVeterinario(veterinario);
        verificaId(veterinario);
        Long id = veterinario.getVeterinaryId();
        Optional<Veterinary> veterinariosEncontrados = repo.findById(id);
        return veterinariosEncontrados.map(Veterinary::getExpertise).orElse(null);
    }

    @Transactional
    public Veterinary atualizarEspecialidade(Veterinary destino, Expertise especialidadeNova){
        EspecialidadeService.verificaEspecialidade(especialidadeNova);
        EspecialidadeService.verificaId(especialidadeNova);
        verificaVeterinario(destino);
        verificaId(destino);
        destino.setExpertise(especialidadeNova);
        return  repo.save(destino);
    }
}