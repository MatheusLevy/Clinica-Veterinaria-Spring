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
    public AreaRepo repo;

    protected static Area getAreaInstance(Boolean temId){
        Area area = Area.builder()
                .nome("Nome")
                .build();
        if (temId)
            area.setAreaId(Long.valueOf(1));
        return area;
    }
    @Test
    public void deveSalvarModel(){
        Area areaSalva = repo.save(getAreaInstance(false));
        Assertions.assertNotNull(areaSalva);
        Assertions.assertEquals(getAreaInstance(false).getNome(), areaSalva.getNome());
        repo.delete(areaSalva);
    }

    @Test
    public void deveAtualizarArea(){
        Area areaSalva = repo.save(getAreaInstance(false));
        areaSalva.setNome("Nome Novo");
        Area areaAtualizado = repo.save(areaSalva);
        Assertions.assertNotNull(areaAtualizado);
        Assertions.assertEquals(areaAtualizado.getAreaId(), areaSalva.getAreaId());
        Assertions.assertEquals(areaAtualizado.getNome(), "Nome Novo");
        repo.delete(areaSalva);
    }

    @Test
    public void deveRemoverModel(){
        Area areaSalva = repo.save(getAreaInstance(false));
        Long id = areaSalva.getAreaId();
        repo.deleteById(id);
        Assertions.assertFalse(repo.findById(id).isPresent());
    }

    @Test
    public void deveBuscarModel(){
        Area areaSalva = repo.save(getAreaInstance(false));
        Optional<Area> temp = repo.findById(areaSalva.getAreaId());
        Assertions.assertTrue(temp.isPresent());
        repo.delete(areaSalva);
    }
}