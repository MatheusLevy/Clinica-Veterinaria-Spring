package com.produtos.apirest.Service;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.ExpertiseRepo;
import com.produtos.apirest.service.ExpertiseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.produtos.apirest.Service.AreaServiceTeste.generateArea;
import static com.produtos.apirest.Service.AreaServiceTeste.rollbackArea;


@SpringBootTest
public class ExpertiseServiceTeste {

    @Autowired
    public ExpertiseService expertiseService;

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
        Expertise especialidadeSalva = expertiseService.save(generateEspecialidade(true));
        Assertions.assertNotNull(especialidadeSalva);
        rollback(especialidadeSalva);
    }

    @Test
    public void deveAtualizar(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        especialidadeSalva.setName("Especialidade Atualizada");
        Expertise especialidadeAtualizada = expertiseService.update(especialidadeSalva);
        Assertions.assertNotNull(especialidadeAtualizada);
        Assertions.assertEquals(especialidadeSalva.getExpertiseId(), especialidadeAtualizada.getExpertiseId());
        rollback(especialidadeSalva);
    }

    @Test
    public void deveAtualizarArea(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        Area areaAntiga = especialidadeSalva.getArea();
        Area areaNova = areaRepo.save(generateArea());
        Expertise especialidadeAtualizada = expertiseService.updateArea(especialidadeSalva, areaNova);
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
        expertiseService.remove(especialidadeSalva);
        Assertions.assertFalse(expertiseRepo.findById(id).isPresent());
        rollbackArea(especialidadeSalva.getArea(), areaRepo);
    }

    @Test
    public void deveRemovercomFeedback(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        Expertise especialidadeFeedback = expertiseService.removeByIdWithFeedback(especialidadeSalva.getExpertiseId());
        Assertions.assertNotNull(especialidadeFeedback);
        Assertions.assertEquals(especialidadeSalva.getExpertiseId(), especialidadeFeedback.getExpertiseId());
        rollbackArea(especialidadeSalva.getArea(), areaRepo);
    }

    @Test
    public void deveRemoverPorId(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        Long id = especialidadeSalva.getExpertiseId();
        expertiseService.removeById(id);
        Assertions.assertFalse(expertiseRepo.findById(id).isPresent());
        rollbackArea(especialidadeSalva.getArea(), areaRepo);
    }

    @Test
    public void deveBuscarPorId(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        Long id = especialidadeSalva.getExpertiseId();
        Expertise especialidadeEncontrada = expertiseService.findById(id);
        Assertions.assertNotNull(especialidadeEncontrada);
        Assertions.assertEquals(especialidadeSalva.getExpertiseId(), especialidadeEncontrada.getExpertiseId());
        rollback(especialidadeSalva);
    }

    @Test
    public void deveBuscarPorFiltro(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        List<Expertise> especialidadesEncontradas = expertiseService.find(especialidadeSalva);
        Assertions.assertFalse(especialidadesEncontradas.isEmpty());
        rollback(especialidadeSalva);
    }

    @Test
    public void deveBuscarTodos(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        List<Expertise> especialidades = expertiseService.findAll();
        Assertions.assertNotNull(especialidades);
        Assertions.assertFalse(especialidades.isEmpty());
        rollback(especialidadeSalva);
    }
}