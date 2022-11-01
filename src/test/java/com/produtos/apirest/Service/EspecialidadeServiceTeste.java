package com.produtos.apirest.Service;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.ExpertiseRepo;
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
    public ExpertiseRepo expertiseRepo;

    @Autowired
    public AreaRepo areaRepo;


    private Expertise generateEspecialidade(Boolean initializeArea){
        Expertise especialidade = Expertise.builder()
                .name("test")
                .area(generateArea())
                .build();
        if (initializeArea)
            especialidade.setArea(areaRepo.save(especialidade.getArea()));
        return especialidade;
    }

    protected static Expertise generateEspecialidade(Boolean initializeArea, AreaRepo areaRepo){
        Expertise especialidade = Expertise.builder()
                .name("test")
                .area(generateArea())
                .build();
        if (initializeArea)
            especialidade.setArea(areaRepo.save(especialidade.getArea()));
        return especialidade;
    }

    private void rollback(Expertise especialidade){
        expertiseRepo.delete(especialidade);
        areaRepo.delete(especialidade.getArea());
    }

    protected static void rollbackEspecialidade(Expertise especialidade, ExpertiseRepo expertiseRepo,
                                                AreaRepo areaRepo){
        expertiseRepo.delete(especialidade);
        areaRepo.delete(especialidade.getArea());
    }

    @Test
    public void deveSalvar(){
        Expertise especialidadeSalva = especialidadeService.salvar(generateEspecialidade(true));
        Assertions.assertNotNull(especialidadeSalva);
        rollback(especialidadeSalva);
    }

    @Test
    public void deveAtualizar(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        especialidadeSalva.setName("Especialidade Atualizada");
        Expertise especialidadeAtualizada = especialidadeService.atualizar(especialidadeSalva);
        Assertions.assertNotNull(especialidadeAtualizada);
        Assertions.assertEquals(especialidadeSalva.getExpertiseId(), especialidadeAtualizada.getExpertiseId());
        rollback(especialidadeSalva);
    }

    @Test
    public void deveAtualizarArea(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        Area areaAntiga = especialidadeSalva.getArea();
        Area areaNova = areaRepo.save(generateArea());
        Expertise especialidadeAtualizada = especialidadeService.atualizarArea(especialidadeSalva, areaNova);
        Assertions.assertNotNull(especialidadeAtualizada);
        Assertions.assertEquals(especialidadeAtualizada.getArea().getAreaId(), areaNova.getAreaId());
        Assertions.assertEquals(especialidadeAtualizada.getArea().getName(), areaNova.getName());
        rollback(especialidadeAtualizada);
        rollbackArea(areaAntiga, areaRepo);
    }
    @Test
    public void deveRemover(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        Long id = especialidadeSalva.getExpertiseId();
        especialidadeService.remover(especialidadeSalva);
        Assertions.assertFalse(expertiseRepo.findById(id).isPresent());
        rollbackArea(especialidadeSalva.getArea(), areaRepo);
    }

    @Test
    public void deveRemovercomFeedback(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        Expertise especialidadeFeedback = especialidadeService.removerFeedback(especialidadeSalva.getExpertiseId());
        Assertions.assertNotNull(especialidadeFeedback);
        Assertions.assertEquals(especialidadeSalva.getExpertiseId(), especialidadeFeedback.getExpertiseId());
        rollbackArea(especialidadeSalva.getArea(), areaRepo);
    }

    @Test
    public void deveRemoverPorId(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        Long id = especialidadeSalva.getExpertiseId();
        especialidadeService.removerPorId(id);
        Assertions.assertFalse(expertiseRepo.findById(id).isPresent());
        rollbackArea(especialidadeSalva.getArea(), areaRepo);
    }

    @Test
    public void deveBuscarPorId(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        Long id = especialidadeSalva.getExpertiseId();
        Expertise especialidadeEncontrada = especialidadeService.buscarPorId(id);
        Assertions.assertNotNull(especialidadeEncontrada);
        Assertions.assertEquals(especialidadeSalva.getExpertiseId(), especialidadeEncontrada.getExpertiseId());
        rollback(especialidadeSalva);
    }

    @Test
    public void deveBuscarPorFiltro(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        List<Expertise> especialidadesEncontradas = especialidadeService.buscar(especialidadeSalva);
        Assertions.assertFalse(especialidadesEncontradas.isEmpty());
        rollback(especialidadeSalva);
    }

    @Test
    public void deveBuscarTodos(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        List<Expertise> especialidades = especialidadeService.buscarTodos();
        Assertions.assertNotNull(especialidades);
        Assertions.assertFalse(especialidades.isEmpty());
        rollback(especialidadeSalva);
    }
}