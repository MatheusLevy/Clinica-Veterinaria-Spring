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

    protected static TipoAnimal getTipoAnimalInstance(Boolean temId){
        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("nome")
                .build();
        if(temId)
            tipoAnimal.setTipoAnimalId(Long.valueOf(1));
        return tipoAnimal;
    }
    @Test
    public void deveSalvarModel(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(getTipoAnimalInstance(false));
        Assertions.assertNotNull(tipoAnimalSalvo);
        Assertions.assertEquals(getTipoAnimalInstance(false).getNome(), tipoAnimalSalvo.getNome());
        tipoAnimalRepo.delete(tipoAnimalSalvo);
    }

    @Test
    public void deveAtualizar(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(getTipoAnimalInstance(false));
        tipoAnimalSalvo.setNome("Novo nome");
        TipoAnimal tipoAnimalAtualizado = tipoAnimalRepo.save(tipoAnimalSalvo);
        Assertions.assertNotNull(tipoAnimalAtualizado);
        Assertions.assertEquals(tipoAnimalAtualizado.getTipoAnimalId(), tipoAnimalSalvo.getTipoAnimalId());
        Assertions.assertEquals(tipoAnimalAtualizado.getNome(), "Novo nome");
        tipoAnimalRepo.delete(tipoAnimalAtualizado);
    }

    @Test
    public void deveRemoverModel(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(getTipoAnimalInstance(false));
        Long id = tipoAnimalSalvo.getTipoAnimalId();
        tipoAnimalRepo.deleteById(id);
        Assertions.assertFalse(tipoAnimalRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscarModel(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(getTipoAnimalInstance(false));
        Long id = tipoAnimalSalvo.getTipoAnimalId();
        Assertions.assertTrue(tipoAnimalRepo.findById(id).isPresent());
        tipoAnimalRepo.delete(tipoAnimalSalvo);
    }
}