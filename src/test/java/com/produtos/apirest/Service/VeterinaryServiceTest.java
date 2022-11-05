package com.produtos.apirest.Service;

import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.ExpertiseRepo;
import com.produtos.apirest.repository.VeterinaryRepo;
import com.produtos.apirest.service.VeterinarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.produtos.apirest.Service.ExpertiseServiceTest.generateExpertise;
import static com.produtos.apirest.Service.ExpertiseServiceTest.rollbackExpertise;
import static com.produtos.apirest.Util.Util.generateCPF;
import static com.produtos.apirest.Util.Util.generatePhone;

@SpringBootTest
public class VeterinaryServiceTest {

    @Autowired
    public VeterinaryRepo veterinaryRepo;

    @Autowired
    public VeterinarioService veterinarioService;

    @Autowired
    public AreaRepo areaRepo;

    @Autowired
    public ExpertiseRepo expertiseRepo;


    private Veterinary generateVeterinary(Boolean initFields){
        Veterinary veterinary = Veterinary.builder()
                .name("name")
                .phone(generatePhone())
                .cpf(generateCPF())
                .expertise(generateExpertise(false, areaRepo))
                .build();
        if (initFields) {
            Expertise expertise = generateExpertise(true, areaRepo);
            veterinary.setExpertise(expertiseRepo.save(expertise));
        }
        return veterinary;
    }

    protected static Veterinary generateVeterinary(Boolean initFields, AreaRepo areaRepo,
                                                   ExpertiseRepo expertiseRepo){
        Veterinary veterinary = Veterinary.builder()
                .name("name")
                .phone(generatePhone())
                .cpf(generateCPF())
                .expertise(generateExpertise(false, areaRepo))
                .build();
        if (initFields) {
            Expertise expertise = generateExpertise(true, areaRepo);
            veterinary.setExpertise(expertiseRepo.save(expertise));
        }
        return veterinary;
    }

    private void rollback(Veterinary veterinary, Boolean skipVeterinary){
        if (!skipVeterinary)
            veterinaryRepo.delete(veterinary);
        expertiseRepo.delete(veterinary.getExpertise());
        areaRepo.delete(veterinary.getExpertise().getArea());
    }

    protected static void rollbackVeterinary(Veterinary veterinary, VeterinaryRepo veterinaryRepo,
                                             AreaRepo areaRepo, ExpertiseRepo expertiseRepo,
                                             Boolean skipVeterinary){
        if (!skipVeterinary)
            veterinaryRepo.delete(veterinary);
        expertiseRepo.delete(veterinary.getExpertise());
        areaRepo.delete(veterinary.getExpertise().getArea());
    }

    @Test
    public void save(){
        Veterinary veterinarySaved = veterinarioService.save(generateVeterinary(true));
        Assertions.assertNotNull(veterinarySaved);
        rollback(veterinarySaved, false);
    }

    @Test
    public void update(){
        Veterinary veterinarySaved = veterinaryRepo.save(generateVeterinary(true));
        veterinarySaved.setName("New Name");
        Veterinary veterinaryUpdated = veterinarioService.update(veterinarySaved);
        Assertions.assertNotNull(veterinaryUpdated);
        Assertions.assertEquals(veterinaryUpdated.getVeterinaryId(), veterinarySaved.getVeterinaryId());
        Assertions.assertEquals(veterinaryUpdated.getName(), "New Name");
        rollback(veterinaryUpdated, false);
    }

    @Test
    public void removeById(){
        Veterinary veterinarySaved = veterinaryRepo.save(generateVeterinary(true));
        Long id = veterinarySaved.getVeterinaryId();
        veterinarioService.removeById(veterinarySaved.getVeterinaryId());
        Assertions.assertNull(veterinaryRepo.findByVeterinaryId(id));
        rollback(veterinarySaved, true);
    }

    @Test
    public void removeByIdWithFeedback(){
        Veterinary veterinarySaved = veterinaryRepo.save(generateVeterinary(true));
        Veterinary feedback = veterinarioService.removeByIdWithFeedback(veterinarySaved.getVeterinaryId());
        Assertions.assertNotNull(feedback);
        Assertions.assertEquals(veterinarySaved.getVeterinaryId(), feedback.getVeterinaryId());
        rollback(feedback, true);
    }

    @Test
    public void findById(){
        Veterinary veterinarySaved = veterinaryRepo.save(generateVeterinary(true));
        Long id = veterinarySaved.getVeterinaryId();
        Veterinary veterinaryFind = veterinarioService.findById(id);
        Assertions.assertNotNull(veterinaryFind);
        Assertions.assertEquals(veterinarySaved.getVeterinaryId(), veterinaryFind.getVeterinaryId());
        rollback(veterinarySaved, false);
    }

    @Test
    public void find(){
        Veterinary veterinarySaved = veterinaryRepo.save(generateVeterinary(true));
        List<Veterinary> veterinaries = veterinarioService.find(veterinarySaved);
        Assertions.assertNotNull(veterinaries);
        Assertions.assertFalse(veterinaries.isEmpty());
        rollback(veterinarySaved, false);
    }

    @Test
    public void findExpertiseById(){
        Veterinary veterinarySaved = veterinaryRepo.save(generateVeterinary(true));
        Expertise expertiseFind = veterinarioService.findExpertisesById(veterinarySaved.getVeterinaryId());
        Assertions.assertNotNull(expertiseFind);
        Assertions.assertEquals(veterinarySaved.getExpertise().getExpertiseId(), expertiseFind.getExpertiseId());
        rollback(veterinarySaved, false);
    }

    @Test
    public void updateExpertise(){
        Veterinary veterinarySaved = veterinaryRepo.save(generateVeterinary(true));
        Expertise expertiseOld = veterinarySaved.getExpertise();
        Expertise expertiseNew = expertiseRepo.save(generateExpertise(true, areaRepo));
        Veterinary veterinaryUpdated = veterinarioService.updateExpertise(veterinarySaved, expertiseNew);
        Assertions.assertNotNull(veterinaryUpdated);
        Assertions.assertEquals(veterinarySaved.getVeterinaryId(), veterinaryUpdated.getVeterinaryId());
        rollback(veterinaryUpdated, false);
        rollbackExpertise(expertiseOld, expertiseRepo, areaRepo);
    }

    @Test
    public void findAll(){
        Veterinary veterinarySaved = veterinarioService.save(generateVeterinary(true));
        List<Veterinary> veterinaries = veterinarioService.findAll();
        Assertions.assertNotNull(veterinaries);
        Assertions.assertFalse(veterinaries.isEmpty());
        rollback(veterinarySaved, false);
    }
}