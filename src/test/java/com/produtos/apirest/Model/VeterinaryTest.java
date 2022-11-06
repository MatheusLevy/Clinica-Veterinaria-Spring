package com.produtos.apirest.Model;

import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.ExpertiseRepo;
import com.produtos.apirest.repository.VeterinaryRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.produtos.apirest.Model.ExpertiseTest.generateExpertise;
import static com.produtos.apirest.Model.ExpertiseTest.rollbackEspecialidade;
import static com.produtos.apirest.Util.Util.generateCPF;
import static com.produtos.apirest.Util.Util.generatePhone;

@SpringBootTest
public class VeterinaryTest {
    @Autowired
    public VeterinaryRepo veterinaryRepo;

    @Autowired
    public AreaRepo areaRepo;

    @Autowired
    public ExpertiseRepo expertiseRepo;

    private Veterinary generateVeterinary(Boolean initExpertise){
        Veterinary veterinary = Veterinary.builder()
                .name("name")
                .cpf(generateCPF())
                .phone(generatePhone())
                .build();
        if (initExpertise)
            veterinary.setExpertise(expertiseRepo.save(generateExpertise(true, areaRepo)));
        return veterinary;
    }

    protected static Veterinary generateVeterinary(Boolean initExpertise,
                                                   ExpertiseRepo expertiseRepo,
                                                   AreaRepo areaRepo){
        Veterinary veterinary = Veterinary.builder()
                .name("name")
                .cpf(generateCPF())
                .phone(generatePhone())
                .expertise(generateExpertise(true, areaRepo))
                .build();
        if (initExpertise)
            veterinary.setExpertise(
                    expertiseRepo.save(veterinary.getExpertise())
            );
        return veterinary;
    }

    private void rollback(Veterinary veterinary){
        veterinaryRepo.delete(veterinary);
        expertiseRepo.delete(veterinary.getExpertise());
        areaRepo.delete(veterinary.getExpertise().getArea());
    }

    protected static void rollbackVeterinary(Veterinary veterinary,
                                             VeterinaryRepo veterinaryRepo,
                                             AreaRepo areaRepo,
                                             ExpertiseRepo expertiseRepo){
        veterinaryRepo.delete(veterinary);
        expertiseRepo.delete(veterinary.getExpertise());
        areaRepo.delete(veterinary.getExpertise().getArea());
    }

    @Test
    public void save(){
        Veterinary veterinarySaved = veterinaryRepo.save(generateVeterinary(true));
        Assertions.assertNotNull(veterinarySaved);
        Assertions.assertEquals(veterinarySaved.getName(), generateVeterinary(false).getName());
        rollback(veterinarySaved);
    }

    @Test
    public void update(){
        Veterinary veterinarySaved = veterinaryRepo.save(generateVeterinary(true));
        veterinarySaved.setName("New Name");
        Veterinary veterinaryUpdated = veterinaryRepo.save(veterinarySaved);
        Assertions.assertNotNull(veterinaryUpdated);
        Assertions.assertEquals(veterinaryUpdated.getVeterinaryId(), veterinarySaved.getVeterinaryId());
        Assertions.assertEquals(veterinaryUpdated.getName(), "New Name");
        rollback(veterinaryUpdated);
    }

    @Test
    public void updateExpertise(){
        Veterinary veterinarySaved = veterinaryRepo.save(generateVeterinary(true));
        Expertise expertiseOld = veterinarySaved.getExpertise();
        veterinarySaved.setExpertise(expertiseRepo.save(generateExpertise(true, areaRepo)));
        Veterinary veterinaryUpdated = veterinaryRepo.save(veterinarySaved);
        Assertions.assertNotNull(veterinaryUpdated);
        Assertions.assertEquals(veterinaryUpdated.getVeterinaryId(), veterinarySaved.getVeterinaryId());
        rollback(veterinaryUpdated);
        rollbackEspecialidade(expertiseOld, expertiseRepo, areaRepo);
    }

    @Test
    public void removeById(){
        Veterinary veterinarySaved = veterinaryRepo.save(generateVeterinary(true));
        Long id  = veterinarySaved.getVeterinaryId();
        veterinaryRepo.deleteById(id);
        Assertions.assertNull(veterinaryRepo.findByVeterinaryId(id));
        rollbackEspecialidade(veterinarySaved.getExpertise(), expertiseRepo, areaRepo);
    }

    @Test
    public void findById(){
        Veterinary veterinarySaved = veterinaryRepo.save(generateVeterinary(true));
        Long id = veterinarySaved.getVeterinaryId();
        Assertions.assertNotNull(veterinaryRepo.findByVeterinaryId(id));
        rollback(veterinarySaved);
    }
}