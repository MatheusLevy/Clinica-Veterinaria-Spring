package com.produtos.apirest.Serviços;

import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.repository.TipoAnimalRepo;
import com.produtos.apirest.service.TipoAnimalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class TipoAnimalServiceTeste {

    @Autowired
    public TipoAnimalService tipoAnimalService;

    @Autowired
    public TipoAnimalRepo tipoAnimalRepo;

    @Test
    public void deveSalvar(){
        //Cenário
        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        //Ação
        TipoAnimal tipoAnimalRetorno = tipoAnimalService.salvar(tipoAnimal);

        //Verificação
        Assertions.assertNotNull(tipoAnimalRetorno);
        Assertions.assertNotNull(tipoAnimalRetorno.getTipoAnimalId());

        //Rollback
        tipoAnimalRepo.delete(tipoAnimalRetorno);
    }

    @Test
    public void deveAtualizar(){
        //Cenário
        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        TipoAnimal tipoAnimalRetorno = tipoAnimalRepo.save(tipoAnimal);

        //Ação
        tipoAnimalRetorno.setNome("Tipo Animal Atualizado");
        TipoAnimal tipoAnimalAtualizado = tipoAnimalService.atualizar(tipoAnimalRetorno);

        //Verificação
        Assertions.assertNotNull(tipoAnimalAtualizado);
        Assertions.assertEquals(tipoAnimalRetorno.getTipoAnimalId(), tipoAnimalAtualizado.getTipoAnimalId());

        //Rollback
        tipoAnimalRepo.delete(tipoAnimalRetorno);
    }

    @Test
    public void deveRemover(){
        //Cenário
        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        TipoAnimal tipoAnimalRetorno = tipoAnimalRepo.save(tipoAnimal);

        //Ação
        tipoAnimalService.remover(tipoAnimalRetorno);

        //Verificação
        Optional<TipoAnimal> tipoAnimalTemp = tipoAnimalRepo.findById(tipoAnimalRetorno.getTipoAnimalId());
        Assertions.assertTrue(!tipoAnimalTemp.isPresent());
    }

    @Test
    public void deveRemoverComFeedback(){
        //Cenário
        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        TipoAnimal tipoAnimalRetorno = tipoAnimalRepo.save(tipoAnimal);

        //Ação
        TipoAnimal tipoRemovido = tipoAnimalService.removerFeedback(tipoAnimalRetorno.getTipoAnimalId());

        //Verificação
        Assertions.assertNotNull(tipoRemovido);
        Assertions.assertEquals(tipoAnimalRetorno.getTipoAnimalId(), tipoRemovido.getTipoAnimalId());
    }

    @Test
    public void deveRemoverPorId(){
        //Cenário
        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        TipoAnimal tipoAnimalRetorno = tipoAnimalRepo.save(tipoAnimal);

        //Ação
        tipoAnimalService.removerPorId(tipoAnimalRetorno.getTipoAnimalId());

        //Verificação
        Optional<TipoAnimal> tipoAnimalTemp = tipoAnimalRepo.findById(tipoAnimalRetorno.getTipoAnimalId());
        Assertions.assertTrue(!tipoAnimalTemp.isPresent());
    }

    @Test
    public void deveBuscarPorId(){
        //Cenário
        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        TipoAnimal tipoAnimalRetorno = tipoAnimalRepo.save(tipoAnimal);

        //Ação
        TipoAnimal TipoAnimalBuscado = tipoAnimalService.buscarTipoAnimalPorId(tipoAnimalRetorno.getTipoAnimalId());

        //Verificação
        Assertions.assertNotNull(TipoAnimalBuscado);
        Assertions.assertEquals(TipoAnimalBuscado.getTipoAnimalId(), tipoAnimalRetorno.getTipoAnimalId());

        //Rollback
        tipoAnimalRepo.delete(tipoAnimalRetorno);
    }

    @Test
    public void deveBuscarPorFiltro(){
        //Cenário
        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        TipoAnimal tipoAnimalRetorno = tipoAnimalRepo.save(tipoAnimal);

        //Ação
        List<TipoAnimal> tipos = tipoAnimalService.buscar(tipoAnimalRetorno);

        //Verificação
        Assertions.assertNotNull(tipos);
        Assertions.assertFalse(tipos.isEmpty());

        //Rollback
        tipoAnimalRepo.delete(tipoAnimalRetorno);
    }

    @Test
    public void deveBuscarTodos(){
        //Cenário
        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        TipoAnimal tipoAnimalRetorno = tipoAnimalRepo.save(tipoAnimal);

        //Ação
        List<TipoAnimal> tipos = tipoAnimalService.buscarTodos();

        //Verificação
        Assertions.assertNotNull(tipos);
        Assertions.assertFalse(tipos.isEmpty());

        //Rollback
        tipoAnimalRepo.delete(tipoAnimalRetorno);
    }

}
