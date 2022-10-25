package com.produtos.apirest.Service;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.EspecialidadeRepo;
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
    public EspecialidadeRepo especialidadeRepo;


    protected static Area generateArea(){
        return Area.builder()
                .nome("nome")
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
        Area areaSalva = areaService.salvar(generateArea());
        Assertions.assertNotNull(areaSalva);
        rollback(areaSalva);
    }

    @Test
    public void deveAtualizar(){
        Area areaSalva = areaRepo.save(generateArea());
        areaSalva.setNome("Area Atualizada");
        Area areaAtualizada = areaService.atualizar(areaSalva);
        Assertions.assertNotNull(areaAtualizada);
        Assertions.assertEquals(areaSalva.getAreaId(), areaAtualizada.getAreaId());
        rollback(areaAtualizada);
    }

    @Test
    public void deveRemover(){
        Area areaSalva = areaRepo.save(generateArea());
        Long id = areaSalva.getAreaId();
        areaService.remover(areaSalva);
        Assertions.assertFalse(areaRepo.findById(id).isPresent());
    }

    @Test
    public void deveRemoverPorId(){
        Area areSalva = areaRepo.save(generateArea());
        Long id = areSalva.getAreaId();
        areaService.removerPorId(id);
        Assertions.assertFalse(areaRepo.findById(id).isPresent());
    }

    @Test
    public void deveRemovercomFeedback(){
        Area areaSalva = areaRepo.save(generateArea());
        Area areaFeedback = areaService.removerComFeedback(areaSalva.getAreaId());
        Assertions.assertNotNull(areaFeedback);
        Assertions.assertEquals(areaFeedback.getAreaId(), areaSalva.getAreaId());
    }


    @Test
    public void deveBuscarPorId(){
        Area areaSalva = areaRepo.save(generateArea());
        Area AreaEncontrada = areaService.buscarPorId(areaSalva.getAreaId());
        Assertions.assertNotNull(AreaEncontrada);
        Assertions.assertEquals(areaSalva.getAreaId(), AreaEncontrada.getAreaId());
        Assertions.assertEquals(areaSalva.getNome(), AreaEncontrada.getNome());
        rollback(areaSalva);
    }

    @Test
    public void deveBuscar(){
        Area areaSalva = areaRepo.save(generateArea());
        Area filtro = Area.builder()
                .areaId(areaSalva.getAreaId())
                .nome(areaSalva.getNome())
                .build();
        Assertions.assertFalse(areaService.buscar(filtro).isEmpty());
        rollback(areaSalva);
    }

    @Test
    public void deveBuscarTodasEspecialidades(){
        Area areaSalva = areaRepo.save(generateArea());
        Especialidade especialidadeComAreaNaoSalva = generateEspecialidade(false, areaRepo);
        especialidadeComAreaNaoSalva.setArea(areaSalva);
        Especialidade especialidadeComAreaSalva = especialidadeComAreaNaoSalva;
        especialidadeRepo.save(especialidadeComAreaSalva);
        List<Especialidade> especialidadeList = areaService.buscarTodasEspecialidades(areaSalva);
        Assertions.assertFalse(especialidadeList.isEmpty());
        rollbackEspecialidade(especialidadeComAreaSalva, especialidadeRepo, areaRepo);
    }
}