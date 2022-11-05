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

import static com.produtos.apirest.Service.ExpertiseServiceTest.generateExpertise;
import static com.produtos.apirest.Service.ExpertiseServiceTest.rollbackExpertise;

@SpringBootTest
public class AreaServiceTest {
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
    public void save(){
        Area areaSaved = areaService.save(generateArea());
        Assertions.assertNotNull(areaSaved);
        rollback(areaSaved);
    }

    @Test
    public void update(){
        Area areaSaved = areaRepo.save(generateArea());
        areaSaved.setName("New Name");
        Area areaUpdated = areaService.update(areaSaved);
        Assertions.assertNotNull(areaUpdated);
        Assertions.assertEquals(areaSaved.getAreaId(), areaUpdated.getAreaId());
        rollback(areaUpdated);
    }

    @Test
    public void remove(){
        Area areaSaved = areaRepo.save(generateArea());
        Long id = areaSaved.getAreaId();
        areaService.remove(areaSaved);
        Assertions.assertNull(areaRepo.findByAreaId(id));
    }

    @Test
    public void removeById(){
        Area areaSaved = areaRepo.save(generateArea());
        Long id = areaSaved.getAreaId();
        areaService.removeById(id);
        Assertions.assertNull(areaRepo.findByAreaId(id));
    }

    @Test
    public void removeByIdWithFeedback(){
        Area areaSaved = areaRepo.save(generateArea());
        Area feedback = areaService.removeByIdWithFeedback(areaSaved.getAreaId());
        Assertions.assertNotNull(feedback);
        Assertions.assertEquals(feedback.getAreaId(), areaSaved.getAreaId());
    }


    @Test
    public void findById(){
        Area areaSaved = areaRepo.save(generateArea());
        Area areaFind = areaService.findById(areaSaved.getAreaId());
        Assertions.assertNotNull(areaFind);
        Assertions.assertEquals(areaSaved.getAreaId(), areaFind.getAreaId());
        Assertions.assertEquals(areaSaved.getName(), areaFind.getName());
        rollback(areaSaved);
    }

    @Test
    public void find(){
        Area areaSaved = areaRepo.save(generateArea());
        Assertions.assertFalse(areaService.find(areaSaved).isEmpty());
        rollback(areaSaved);
    }

    @Test
    public void findAllExpertises(){
        Area areaSaved = areaRepo.save(generateArea());
        Expertise expertise = generateExpertise(false, areaRepo);
        expertise.setArea(areaSaved);
        expertiseRepo.save(expertise);
        List<Expertise> expertises = areaService.findAllExpertiseByAreaId(areaSaved.getAreaId());
        Assertions.assertFalse(expertises.isEmpty());
        rollbackExpertise(expertise, expertiseRepo, areaRepo);
    }
}