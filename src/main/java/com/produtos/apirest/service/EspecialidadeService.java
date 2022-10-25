package com.produtos.apirest.service;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.EspecialidadeRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadeService {

    private final EspecialidadeRepo repo;
    private final AreaRepo areaRepo;

    public EspecialidadeService(EspecialidadeRepo especialidadeRepo, AreaRepo areaRepo){
        this.repo = especialidadeRepo;
        this.areaRepo = areaRepo;
    }

    public static void verificaEspecialidade(Especialidade especialidade){
        if (especialidade == null)
            throw new NullPointerException("Especialidade não pode ser Nula!");
        if (especialidade.getNome() == null || especialidade.getNome().equals(""))
            throw new RegraNegocioRunTime("Especialidade deve ter um nome");
        if (especialidade.getArea() == null)
            throw new RegraNegocioRunTime("Especialidade deve ter uma Area!");
    }

    public static void verificaId(Especialidade especialidade){
        if (especialidade == null || especialidade.getEspecialidadeId() <= 0)
            throw new RegraNegocioRunTime("Especialidade deve ter um identificador!s");
    }

    public static void verificaId(Long id){
        if (id <= 0)
            throw new RegraNegocioRunTime("Especialidade deve ter um identificador!s");
    }

    @Transactional
    public Especialidade salvar(Especialidade especialidade){
        verificaEspecialidade(especialidade);
        return repo.save(especialidade);
    }

    @Transactional
    public Especialidade atualizar(Especialidade especialidade){
        verificaEspecialidade(especialidade);
        verificaId(especialidade);
        return repo.save(especialidade);
    }

    @Transactional
    public Especialidade atualizarArea(Especialidade destino, Area areaNova){
        verificaEspecialidade(destino);
        verificaId(destino);
        AreaService.verificaArea(areaNova);
        AreaService.verificaId(areaNova);

        Optional<Especialidade> especialidadesOptional = repo.findById(destino.getEspecialidadeId());
        if (especialidadesOptional.isEmpty())
            throw new RegraNegocioRunTime("Não foi possivel encontrar a Especialidade");

        Optional<Area> areasOptional = areaRepo.findById(areaNova.getAreaId());
        if (areasOptional.isEmpty())
            throw new RegraNegocioRunTime("Não foi possivel encontrar a Especialidade");

        Especialidade especialidadeDestinoEncontrada = especialidadesOptional.get();
        Area areaNovaEncontrada = areasOptional.get();

        especialidadeDestinoEncontrada.setArea(areaNovaEncontrada);
        return repo.save(especialidadeDestinoEncontrada);
    }

    @Transactional
    public void remover(Especialidade especialidade){
        verificaEspecialidade(especialidade);
        verificaId(especialidade);
        repo.delete(especialidade);
    }

    @Transactional
    public Especialidade removerFeedback(Long id){
        verificaId(id);
        Optional<Especialidade> especialidadesEncotradas = repo.findById(id);
        if (especialidadesEncotradas.isPresent()) {
            Especialidade especialidadeFeedback = especialidadesEncotradas.get();
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
    public Especialidade buscarPorId(Long id){
        verificaId(id);
        Optional<Especialidade> especialidadeEncontradas = repo.findById(id);
        return especialidadeEncontradas.orElse(null);
    }

    @Transactional
    public List<Especialidade> buscar(Especialidade filtro){
        if (filtro == null)
            throw new NullPointerException("Filtro não pode ser nulo");
        Example<Especialidade> example = Example.of(filtro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repo.findAll(example);
    }

    @Transactional
    public List<Especialidade> buscarTodos(){
        return repo.findAll();
    }
}