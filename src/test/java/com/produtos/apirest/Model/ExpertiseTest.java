package com.produtos.apirest.Model;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.ExpertiseRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.produtos.apirest.Model.AreaTest.generateArea;
import static com.produtos.apirest.Model.AreaTest.rollbackArea;

@SpringBootTest
public class ExpertiseTest {
    @Autowired
    public ExpertiseRepo expertiseRepo;

    @Autowired
    public AreaRepo areaRepo;

    private Expertise generateExpertise(Boolean initArea){
        Expertise especialidade =  Expertise.builder()
                .name("name")
                .area(generateArea())
                .build();
         if (initArea)
             especialidade.setArea(areaRepo.save(especialidade.getArea()));
         return especialidade;
    }

    protected static Expertise generateExpertise(Boolean initArea, AreaRepo areaRepo){
        Expertise especialidade =  Expertise.builder()
                .name("name")
                .area(generateArea())
                .build();
        if (initArea)
            especialidade.setArea(areaRepo.save(especialidade.getArea()));
        return especialidade;
    }

    private void rollback(Expertise expertise){
        expertiseRepo.delete(expertise);
        areaRepo.delete(expertise.getArea());
    }

    protected static void rollbackEspecialidade(Expertise expertise,
                                                ExpertiseRepo expertiseRepo,
                                                AreaRepo areaRepo){
        expertiseRepo.delete(expertise);
        areaRepo.delete(expertise.getArea());
    }

    @Test
    public void save(){
        Expertise expertiseSaved = expertiseRepo.save(generateExpertise(true));
        Assertions.assertNotNull(expertiseSaved);
        Assertions.assertEquals(expertiseSaved.getName(), generateExpertise(false).getName());
        rollback(expertiseSaved);
    }

    @Test
    public void update(){
        Expertise expertiseSaved = expertiseRepo.save(generateExpertise(true));
        expertiseSaved.setName("New Name");
        Expertise expertiseUpdated = expertiseRepo.save(expertiseSaved);
        Assertions.assertNotNull(expertiseUpdated);
        Assertions.assertEquals(expertiseSaved.getExpertiseId(), expertiseUpdated.getExpertiseId());
        Assertions.assertEquals(expertiseUpdated.getName(),  "New Name");
        rollback(expertiseUpdated);
    }

    @Test
    public void updateArea(){
        Expertise expertiseSaved = expertiseRepo.save(generateExpertise(true));
        Area areaOld = expertiseSaved.getArea();
        expertiseSaved.setArea(areaRepo.save(generateArea()));
        Expertise expertiseUpdated = expertiseRepo.save(expertiseSaved);
        Assertions.assertNotNull(expertiseUpdated);
        Assertions.assertEquals(expertiseUpdated.getExpertiseId(), expertiseSaved.getExpertiseId());
        Assertions.assertEquals(expertiseUpdated.getArea().getAreaId(), expertiseSaved.getArea().getAreaId());
        rollback(expertiseUpdated);
        rollbackArea(areaOld, areaRepo);
    }

    @Test
    public void removeById() {
        Expertise expertiseSaved = expertiseRepo.save(generateExpertise(true));
        Long id = expertiseSaved.getExpertiseId();
        expertiseRepo.deleteById(id);
        Assertions.assertNull(expertiseRepo.findByExpertiseId(id));
        rollbackArea(expertiseSaved.getArea(), areaRepo);
    }

    @Test
    public void findById(){
        Expertise expertiseSaved = expertiseRepo.save(generateExpertise(true));
        Long id = expertiseSaved.getExpertiseId();
        Assertions.assertNotNull(expertiseRepo.findByExpertiseId(id));
        rollback(expertiseSaved);
    }
}