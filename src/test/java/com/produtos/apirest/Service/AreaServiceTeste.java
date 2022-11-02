package com.produtos.apirest.Service;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.ExpertiseRepo;
import com.produtos.apirest.service.AreaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.produtos.apirest.Service.EspecialidadeServiceTeste.generateEspecialidade;
import static com.produtos.apirest.Service.EspecialidadeServiceTeste.rollbackEspecialidade;

@SpringBootTest
public class AreaServiceTeste {
    @Autowired
    public AreaRepo areaRepo;

    @Autowired
    public AreaService areaService;

    @Autowired
    public ExpertiseRepo expertiseRepo;


    protected static Area generateArea(){
        return Area.builder()
                .name("name")
                .build();
    }

    private void rollback(Area area){
        areaRepo.delete(area);
    }

    protected static void rollbackArea(Area area, AreaRepo areaRepo){
        areaRepo.delete(area);
    }

    @Test
    public void deveSalvar(){
        Area areaSalva = areaService.save(generateArea());
        Assertions.assertNotNull(areaSalva);
        rollback(areaSalva);
    }

    @Test
    public void deveAtualizar(){
        Area areaSalva = areaRepo.save(generateArea());
        areaSalva.setName("Area Atualizada");
        Area areaAtualizada = areaService.update(areaSalva);
        Assertions.assertNotNull(areaAtualizada);
        Assertions.assertEquals(areaSalva.getAreaId(), areaAtualizada.getAreaId());
        rollback(areaAtualizada);
    }

    @Test
    public void deveRemover(){
        Area areaSalva = areaRepo.save(generateArea());
        Long id = areaSalva.getAreaId();
        areaService.remove(areaSalva);
        Assertions.assertFalse(areaRepo.findById(id).isPresent());
    }

    @Test
    public void deveRemoverPorId(){
        Area areSalva = areaRepo.save(generateArea());
        Long id = areSalva.getAreaId();
        areaService.removeById(id);
        Assertions.assertFalse(areaRepo.findById(id).isPresent());
    }

    @Test
    public void deveRemovercomFeedback(){
        Area areaSalva = areaRepo.save(generateArea());
        Area areaFeedback = areaService.removeByIdWithFeedback(areaSalva.getAreaId());
        Assertions.assertNotNull(areaFeedback);
        Assertions.assertEquals(areaFeedback.getAreaId(), areaSalva.getAreaId());
    }


    @Test
    public void deveBuscarPorId(){
        Area areaSalva = areaRepo.save(generateArea());
        Area AreaEncontrada = areaService.findById(areaSalva.getAreaId());
        Assertions.assertNotNull(AreaEncontrada);
        Assertions.assertEquals(areaSalva.getAreaId(), AreaEncontrada.getAreaId());
        Assertions.assertEquals(areaSalva.getName(), AreaEncontrada.getName());
        rollback(areaSalva);
    }

    @Test
    public void deveBuscar(){
        Area areaSalva = areaRepo.save(generateArea());
        Area filtro = Area.builder()
                .areaId(areaSalva.getAreaId())
                .name(areaSalva.getName())
                .build();
        Assertions.assertFalse(areaService.find(filtro).isEmpty());
        rollback(areaSalva);
    }

    @Test
    public void deveBuscarTodasEspecialidades(){
        Area areaSalva = areaRepo.save(generateArea());
        Expertise especialidadeComAreaNaoSalva = generateEspecialidade(false, areaRepo);
        especialidadeComAreaNaoSalva.setArea(areaSalva);
        expertiseRepo.save(especialidadeComAreaNaoSalva);
        List<Expertise> especialidadeList = areaService.findAllExpertiseByAreaId(areaSalva.getAreaId());
        Assertions.assertFalse(especialidadeList.isEmpty());
        rollbackEspecialidade(especialidadeComAreaNaoSalva, expertiseRepo, areaRepo);
    }
}