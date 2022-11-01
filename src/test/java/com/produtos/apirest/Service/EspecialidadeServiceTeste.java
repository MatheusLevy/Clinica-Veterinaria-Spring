package com.produtos.apirest.Service;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.EspecialidadeRepo;
import com.produtos.apirest.service.EspecialidadeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.produtos.apirest.Service.AreaServiceTeste.generateArea;
import static com.produtos.apirest.Service.AreaServiceTeste.rollbackArea;


@SpringBootTest
public class EspecialidadeServiceTeste {

    @Autowired
    public EspecialidadeService especialidadeService;

    @Autowired
    public EspecialidadeRepo especialidadeRepo;

    @Autowired
    public AreaRepo areaRepo;


    private Especialidade generateEspecialidade(Boolean initializeArea){
        Especialidade especialidade = Especialidade.builder()
                .name("test")
                .area(generateArea())
                .build();
        if (initializeArea)
            especialidade.setArea(areaRepo.save(especialidade.getArea()));
        return especialidade;
    }

    protected static Especialidade generateEspecialidade(Boolean initializeArea, AreaRepo areaRepo){
        Especialidade especialidade = Especialidade.builder()
                .name("test")
                .area(generateArea())
                .build();
        if (initializeArea)
            especialidade.setArea(areaRepo.save(especialidade.getArea()));
        return especialidade;
    }

    private void rollback(Especialidade especialidade){
        especialidadeRepo.delete(especialidade);
        areaRepo.delete(especialidade.getArea());
    }

    protected static void rollbackEspecialidade(Especialidade especialidade, EspecialidadeRepo especialidadeRepo,
                                                AreaRepo areaRepo){
        especialidadeRepo.delete(especialidade);
        areaRepo.delete(especialidade.getArea());
    }

    @Test
    public void deveSalvar(){
        Especialidade especialidadeSalva = especialidadeService.salvar(generateEspecialidade(true));
        Assertions.assertNotNull(especialidadeSalva);
        rollback(especialidadeSalva);
    }

    @Test
    public void deveAtualizar(){
        Especialidade especialidadeSalva = especialidadeRepo.save(generateEspecialidade(true));
        especialidadeSalva.setName("Especialidade Atualizada");
        Especialidade especialidadeAtualizada = especialidadeService.atualizar(especialidadeSalva);
        Assertions.assertNotNull(especialidadeAtualizada);
        Assertions.assertEquals(especialidadeSalva.getExpertiseId(), especialidadeAtualizada.getExpertiseId());
        rollback(especialidadeSalva);
    }

    @Test
    public void deveAtualizarArea(){
        Especialidade especialidadeSalva = especialidadeRepo.save(generateEspecialidade(true));
        Area areaAntiga = especialidadeSalva.getArea();
        Area areaNova = areaRepo.save(generateArea());
        Especialidade especialidadeAtualizada = especialidadeService.atualizarArea(especialidadeSalva, areaNova);
        Assertions.assertNotNull(especialidadeAtualizada);
        Assertions.assertEquals(especialidadeAtualizada.getArea().getAreaId(), areaNova.getAreaId());
        Assertions.assertEquals(especialidadeAtualizada.getArea().getName(), areaNova.getName());
        rollback(especialidadeAtualizada);
        rollbackArea(areaAntiga, areaRepo);
    }
    @Test
    public void deveRemover(){
        Especialidade especialidadeSalva = especialidadeRepo.save(generateEspecialidade(true));
        Long id = especialidadeSalva.getExpertiseId();
        especialidadeService.remover(especialidadeSalva);
        Assertions.assertFalse(especialidadeRepo.findById(id).isPresent());
        rollbackArea(especialidadeSalva.getArea(), areaRepo);
    }

    @Test
    public void deveRemovercomFeedback(){
        Especialidade especialidadeSalva = especialidadeRepo.save(generateEspecialidade(true));
        Especialidade especialidadeFeedback = especialidadeService.removerFeedback(especialidadeSalva.getExpertiseId());
        Assertions.assertNotNull(especialidadeFeedback);
        Assertions.assertEquals(especialidadeSalva.getExpertiseId(), especialidadeFeedback.getExpertiseId());
        rollbackArea(especialidadeSalva.getArea(), areaRepo);
    }

    @Test
    public void deveRemoverPorId(){
        Especialidade especialidadeSalva = especialidadeRepo.save(generateEspecialidade(true));
        Long id = especialidadeSalva.getExpertiseId();
        especialidadeService.removerPorId(id);
        Assertions.assertFalse(especialidadeRepo.findById(id).isPresent());
        rollbackArea(especialidadeSalva.getArea(), areaRepo);
    }

    @Test
    public void deveBuscarPorId(){
        Especialidade especialidadeSalva = especialidadeRepo.save(generateEspecialidade(true));
        Long id = especialidadeSalva.getExpertiseId();
        Especialidade especialidadeEncontrada = especialidadeService.buscarPorId(id);
        Assertions.assertNotNull(especialidadeEncontrada);
        Assertions.assertEquals(especialidadeSalva.getExpertiseId(), especialidadeEncontrada.getExpertiseId());
        rollback(especialidadeSalva);
    }

    @Test
    public void deveBuscarPorFiltro(){
        Especialidade especialidadeSalva = especialidadeRepo.save(generateEspecialidade(true));
        List<Especialidade> especialidadesEncontradas = especialidadeService.buscar(especialidadeSalva);
        Assertions.assertFalse(especialidadesEncontradas.isEmpty());
        rollback(especialidadeSalva);
    }

    @Test
    public void deveBuscarTodos(){
        Especialidade especialidadeSalva = especialidadeRepo.save(generateEspecialidade(true));
        List<Especialidade> especialidades = especialidadeService.buscarTodos();
        Assertions.assertNotNull(especialidades);
        Assertions.assertFalse(especialidades.isEmpty());
        rollback(especialidadeSalva);
    }
}