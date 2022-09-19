package com.produtos.apirest.Serviços;

import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.repository.TipoAnimalRepo;
import com.produtos.apirest.service.Tipo_animalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class TipoAnimalServiceTeste {

    @Autowired
    public Tipo_animalService tipo_animalService;

    @Autowired
    public TipoAnimalRepo tipo_animalRepo;

    @Test
    public void deveSalvar(){
        TipoAnimal tipo_animal = TipoAnimal.builder().nome("Tipo Animal Teste").build();
        TipoAnimal tipo_animalRetorno = tipo_animalService.salvar(tipo_animal);
        Assertions.assertNotNull(tipo_animalRetorno);
        Assertions.assertNotNull(tipo_animalRetorno.getTipoAnimalId());
        tipo_animalRepo.delete(tipo_animalRetorno);
    }

    @Test
    public void deveAtualizar(){
        TipoAnimal tipo_animal = TipoAnimal.builder().nome("Tipo Animal Teste").build();
        TipoAnimal tipo_animalRetorno = tipo_animalRepo.save(tipo_animal);
        tipo_animalRetorno.setNome("Tipo Animal Atualizado");
        TipoAnimal tipo_animalAtualizado = tipo_animalService.atualizar(tipo_animalRetorno);
        Assertions.assertNotNull(tipo_animalAtualizado);
        Assertions.assertEquals(tipo_animalRetorno.getTipoAnimalId(), tipo_animalAtualizado.getTipoAnimalId());
        tipo_animalRepo.delete(tipo_animalRetorno);
    }

    @Test
    public void deveRemover(){
        TipoAnimal tipo_animal = TipoAnimal.builder().nome("Tipo Animal Teste").build();
        TipoAnimal tipo_animalRetorno = tipo_animalRepo.save(tipo_animal);
        tipo_animalService.remover(tipo_animalRetorno);
        Optional<TipoAnimal> tipo_animalTemp = tipo_animalRepo.findById(tipo_animalRetorno.getTipoAnimalId());
        Assertions.assertTrue(!tipo_animalTemp.isPresent());
    }


}
