package com.produtos.apirest.service;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.EspecialidadeRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadeService {

    @Autowired
    public EspecialidadeRepo repo;

    @Autowired
    public AreaRepo areaRepo;

    public static void verificaEspecialidade(Especialidade especialidade){
        if(especialidade == null)
            throw new NullPointerException("Especialidade não pode ser Nula!");
        if(especialidade.getNome() == null || especialidade.getNome().equals(""))
            throw new RegraNegocioRunTime("Especialidade deve ter um nome");
        if(especialidade.getArea() == null)
            throw new RegraNegocioRunTime("Especialidade deve ter uma Area!");
    }

    public static void verificaId(Especialidade especialidade){
        if(especialidade == null || Long.valueOf(especialidade.getEspecialidadeId()) == null)
            throw new RegraNegocioRunTime("Especialidade deve ter um identificador!s");
    }

    public static void verificaId(Long id){
        if(Long.valueOf(id) == null)
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
    public Especialidade atualizarArea(Especialidade especialidade, Area area){
        verificaEspecialidade(especialidade);
        verificaId(especialidade);
        AreaService.verificaArea(area);
        AreaService.verificaId(area);

        Optional<Especialidade> especialidadeTemp = repo.findById(especialidade.getEspecialidadeId());
        if (!especialidadeTemp.isPresent())
            throw new RegraNegocioRunTime("Não foi possivel encontrar a Especialidade");

        Optional<Area> areaTemp = areaRepo.findById(area.getAreaId());
        if (!areaTemp.isPresent())
            throw new RegraNegocioRunTime("Não foi possivel encontrar a Especialidade");

        Especialidade especialidade1 = especialidadeTemp.get();
        Area area1 = areaTemp.get();

        especialidade1.setArea(area1);
        return repo.save(especialidade1);
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
        Optional<Especialidade> especialidadeRemover = repo.findById(id);
        Especialidade especialidadeTemp = especialidadeRemover.get();
        repo.delete(especialidadeTemp);
        return especialidadeTemp;
    }

    @Transactional
    public void removerPorId(Long id){
        verificaId(id);
        repo.deleteById(id);
    }

    @Transactional
    public Especialidade buscarEspecialidadePorId(Long id){
        verificaId(id);
        return repo.findById(id).get();
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
