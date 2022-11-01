package com.produtos.apirest.Model;

import com.produtos.apirest.models.Dono;
import com.produtos.apirest.repository.DonoRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.produtos.apirest.Util.Util.*;

@SpringBootTest
public class DonoTeste {

    @Autowired
    public DonoRepo donoRepo;

    protected static  Dono generateDono(){
        return Dono.builder()
                .name("name")
                .cpf(generateCPF())
                .phone(generateTelefone())
                .build();
    }

    private void rollback(Dono dono){
        donoRepo.delete(dono);
    }

    protected static void rollbackDono(Dono dono, DonoRepo donoRepo){
        donoRepo.delete(dono);
    }

    @Test
    public void deveSalvar(){
        Dono donoSalvo = donoRepo.save(generateDono());
        Assertions.assertNotNull(donoSalvo);
        Assertions.assertEquals(generateDono().getName(), donoSalvo.getName());
        rollback(donoSalvo);
    }

    @Test
    public void deveAtualizar(){
        Dono donoSalvo = donoRepo.save(generateDono());
        donoSalvo.setName("Novo nome");
        Dono donoAtualizado = donoRepo.save(donoSalvo);
        Assertions.assertNotNull(donoAtualizado);
        Assertions.assertEquals(donoAtualizado.getDonoId(), donoSalvo.getDonoId());
        rollback(donoAtualizado);
    }

    @Test
    public void deveRemover(){
        Dono donoSalvo = donoRepo.save(generateDono());
        Long id = donoSalvo.getDonoId();
        donoRepo.deleteById(id);
        Assertions.assertFalse(donoRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscar(){
        Dono donoSalvo = donoRepo.save(generateDono());
        Long id = donoSalvo.getDonoId();
        Assertions.assertTrue(donoRepo.findById(id).isPresent());
        rollback(donoSalvo);
    }
}