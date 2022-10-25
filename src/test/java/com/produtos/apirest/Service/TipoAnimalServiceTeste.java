package com.produtos.apirest.Service;

import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.repository.TipoAnimalRepo;
import com.produtos.apirest.service.TipoAnimalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TipoAnimalServiceTeste {

    @Autowired
    public TipoAnimalService tipoAnimalService;

    @Autowired
    public TipoAnimalRepo tipoAnimalRepo;

    protected static TipoAnimal generateTipoAnimal(){
        return TipoAnimal.builder()
                .nome("teste")
                .build();
    }

    private void rollback(TipoAnimal tipoAnimal){
        tipoAnimalRepo.delete(tipoAnimal);
    }

    protected void rollbackTipoAnimal(TipoAnimal tipoAnimal, TipoAnimalRepo tipoAnimalRepo){
        tipoAnimalRepo.delete(tipoAnimal);
    }

    @Test
    public void deveSalvar(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalService.salvar(generateTipoAnimal());
        Assertions.assertNotNull(tipoAnimalSalvo);
        rollback(tipoAnimalSalvo);
    }

    @Test
    public void deveAtualizar(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(generateTipoAnimal());
        tipoAnimalSalvo.setNome("Tipo Animal Atualizado");
        TipoAnimal tipoAnimalAtualizado = tipoAnimalService.atualizar(tipoAnimalSalvo);
        Assertions.assertNotNull(tipoAnimalAtualizado);
        Assertions.assertEquals(tipoAnimalSalvo.getTipoAnimalId(), tipoAnimalAtualizado.getTipoAnimalId());
        rollback(tipoAnimalAtualizado);
    }

    @Test
    public void deveRemover(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(generateTipoAnimal());
        tipoAnimalService.remover(tipoAnimalSalvo);
        Assertions.assertFalse(tipoAnimalRepo.findById(tipoAnimalSalvo.getTipoAnimalId()).isPresent());
    }

    @Test
    public void deveRemoverComFeedback(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(generateTipoAnimal());
        TipoAnimal tipoAnimalFeedback = tipoAnimalService.removerComFeedback(tipoAnimalSalvo.getTipoAnimalId());
        Assertions.assertNotNull(tipoAnimalFeedback);
        Assertions.assertEquals(tipoAnimalSalvo.getTipoAnimalId(), tipoAnimalFeedback.getTipoAnimalId());
    }

    @Test
    public void deveRemoverPorId(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(generateTipoAnimal());
        Long id = tipoAnimalSalvo.getTipoAnimalId();
        tipoAnimalService.removerPorId(id);;
        Assertions.assertFalse(tipoAnimalRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscarPorId(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(generateTipoAnimal());
        TipoAnimal TipoAnimalEncontrado = tipoAnimalService.buscarPorId(tipoAnimalSalvo.getTipoAnimalId());
        Assertions.assertNotNull(TipoAnimalEncontrado);
        Assertions.assertEquals(TipoAnimalEncontrado.getTipoAnimalId(), tipoAnimalSalvo.getTipoAnimalId());
        rollback(tipoAnimalSalvo);
    }

    @Test
    public void deveBuscarPorFiltro(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(generateTipoAnimal());
        List<TipoAnimal> tiposEncontradosList = tipoAnimalService.buscar(tipoAnimalSalvo);
        Assertions.assertNotNull(tiposEncontradosList);
        Assertions.assertFalse(tiposEncontradosList.isEmpty());
        rollback(tipoAnimalSalvo);
    }

    @Test
    public void deveBuscarTodos(){
        TipoAnimal tipoAnimalSalvo = tipoAnimalRepo.save(generateTipoAnimal());
        List<TipoAnimal> tiposList = tipoAnimalService.buscarTodos();
        Assertions.assertNotNull(tiposList);
        Assertions.assertFalse(tiposList.isEmpty());
        rollback(tipoAnimalSalvo);
    }
}