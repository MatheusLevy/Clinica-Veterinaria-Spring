package com.produtos.apirest.Model;

import com.produtos.apirest.models.Owner;
import com.produtos.apirest.repository.OwnerRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.produtos.apirest.Util.Util.generateCPF;
import static com.produtos.apirest.Util.Util.generateTelefone;

@SpringBootTest
public class DonoTeste {

    @Autowired
    public OwnerRepo ownerRepo;

    protected static Owner generateDono(){
        return Owner.builder()
                .name("name")
                .cpf(generateCPF())
                .phone(generateTelefone())
                .build();
    }

    private void rollback(Owner dono){
        ownerRepo.delete(dono);
    }

    protected static void rollbackDono(Owner dono, OwnerRepo ownerRepo){
        ownerRepo.delete(dono);
    }

    @Test
    public void deveSalvar(){
        Owner donoSalvo = ownerRepo.save(generateDono());
        Assertions.assertNotNull(donoSalvo);
        Assertions.assertEquals(generateDono().getName(), donoSalvo.getName());
        rollback(donoSalvo);
    }

    @Test
    public void deveAtualizar(){
        Owner donoSalvo = ownerRepo.save(generateDono());
        donoSalvo.setName("Novo nome");
        Owner donoAtualizado = ownerRepo.save(donoSalvo);
        Assertions.assertNotNull(donoAtualizado);
        Assertions.assertEquals(donoAtualizado.getOwnerId(), donoSalvo.getOwnerId());
        rollback(donoAtualizado);
    }

    @Test
    public void deveRemover(){
        Owner donoSalvo = ownerRepo.save(generateDono());
        Long id = donoSalvo.getOwnerId();
        ownerRepo.deleteById(id);
        Assertions.assertFalse(ownerRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscar(){
        Owner donoSalvo = ownerRepo.save(generateDono());
        Long id = donoSalvo.getOwnerId();
        Assertions.assertTrue(ownerRepo.findById(id).isPresent());
        rollback(donoSalvo);
    }
}