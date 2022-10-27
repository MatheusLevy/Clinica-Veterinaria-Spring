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
                .nome("nome")
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
        Assertions.assertEquals(generateTipoAnimal().getNome(), tipoAnimalSalvo.getNome());
        rollback(tipoAnimalSalvo);
    }

    @Test
    public void deveAtualizar(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(generateTipoAnimal());
        tipoAnimalSalvo.setNome("Novo nome");
        TipoAnimal tipoAnimalAtualizado = tipoAnimalRepo.save(tipoAnimalSalvo);
        Assertions.assertNotNull(tipoAnimalAtualizado);
        Assertions.assertEquals(tipoAnimalAtualizado.getTipoAnimalId(), tipoAnimalSalvo.getTipoAnimalId());
        Assertions.assertEquals(tipoAnimalAtualizado.getNome(), "Novo nome");
        rollback(tipoAnimalAtualizado);
    }

    @Test
    public void deveRemover(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(generateTipoAnimal());
        Long id = tipoAnimalSalvo.getTipoAnimalId();
        tipoAnimalRepo.deleteById(id);
        Assertions.assertFalse(tipoAnimalRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscar(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(generateTipoAnimal());
        Long id = tipoAnimalSalvo.getTipoAnimalId();
        Assertions.assertTrue(tipoAnimalRepo.findById(id).isPresent());
        rollback(tipoAnimalSalvo);
    }
}