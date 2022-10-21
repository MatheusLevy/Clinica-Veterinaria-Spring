package com.produtos.apirest.Model;

import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.repository.TipoConsultaRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TipoConsultaTeste {
    @Autowired
    public TipoConsultaRepo tipoConsultaRepo;

    protected static TipoConsulta generateTipoConsulta(){
       return TipoConsulta.builder()
                .nome("nome")
                .build();
    }

    private void rollback(TipoConsulta tipoConsulta){
        tipoConsultaRepo.delete(tipoConsulta);
    }

    protected static void rollbackTipoConsulta(TipoConsulta tipoConsulta, TipoConsultaRepo tipoConsultaRepo){
        tipoConsultaRepo.delete(tipoConsulta);
    }

    @Test
    public void deveSalvarModel(){
        TipoConsulta tipoConsultaSalva = tipoConsultaRepo.save(generateTipoConsulta());
        Assertions.assertNotNull(tipoConsultaSalva);
        Assertions.assertEquals(generateTipoConsulta().getNome(), tipoConsultaSalva.getNome());
        rollback(tipoConsultaSalva);
    }

    @Test
    public void deveAtualizarModel(){
        TipoConsulta tipoConsultaSalva = tipoConsultaRepo.save(generateTipoConsulta());
        tipoConsultaSalva.setNome("Nome Novo");
        TipoConsulta tipoConsultaAtualizada =  tipoConsultaRepo.save(tipoConsultaSalva);
        Assertions.assertNotNull(tipoConsultaAtualizada);
        Assertions.assertEquals(tipoConsultaAtualizada.getTipoConsultaId(), tipoConsultaSalva.getTipoConsultaId());
        Assertions.assertEquals(tipoConsultaAtualizada.getNome(), "Nome Novo");
        rollback(tipoConsultaAtualizada);
    }

    @Test
    public void deveRemoverModel(){
        TipoConsulta tipoConsultaSalva = tipoConsultaRepo.save(generateTipoConsulta());
        Long id = tipoConsultaSalva.getTipoConsultaId();
        tipoConsultaRepo.deleteById(id);
        Assertions.assertFalse(tipoConsultaRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscarModel(){
        TipoConsulta tipoConsultaSalva = tipoConsultaRepo.save(generateTipoConsulta());
        Long id = tipoConsultaSalva.getTipoConsultaId();
        Assertions.assertTrue(tipoConsultaRepo.findById(id).isPresent());
        rollback(tipoConsultaSalva);
    }
}