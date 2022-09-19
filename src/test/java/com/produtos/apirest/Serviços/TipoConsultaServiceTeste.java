package com.produtos.apirest.Serviços;

import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.repository.TipoConsultaRepo;
import com.produtos.apirest.service.Tipo_ConsultaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class TipoConsultaServiceTeste {

    @Autowired
    public Tipo_ConsultaService tipo_consultaService;

    @Autowired
    public TipoConsultaRepo tipo_consultaRepo;


    @Test
    public void deveSalvar(){
        TipoConsulta tipo = TipoConsulta.builder().nome("Tipo Consulta Teste").build();
        TipoConsulta tipoRetorno = tipo_consultaService.salvar(tipo);
        Assertions.assertNotNull(tipoRetorno);
        Assertions.assertNotNull(tipoRetorno.getTipoConsultaId());
        tipo_consultaRepo.delete(tipoRetorno);
    }

    @Test
    public void deveAtualizar(){
        TipoConsulta tipo = TipoConsulta.builder().nome("Tipo Consulta Teste").build();
        TipoConsulta tipoRetorno = tipo_consultaRepo.save(tipo);
        tipoRetorno.setNome("Tipo Consulta Atualizado");
        TipoConsulta tipo_consultaAtualizada = tipo_consultaService.atualizar(tipoRetorno);
        Assertions.assertNotNull(tipo_consultaAtualizada);
        Assertions.assertEquals(tipoRetorno.getTipoConsultaId(), tipo_consultaAtualizada.getTipoConsultaId());
        tipo_consultaRepo.delete(tipoRetorno);
    }

    @Test
    public void deveRemover(){
        TipoConsulta tipo = TipoConsulta.builder().nome("Tipo Consulta Teste").build();
        TipoConsulta tipoRetorno = tipo_consultaRepo.save(tipo);
        tipo_consultaService.remover(tipoRetorno);
        Optional<TipoConsulta> tipoTemp = tipo_consultaRepo.findById(tipoRetorno.getTipoConsultaId());
        Assertions.assertFalse(tipoTemp.isPresent());
    }

    @Test
    public void deveRemoverComFeedback(){
        TipoConsulta tipo = TipoConsulta.builder().nome("Tipo Consulta Teste").build();
        TipoConsulta tipoRetorno = tipo_consultaRepo.save(tipo);
        TipoConsulta tipoFeedback = tipo_consultaService.removerFeedback(tipoRetorno);
        Assertions.assertNotNull(tipoFeedback);
    }

    @Test
    public void deveBuscarTipoConsutlaPorId(){
        TipoConsulta tipo = TipoConsulta.builder().nome("Tipo Consulta Teste").build();
        TipoConsulta tipoRetorno = tipo_consultaRepo.save(tipo);
        TipoConsulta tipoBuscado = tipo_consultaService.buscarTipo_consultaPorId(tipoRetorno);
        Assertions.assertNotNull(tipoBuscado);
        Assertions.assertEquals(tipoRetorno.getTipoConsultaId(), tipoBuscado.getTipoConsultaId());
        tipo_consultaRepo.delete(tipoRetorno);
    }

    @Test
    public void deveBuscarComFiltro(){
        TipoConsulta tipo = TipoConsulta.builder().nome("Tipo Consulta Teste").build();
        TipoConsulta tipoRetorno = tipo_consultaRepo.save(tipo);
        List<TipoConsulta> tipos = tipo_consultaService.buscar(tipoRetorno);
        Assertions.assertFalse(tipos.isEmpty());
        tipo_consultaRepo.delete(tipoRetorno);
    }
}
