package com.produtos.apirest.Serviços;

import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.repository.TipoAnimalRepo;
import com.produtos.apirest.service.TipoAnimalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class TipoAnimalServiceTeste {

    @Autowired
    public TipoAnimalService tipoAnimalService;

    @Autowired
    public TipoAnimalRepo tipoAnimalRepo;

    @Test
    public void deveSalvar(){
        TipoAnimal tipo_animal = TipoAnimal.builder().nome("Tipo Animal Teste").build();
        TipoAnimal tipo_animalRetorno = tipoAnimalService.salvar(tipo_animal);
        Assertions.assertNotNull(tipo_animalRetorno);
        Assertions.assertNotNull(tipo_animalRetorno.getTipoAnimalId());
        tipoAnimalRepo.delete(tipo_animalRetorno);
    }

    @Test
    public void deveAtualizar(){
        TipoAnimal tipo_animal = TipoAnimal.builder().nome("Tipo Animal Teste").build();
        TipoAnimal tipo_animalRetorno = tipoAnimalRepo.save(tipo_animal);
        tipo_animalRetorno.setNome("Tipo Animal Atualizado");
        TipoAnimal tipo_animalAtualizado = tipoAnimalService.atualizar(tipo_animalRetorno);
        Assertions.assertNotNull(tipo_animalAtualizado);
        Assertions.assertEquals(tipo_animalRetorno.getTipoAnimalId(), tipo_animalAtualizado.getTipoAnimalId());
        tipoAnimalRepo.delete(tipo_animalRetorno);
    }

    @Test
    public void deveRemover(){
        TipoAnimal tipo_animal = TipoAnimal.builder().nome("Tipo Animal Teste").build();
        TipoAnimal tipo_animalRetorno = tipoAnimalRepo.save(tipo_animal);
        tipoAnimalService.remover(tipo_animalRetorno);
        Optional<TipoAnimal> tipo_animalTemp = tipoAnimalRepo.findById(tipo_animalRetorno.getTipoAnimalId());
        Assertions.assertTrue(!tipo_animalTemp.isPresent());
    }


}
