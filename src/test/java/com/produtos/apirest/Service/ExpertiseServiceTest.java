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

import static com.produtos.apirest.Service.AreaServiceTest.generateArea;
import static com.produtos.apirest.Service.AreaServiceTest.rollbackArea;


@SpringBootTest
public class ExpertiseServiceTest {

    @Autowired
    public ExpertiseService expertiseService;

    @Autowired
    public ExpertiseRepo expertiseRepo;

    @Autowired
    public AreaRepo areaRepo;


    private Expertise generateExpertise(Boolean initArea){
        Expertise especialidade = Expertise.builder()
                .name("name")
                .area(generateArea())
                .build();
        if (initArea)
            especialidade.setArea(areaRepo.save(especialidade.getArea()));
        return especialidade;
    }

    protected static Expertise generateExpertise(Boolean initArea, AreaRepo areaRepo){
        Expertise especialidade = Expertise.builder()
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

    protected static void rollbackExpertise(Expertise expertise, ExpertiseRepo expertiseRepo,
                                            AreaRepo areaRepo){
        expertiseRepo.delete(expertise);
        areaRepo.delete(expertise.getArea());
    }

    @Test
    public void save(){
        Expertise expertiseSaved = expertiseService.save(generateExpertise(true));
        Assertions.assertNotNull(expertiseSaved);
        rollback(expertiseSaved);
    }

    @Test
    public void update(){
        Expertise expertiseSaved = expertiseRepo.save(generateExpertise(true));
        expertiseSaved.setName("New Name");
        Expertise expertiseUpdated = expertiseService.update(expertiseSaved);
        Assertions.assertNotNull(expertiseUpdated);
        Assertions.assertEquals(expertiseSaved.getExpertiseId(), expertiseUpdated.getExpertiseId());
        rollback(expertiseSaved);
    }

    @Test
    public void updateArea(){
        Expertise expertiseSaved = expertiseRepo.save(generateExpertise(true));
        Area areaOld = expertiseSaved.getArea();
        Area areaNew = areaRepo.save(generateArea());
        Expertise expertiseUpdated = expertiseService.updateArea(expertiseSaved, areaNew);
        Assertions.assertNotNull(expertiseUpdated);
        Assertions.assertEquals(expertiseUpdated.getArea().getAreaId(), areaNew.getAreaId());
        Assertions.assertEquals(expertiseUpdated.getArea().getName(), areaNew.getName());
        rollback(expertiseUpdated);
        rollbackArea(areaOld, areaRepo);
    }
    @Test
    public void remove(){
        Expertise expertiseSaved = expertiseRepo.save(generateExpertise(true));
        Long id = expertiseSaved.getExpertiseId();
        expertiseService.remove(expertiseSaved);
        Assertions.assertNull(expertiseRepo.findByExpertiseId(id));
        rollbackArea(expertiseSaved.getArea(), areaRepo);
    }

    @Test
    public void removeByIdWithFeedback(){
        Expertise expertiseSaved = expertiseRepo.save(generateExpertise(true));
        Expertise feedback = expertiseService.removeByIdWithFeedback(expertiseSaved.getExpertiseId());
        Assertions.assertNotNull(feedback);
        Assertions.assertEquals(expertiseSaved.getExpertiseId(), feedback.getExpertiseId());
        rollbackArea(expertiseSaved.getArea(), areaRepo);
    }

    @Test
    public void removeById(){
        Expertise expertiseSaved = expertiseRepo.save(generateExpertise(true));
        Long id = expertiseSaved.getExpertiseId();
        expertiseService.removeById(id);
        Assertions.assertNull(expertiseRepo.findByExpertiseId(id));
        rollbackArea(expertiseSaved.getArea(), areaRepo);
    }

    @Test
    public void findById(){
        Expertise expertiseSaved = expertiseRepo.save(generateExpertise(true));
        Long id = expertiseSaved.getExpertiseId();
        Expertise expertiseFind = expertiseService.findById(id);
        Assertions.assertNotNull(expertiseFind);
        Assertions.assertEquals(expertiseSaved.getExpertiseId(), expertiseFind.getExpertiseId());
        rollback(expertiseSaved);
    }

    @Test
    public void find(){
        Expertise expertiseSaved = expertiseRepo.save(generateExpertise(true));
        List<Expertise> expertises = expertiseService.find(expertiseSaved);
        Assertions.assertFalse(expertises.isEmpty());
        rollback(expertiseSaved);
    }

    @Test
    public void findAll(){
        Expertise expertiseSaved = expertiseRepo.save(generateExpertise(true));
        List<Expertise> expertises = expertiseService.findAll();
        Assertions.assertNotNull(expertises);
        Assertions.assertFalse(expertises.isEmpty());
        rollback(expertiseSaved);
    }
}