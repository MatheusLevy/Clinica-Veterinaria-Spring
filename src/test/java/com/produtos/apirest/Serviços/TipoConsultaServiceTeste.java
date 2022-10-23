package com.produtos.apirest.Serviços;

import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.repository.TipoConsultaRepo;
import com.produtos.apirest.service.TipoConsultaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TipoConsultaServiceTeste {

    @Autowired
    public TipoConsultaService tipoConsultaService;

    @Autowired
    public TipoConsultaRepo tipoConsultaRepo;

    protected static TipoConsulta generateTipoConsulta(){
        return TipoConsulta.builder()
                .nome("Teste")
                .build();
    }

    private void rollback(TipoConsulta tipoConsulta){
        tipoConsultaRepo.delete(tipoConsulta);
    }

    protected static void rollbackTipoConsulta(TipoConsulta tipoConsulta, TipoConsultaRepo tipoConsultaRepo){
        tipoConsultaRepo.delete(tipoConsulta);
    }

    @Test
    public void deveSalvar(){
        TipoConsulta tipoConsultaSalva = tipoConsultaService.salvar(generateTipoConsulta());
        Assertions.assertNotNull(tipoConsultaSalva);
        Assertions.assertNotNull(tipoConsultaSalva.getTipoConsultaId());
        rollback(tipoConsultaSalva);
    }

    @Test
    public void deveAtualizar(){
        TipoConsulta tipoConsultaSalva = tipoConsultaRepo.save(generateTipoConsulta());
        tipoConsultaSalva.setNome("Tipo Consulta Atualizado");
        TipoConsulta tipoConsultaAtualizada = tipoConsultaRepo.save(tipoConsultaSalva);
        Assertions.assertNotNull(tipoConsultaAtualizada);
        Assertions.assertEquals(tipoConsultaSalva.getTipoConsultaId(), tipoConsultaAtualizada.getTipoConsultaId());
        rollback(tipoConsultaAtualizada);
    }

    @Test
    public void deveRemover(){
        TipoConsulta tipoConsultaSalvo = tipoConsultaRepo.save(generateTipoConsulta());
        Long id = tipoConsultaSalvo.getTipoConsultaId();
        tipoConsultaService.removerPorId(id);
        Assertions.assertFalse(tipoConsultaRepo.findById(id).isPresent());
    }

    @Test
    public void deveRemoverComFeedback(){
        TipoConsulta tipoConsultaSalvo = tipoConsultaRepo.save(generateTipoConsulta());
        TipoConsulta tipoConsultaFeedback = tipoConsultaService.removerComFeedback(tipoConsultaSalvo.getTipoConsultaId());
        Assertions.assertNotNull(tipoConsultaFeedback);
    }

    @Test
    public void deveBuscarPorId(){
        TipoConsulta tipoConsultaSalvo = tipoConsultaRepo.save(generateTipoConsulta());
        TipoConsulta tipoConsultaEncontrado = tipoConsultaService.buscarPorId(tipoConsultaSalvo.getTipoConsultaId());
        Assertions.assertNotNull(tipoConsultaEncontrado);
        Assertions.assertEquals(tipoConsultaSalvo.getTipoConsultaId(), tipoConsultaEncontrado.getTipoConsultaId());
        rollback(tipoConsultaSalvo);
    }

    @Test
    public void deveBuscarComFiltro(){
        TipoConsulta tipoConsultaSalvo = tipoConsultaRepo.save(generateTipoConsulta());
        Assertions.assertFalse(tipoConsultaService.buscar(tipoConsultaSalvo).isEmpty());
        rollback(tipoConsultaSalvo);
    }

    @Test
    public void deveBuscarTodos(){
        TipoConsulta tipoConsultaSalvo = tipoConsultaRepo.save(generateTipoConsulta());
        List<TipoConsulta> tiposList = tipoConsultaService.buscarTodos();
        Assertions.assertNotNull(tiposList);
        Assertions.assertFalse(tiposList.isEmpty());
        rollback(tipoConsultaSalvo);
    }
}