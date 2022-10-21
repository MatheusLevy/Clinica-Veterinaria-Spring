package com.produtos.apirest.Serviços;

import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.repository.TipoConsultaRepo;
import com.produtos.apirest.service.TipoConsultaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class TipoConsultaServiceTeste {

    @Autowired
    public TipoConsultaService tipoConsultaService;

    @Autowired
    public TipoConsultaRepo tipoConsultaRepo;


    @Test
    public void deveSalvar(){
        //Cenário
        TipoConsulta tipo = TipoConsulta.builder()
                .nome("Tipo Consulta Teste")
                .build();

        //Ação
        TipoConsulta tipoRetorno = tipoConsultaService.salvar(tipo);

        //Verificação
        Assertions.assertNotNull(tipoRetorno);
        Assertions.assertNotNull(tipoRetorno.getTipoConsultaId());

        //Rollback
        tipoConsultaRepo.delete(tipoRetorno);
    }

    @Test
    public void deveAtualizar(){
        //Cenário
        TipoConsulta tipo = TipoConsulta.builder()
                .nome("Tipo Consulta Teste")
                .build();
        TipoConsulta tipoRetorno = tipoConsultaRepo.save(tipo);

        //Ação
        tipoRetorno.setNome("Tipo Consulta Atualizado");
        TipoConsulta tipoConsultaAtualizada = tipoConsultaService.atualizar(tipoRetorno);

        //Verificação
        Assertions.assertNotNull(tipoConsultaAtualizada);
        Assertions.assertEquals(tipoRetorno.getTipoConsultaId(), tipoConsultaAtualizada.getTipoConsultaId());

        //Rollback
        tipoConsultaRepo.delete(tipoRetorno);
    }

    @Test
    public void deveRemover(){
        //Cenário
        TipoConsulta tipo = TipoConsulta.builder()
                .nome("Tipo Consulta Teste")
                .build();
        TipoConsulta tipoRetorno = tipoConsultaRepo.save(tipo);

        //Ação
        tipoConsultaService.remover(tipoRetorno);

        //Verificação
        Optional<TipoConsulta> tipoTemp = tipoConsultaRepo.findById(tipoRetorno.getTipoConsultaId());
        Assertions.assertFalse(tipoTemp.isPresent());
    }

    @Test
    public void deveRemoverComFeedback(){
        //Cenário
        TipoConsulta tipo = TipoConsulta.builder()
                .nome("Tipo Consulta Teste")
                .build();
        TipoConsulta tipoRetorno = tipoConsultaRepo.save(tipo);

        //Ação
        TipoConsulta tipoFeedback = tipoConsultaService.removerComFeedback(tipoRetorno.getTipoConsultaId());

        //Verificação
        Assertions.assertNotNull(tipoFeedback);
    }

    @Test
    public void deveBuscarTipoConsultaPorId(){
        //Cenário
        TipoConsulta tipo = TipoConsulta.builder()
                .nome("Tipo Consulta Teste")
                .build();
        TipoConsulta tipoRetorno = tipoConsultaRepo.save(tipo);

        //Ação
        TipoConsulta tipoBuscado = tipoConsultaService.buscarPorId(tipoRetorno.getTipoConsultaId());

        //Verificação
        Assertions.assertNotNull(tipoBuscado);
        Assertions.assertEquals(tipoRetorno.getTipoConsultaId(), tipoBuscado.getTipoConsultaId());

        //Rollback
        tipoConsultaRepo.delete(tipoRetorno);
    }

    @Test
    public void deveBuscarComFiltro(){
        //Cenário
        TipoConsulta tipo = TipoConsulta.builder()
                .nome("Tipo Consulta Teste")
                .build();
        TipoConsulta tipoRetorno = tipoConsultaRepo.save(tipo);

        //Ação
        List<TipoConsulta> tipos = tipoConsultaService.buscar(tipoRetorno);

        //Verificação
        Assertions.assertFalse(tipos.isEmpty());

        //Rollback
        tipoConsultaRepo.delete(tipoRetorno);
    }

    @Test
    public void deveBuscarTodos(){
        //Cenário
        TipoConsulta tipo = TipoConsulta.builder()
                .nome("Tipo Consulta Teste")
                .build();
        TipoConsulta tipoRetorno = tipoConsultaRepo.save(tipo);

        //Ação
        List<TipoConsulta> tipos = tipoConsultaService.buscarTodos();

        //Verificação
        Assertions.assertNotNull(tipos);
        Assertions.assertFalse(tipos.isEmpty());

        //Rollback
        tipoConsultaRepo.delete(tipoRetorno);
    }
}
