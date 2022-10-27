package com.produtos.apirest.Model;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.EspecialidadeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.produtos.apirest.Model.AreaTeste.generateArea;
import static com.produtos.apirest.Model.AreaTeste.rollbackArea;

@SpringBootTest
public class EspecialidadeTeste {
    @Autowired
    public EspecialidadeRepo especialidadeRepo;

    @Autowired
    public AreaRepo areaRepo;

    private Especialidade generateEspecialidade(Boolean inicializeArea){
         Especialidade especialidade =  Especialidade.builder()
                .nome("nome")
                .area(generateArea())
                .build();
         if (inicializeArea)
             especialidade.setArea(areaRepo.save(especialidade.getArea()));
         return especialidade;
    }

    protected static Especialidade generateEspecialidade(Boolean inicializeArea, AreaRepo areaRepo){
        Especialidade especialidade =  Especialidade.builder()
                .nome("nome")
                .area(generateArea())
                .build();
        if (inicializeArea)
            especialidade.setArea(areaRepo.save(especialidade.getArea()));
        return especialidade;
    }

    private void rollback(Especialidade especialidade){
        especialidadeRepo.delete(especialidade);
        areaRepo.delete(especialidade.getArea());
    }

    protected static void rollbackEspecialidade(Especialidade especialidade,
                                                EspecialidadeRepo especialidadeRepo,
                                                AreaRepo areaRepo){
        especialidadeRepo.delete(especialidade);
        areaRepo.delete(especialidade.getArea());
    }

    @Test
    public void deveSalvar(){
        Especialidade especialidadeSalva = especialidadeRepo.save(generateEspecialidade(true));
        Assertions.assertNotNull(especialidadeSalva);
        Assertions.assertEquals(especialidadeSalva.getNome(), generateEspecialidade(false).getNome());
        rollback(especialidadeSalva);
    }

    @Test
    public void deveAtualizar(){
        Especialidade especialidadeSalva = especialidadeRepo.save(generateEspecialidade(true));
        especialidadeSalva.setNome("Nome Atualizado");
        Especialidade especialidadeAtualizada = especialidadeRepo.save(especialidadeSalva);
        Assertions.assertNotNull(especialidadeAtualizada);
        Assertions.assertEquals(especialidadeSalva.getEspecialidadeId(), especialidadeAtualizada.getEspecialidadeId());
        Assertions.assertEquals(especialidadeAtualizada.getNome(),  "Nome Atualizado");
        rollback(especialidadeAtualizada);
    }

    @Test
    public void deveAtualizarArea(){
        Especialidade especialidadeSalva = especialidadeRepo.save(generateEspecialidade(true));
        Area areaAntiga = especialidadeSalva.getArea();
        especialidadeSalva.setArea(areaRepo.save(generateArea()));
        Especialidade especialiadeAtualizada = especialidadeRepo.save(especialidadeSalva);
        Assertions.assertNotNull(especialiadeAtualizada);
        Assertions.assertEquals(especialiadeAtualizada.getEspecialidadeId(), especialidadeSalva.getEspecialidadeId());
        Assertions.assertEquals(especialiadeAtualizada.getArea().getAreaId(), especialidadeSalva.getArea().getAreaId());
        rollback(especialiadeAtualizada);
        rollbackArea(areaAntiga, areaRepo);
    }

    @Test
    public void deveRemover() {
        Especialidade especialidadeSalva = especialidadeRepo.save(generateEspecialidade(true));
        Long id = especialidadeSalva.getEspecialidadeId();
        especialidadeRepo.deleteById(id);
        Assertions.assertFalse(especialidadeRepo.findById(id).isPresent());
        rollbackArea(especialidadeSalva.getArea(), areaRepo);
    }

    @Test
    public void deveBuscar(){
        Especialidade especialidadeSalva = especialidadeRepo.save(generateEspecialidade(true));
        Long id = especialidadeSalva.getEspecialidadeId();
        Assertions.assertTrue(especialidadeRepo.findById(id).isPresent());
        rollback(especialidadeSalva);
    }
}