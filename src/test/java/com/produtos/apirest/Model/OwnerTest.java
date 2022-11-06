package com.produtos.apirest.Model;

import com.produtos.apirest.models.Owner;
import com.produtos.apirest.repository.OwnerRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.produtos.apirest.Util.Util.generateCPF;
import static com.produtos.apirest.Util.Util.generatePhone;

@SpringBootTest
public class OwnerTest {

    @Autowired
    public OwnerRepo ownerRepo;

    protected static Owner generateOwner(){
        return Owner.builder()
                .name("name")
                .cpf(generateCPF())
                .phone(generatePhone())
                .build();
    }

    private void rollback(Owner owner){
        ownerRepo.delete(owner);
    }

    protected static void rollbackOwner(Owner owner, OwnerRepo ownerRepo){
        ownerRepo.delete(owner);
    }

    @Test
    public void save(){
        Owner ownerSaved = ownerRepo.save(generateOwner());
        Assertions.assertNotNull(ownerSaved);
        Assertions.assertEquals(generateOwner().getName(), ownerSaved.getName());
        rollback(ownerSaved);
    }

    @Test
    public void update(){
        Owner ownerSaved = ownerRepo.save(generateOwner());
        ownerSaved.setName("New Name");
        Owner ownerUpdated = ownerRepo.save(ownerSaved);
        Assertions.assertNotNull(ownerUpdated);
        Assertions.assertEquals(ownerUpdated.getOwnerId(), ownerSaved.getOwnerId());
        rollback(ownerUpdated);
    }

    @Test
    public void removeById(){
        Owner ownerSaved = ownerRepo.save(generateOwner());
        Long id = ownerSaved.getOwnerId();
        ownerRepo.deleteById(id);
        Assertions.assertNull(ownerRepo.findByOwnerId(id));
    }

    @Test
    public void findById(){
        Owner ownerSaved = ownerRepo.save(generateOwner());
        Long id = ownerSaved.getOwnerId();
        Assertions.assertNotNull(ownerRepo.findByOwnerId(id));
        rollback(ownerSaved);
    }
}