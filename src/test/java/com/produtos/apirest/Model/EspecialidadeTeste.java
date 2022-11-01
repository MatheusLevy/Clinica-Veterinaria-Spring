package com.produtos.apirest.Model;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.ExpertiseRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.produtos.apirest.Model.AreaTeste.generateArea;
import static com.produtos.apirest.Model.AreaTeste.rollbackArea;

@SpringBootTest
public class EspecialidadeTeste {
    @Autowired
    public ExpertiseRepo expertiseRepo;

    @Autowired
    public AreaRepo areaRepo;

    private Expertise generateEspecialidade(Boolean inicializeArea){
        Expertise especialidade =  Expertise.builder()
                .name("name")
                .area(generateArea())
                .build();
         if (inicializeArea)
             especialidade.setArea(areaRepo.save(especialidade.getArea()));
         return especialidade;
    }

    protected static Expertise generateEspecialidade(Boolean inicializeArea, AreaRepo areaRepo){
        Expertise especialidade =  Expertise.builder()
                .name("name")
                .area(generateArea())
                .build();
        if (inicializeArea)
            especialidade.setArea(areaRepo.save(especialidade.getArea()));
        return especialidade;
    }

    private void rollback(Expertise especialidade){
        expertiseRepo.delete(especialidade);
        areaRepo.delete(especialidade.getArea());
    }

    protected static void rollbackEspecialidade(Expertise especialidade,
                                                ExpertiseRepo expertiseRepo,
                                                AreaRepo areaRepo){
        expertiseRepo.delete(especialidade);
        areaRepo.delete(especialidade.getArea());
    }

    @Test
    public void deveSalvar(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        Assertions.assertNotNull(especialidadeSalva);
        Assertions.assertEquals(especialidadeSalva.getName(), generateEspecialidade(false).getName());
        rollback(especialidadeSalva);
    }

    @Test
    public void deveAtualizar(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        especialidadeSalva.setName("Nome Atualizado");
        Expertise especialidadeAtualizada = expertiseRepo.save(especialidadeSalva);
        Assertions.assertNotNull(especialidadeAtualizada);
        Assertions.assertEquals(especialidadeSalva.getExpertiseId(), especialidadeAtualizada.getExpertiseId());
        Assertions.assertEquals(especialidadeAtualizada.getName(),  "Nome Atualizado");
        rollback(especialidadeAtualizada);
    }

    @Test
    public void deveAtualizarArea(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        Area areaAntiga = especialidadeSalva.getArea();
        especialidadeSalva.setArea(areaRepo.save(generateArea()));
        Expertise especialiadeAtualizada = expertiseRepo.save(especialidadeSalva);
        Assertions.assertNotNull(especialiadeAtualizada);
        Assertions.assertEquals(especialiadeAtualizada.getExpertiseId(), especialidadeSalva.getExpertiseId());
        Assertions.assertEquals(especialiadeAtualizada.getArea().getAreaId(), especialidadeSalva.getArea().getAreaId());
        rollback(especialiadeAtualizada);
        rollbackArea(areaAntiga, areaRepo);
    }

    @Test
    public void deveRemover() {
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        Long id = especialidadeSalva.getExpertiseId();
        expertiseRepo.deleteById(id);
        Assertions.assertFalse(expertiseRepo.findById(id).isPresent());
        rollbackArea(especialidadeSalva.getArea(), areaRepo);
    }

    @Test
    public void deveBuscar(){
        Expertise especialidadeSalva = expertiseRepo.save(generateEspecialidade(true));
        Long id = especialidadeSalva.getExpertiseId();
        Assertions.assertTrue(expertiseRepo.findById(id).isPresent());
        rollback(especialidadeSalva);
    }
}