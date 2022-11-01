package com.produtos.apirest.service;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.repository.ExpertiseRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadeService {

    private final ExpertiseRepo repo;

    public EspecialidadeService(ExpertiseRepo expertiseRepo){
        this.repo = expertiseRepo;
    }

    public static void verificaEspecialidade(Expertise especialidade){
        if (especialidade == null)
            throw new NullPointerException("Especialidade não pode ser Nula!");
        if (especialidade.getName().equals(""))
            throw new RegraNegocioRunTime("Especialidade deve ter um nome");
        if (especialidade.getArea() == null)
            throw new RegraNegocioRunTime("Especialidade deve ter uma Area!");
    }

    public static void verificaId(Expertise especialidade){
        if (especialidade == null || especialidade.getExpertiseId() <= 0)
            throw new RegraNegocioRunTime("Especialidade deve ter um identificador!s");
    }

    public static void verificaId(Long id){
        if (id <= 0)
            throw new RegraNegocioRunTime("Especialidade deve ter um identificador!s");
    }

    @Transactional
    public Expertise salvar(Expertise especialidade){
        verificaEspecialidade(especialidade);
        return repo.save(especialidade);
    }

    @Transactional
    public Expertise atualizar(Expertise especialidade){
        verificaEspecialidade(especialidade);
        verificaId(especialidade);
        return repo.save(especialidade);
    }

    @Transactional
    public Expertise atualizarArea(Expertise destino, Area areaNova){
        verificaEspecialidade(destino);
        verificaId(destino);
        AreaService.verificaArea(areaNova);
        AreaService.verificaId(areaNova);
        destino.setArea(areaNova);
        return repo.save(destino);
    }

    @Transactional
    public void remover(Expertise especialidade){
        verificaEspecialidade(especialidade);
        verificaId(especialidade);
        repo.delete(especialidade);
    }

    @Transactional
    public Expertise removerFeedback(Long id){
        verificaId(id);
        Optional<Expertise> especialidadesEncotradas = repo.findById(id);
        if (especialidadesEncotradas.isPresent()) {
            Expertise especialidadeFeedback = especialidadesEncotradas.get();
            repo.delete(especialidadeFeedback);
            return especialidadeFeedback;
        }
        return null;
    }

    @Transactional
    public void removerPorId(Long id){
        verificaId(id);
        repo.deleteById(id);
    }

    @Transactional
    public Expertise buscarPorId(Long id){
        verificaId(id);
        Optional<Expertise> especialidadeEncontradas = repo.findById(id);
        return especialidadeEncontradas.orElse(null);
    }

    @Transactional
    public List<Expertise> buscar(Expertise filtro){
        if (filtro == null)
            throw new NullPointerException("Filtro não pode ser nulo");
        Example<Expertise> example = Example.of(filtro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repo.findAll(example);
    }

    @Transactional
    public List<Expertise> buscarTodos(){
        return repo.findAll();
    }
}