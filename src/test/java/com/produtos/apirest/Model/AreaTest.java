package com.produtos.apirest.Model;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.repository.AreaRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AreaTest {
    @Autowired
    public AreaRepo areaRepo;

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
    public void save(){
        Area areaSaved = areaRepo.save(generateArea());
        Assertions.assertNotNull(areaSaved);
        Assertions.assertEquals(generateArea().getName(), areaSaved.getName());
        rollback(areaSaved);
    }

    @Test
    public void update(){
        Area areaSaved = areaRepo.save(generateArea());
        areaSaved.setName("New Name");
        Area areaUpdated = areaRepo.save(areaSaved);
        Assertions.assertNotNull(areaUpdated);
        Assertions.assertEquals(areaUpdated.getAreaId(), areaSaved.getAreaId());
        Assertions.assertEquals(areaUpdated.getName(), "New Name");
        rollback(areaUpdated);
    }

    @Test
    public void removeById(){
        Area areaSaved = areaRepo.save(generateArea());
        Long id = areaSaved.getAreaId();
        areaRepo.deleteById(id);
        Assertions.assertNull(areaRepo.findByAreaId(id));
    }

    @Test
    public void findById(){
        Area areaSaved = areaRepo.save(generateArea());
        Long id = areaSaved.getAreaId();
        Assertions.assertNotNull(areaRepo.findByAreaId(id));
        rollback(areaSaved);
    }
}