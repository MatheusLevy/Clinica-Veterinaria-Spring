package com.produtos.apirest.Model;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.repository.AreaRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class AreaTeste {
    @Autowired
    public AreaRepo areaRepo;

    protected static Area generateArea(){
        return Area.builder()
                .nome("Nome")
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
        Area areaSalva = areaRepo.save(generateArea());
        Assertions.assertNotNull(areaSalva);
        Assertions.assertEquals(generateArea().getNome(), areaSalva.getNome());
        rollback(areaSalva);
    }

    @Test
    public void deveAtualizar(){
        Area areaSalva = areaRepo.save(generateArea());
        areaSalva.setNome("Nome Novo");
        Area areaAtualizado = areaRepo.save(areaSalva);
        Assertions.assertNotNull(areaAtualizado);
        Assertions.assertEquals(areaAtualizado.getAreaId(), areaSalva.getAreaId());
        Assertions.assertEquals(areaAtualizado.getNome(), "Nome Novo");
        rollback(areaAtualizado);
    }

    @Test
    public void deveRemover(){
        Area areaSalva = areaRepo.save(generateArea());
        Long id = areaSalva.getAreaId();
        areaRepo.deleteById(id);
        Assertions.assertFalse(areaRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscar(){
        Area areaSalva = areaRepo.save(generateArea());
        Optional<Area> temp = areaRepo.findById(areaSalva.getAreaId());
        Assertions.assertTrue(temp.isPresent());
        rollback(areaSalva);
    }
}