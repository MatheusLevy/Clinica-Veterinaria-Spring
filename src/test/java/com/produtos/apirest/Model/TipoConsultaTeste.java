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

    protected static TipoConsulta getTipoConsultaInstance(boolean temId){
        TipoConsulta tipoConsulta = TipoConsulta.builder()
                .nome("nome")
                .build();
        if (temId)
            tipoConsulta.setTipoConsultaId(Long.valueOf(1));
        return tipoConsulta;
    }

    @Test
    public void deveSalvarModel(){
        TipoConsulta tipoConsultaSalva = tipoConsultaRepo.save(getTipoConsultaInstance(false));
        Assertions.assertNotNull(tipoConsultaSalva);
        Assertions.assertEquals(getTipoConsultaInstance(false).getNome(), tipoConsultaSalva.getNome());
        tipoConsultaRepo.delete(tipoConsultaSalva);
    }

    @Test
    public void deveAtualizarModel(){
        TipoConsulta tipoConsultaSalva = tipoConsultaRepo.save(getTipoConsultaInstance(false));
        tipoConsultaSalva.setNome("Nome Novo");
        TipoConsulta tipoConsultaAtualizada =  tipoConsultaRepo.save(tipoConsultaSalva);
        Assertions.assertNotNull(tipoConsultaAtualizada);
        Assertions.assertEquals(tipoConsultaAtualizada.getTipoConsultaId(), tipoConsultaSalva.getTipoConsultaId());
        Assertions.assertEquals(tipoConsultaAtualizada.getNome(), "Nome Novo");
        tipoConsultaRepo.delete(tipoConsultaAtualizada);
    }

    @Test
    public void deveRemoverModel(){
        TipoConsulta tipoConsultaSalva = tipoConsultaRepo.save(getTipoConsultaInstance(false));
        Long id = tipoConsultaSalva.getTipoConsultaId();
        tipoConsultaRepo.deleteById(id);
        Assertions.assertFalse(tipoConsultaRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscarModel(){
        TipoConsulta tipoConsultaSalva = tipoConsultaRepo.save(getTipoConsultaInstance(false));
        Long id = tipoConsultaSalva.getTipoConsultaId();
        Assertions.assertTrue(tipoConsultaRepo.findById(id).isPresent());
        tipoConsultaRepo.delete(tipoConsultaSalva);
    }
}