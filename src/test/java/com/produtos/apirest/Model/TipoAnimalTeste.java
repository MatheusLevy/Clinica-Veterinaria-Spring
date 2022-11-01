package com.produtos.apirest.Model;

import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.repository.TipoAnimalRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TipoAnimalTeste {
    @Autowired
    public TipoAnimalRepo tipoAnimalRepo;

    protected static TipoAnimal generateTipoAnimal(){
       return TipoAnimal.builder()
                .name("name")
                .build();
    }

    private void rollback(TipoAnimal tipoAnimal){
        tipoAnimalRepo.delete(tipoAnimal);
    }

    protected static void rollbackTipoAnimal(TipoAnimal tipoAnimal, TipoAnimalRepo tipoAnimalRepo){
        tipoAnimalRepo.delete(tipoAnimal);
    }

    @Test
    public void deveSalvar(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(generateTipoAnimal());
        Assertions.assertNotNull(tipoAnimalSalvo);
        Assertions.assertEquals(generateTipoAnimal().getName(), tipoAnimalSalvo.getName());
        rollback(tipoAnimalSalvo);
    }

    @Test
    public void deveAtualizar(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(generateTipoAnimal());
        tipoAnimalSalvo.setName("Novo nome");
        TipoAnimal tipoAnimalAtualizado = tipoAnimalRepo.save(tipoAnimalSalvo);
        Assertions.assertNotNull(tipoAnimalAtualizado);
        Assertions.assertEquals(tipoAnimalAtualizado.getAnimalTypeId(), tipoAnimalSalvo.getAnimalTypeId());
        Assertions.assertEquals(tipoAnimalAtualizado.getName(), "Novo nome");
        rollback(tipoAnimalAtualizado);
    }

    @Test
    public void deveRemover(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(generateTipoAnimal());
        Long id = tipoAnimalSalvo.getAnimalTypeId();
        tipoAnimalRepo.deleteById(id);
        Assertions.assertFalse(tipoAnimalRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscar(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(generateTipoAnimal());
        Long id = tipoAnimalSalvo.getAnimalTypeId();
        Assertions.assertTrue(tipoAnimalRepo.findById(id).isPresent());
        rollback(tipoAnimalSalvo);
    }
}