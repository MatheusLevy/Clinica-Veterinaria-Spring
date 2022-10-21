package com.produtos.apirest.Serviços;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.EspecialidadeRepo;
import com.produtos.apirest.service.EspecialidadeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EspecialidadeServiceTeste {

    @Autowired
    public EspecialidadeService especialidadeService;

    @Autowired
    public EspecialidadeRepo especialidadeRepo;

    @Autowired
    public AreaRepo areaRepo;

    @Test
    public void deveSalvar(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especidalide Teste")
                .area(areaRetorno)
                .build();

        //Ação
        Especialidade especialidadeRetorno = especialidadeService.salvar(especialidade);

        //Verificação
        Assertions.assertNotNull(especialidadeRetorno);
        Assertions.assertNotNull(especialidadeRetorno.getEspecialidadeId());

        //Rollback
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveAtualizar(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especidalide Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        //Ação
        especialidadeRetorno.setNome("Especialidade Atualizada");
        Especialidade especialidadeAtualizada = especialidadeService.atualizar(especialidadeRetorno);

        //Verificação
        Assertions.assertNotNull(especialidadeAtualizada);
        Assertions.assertEquals(especialidadeRetorno.getEspecialidadeId(), especialidadeAtualizada.getEspecialidadeId());

        //Rollback
        especialidadeRepo.delete(especialidadeAtualizada);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveAtualizarArea(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Area novaArea = Area.builder()
                .nome("Area Nova")
                .build();
        Area novaAreaRetorno = areaRepo.save(novaArea);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especidalide Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        //Ação
        Especialidade especialidadeAtualizada = especialidadeService.atualizarArea(especialidadeRetorno, novaAreaRetorno);

        //Verificação
        Assertions.assertNotNull(especialidadeAtualizada);
        Assertions.assertEquals(especialidadeAtualizada.getArea().getAreaId(), novaAreaRetorno.getAreaId());
        Assertions.assertEquals(especialidadeAtualizada.getArea().getNome(), novaAreaRetorno.getNome());

        //Rollback
        especialidadeRepo.delete(especialidadeAtualizada);
        areaRepo.delete(areaRetorno);
        areaRepo.delete(novaAreaRetorno);
    }
    @Test
    public void deveRemover(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especidalide Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        //Ação
        especialidadeService.remover(especialidadeRetorno);

        //Verificação
        Optional<Especialidade> especialidadeTemp = especialidadeRepo.findById(especialidadeRetorno.getEspecialidadeId());
        Assertions.assertTrue(!especialidadeTemp.isPresent());

        //Rollback
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveRemovercomFeedback(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especidalide Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        //Ação
        Especialidade especialidadeRemovida = especialidadeService.removerFeedback(especialidadeRetorno.getEspecialidadeId());

        //Verificação
        Assertions.assertNotNull(especialidadeRemovida);
        Assertions.assertNotNull(especialidadeRemovida.getEspecialidadeId());
        Assertions.assertEquals(especialidadeRetorno.getEspecialidadeId(), especialidadeRemovida.getEspecialidadeId());

        //Rollback
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveRemoverPorId(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especidalide Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        //Ação
        especialidadeService.removerPorId(especialidadeRetorno.getEspecialidadeId());

        //Verificação
        Optional<Especialidade> especialidadeTemp = especialidadeRepo.findById(especialidadeRetorno.getEspecialidadeId());
        Assertions.assertTrue(!especialidadeTemp.isPresent());

        //Rollback
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveBuscarEspecialidadePorId(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especidalide Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        //Ação
        Especialidade especialidadeBuscada = especialidadeService.buscarPorId(especialidadeRetorno.getEspecialidadeId());

        //Verificação
        Assertions.assertNotNull(especialidadeBuscada);
        Assertions.assertEquals(especialidadeRetorno.getEspecialidadeId(), especialidadeBuscada.getEspecialidadeId());

        //Rollback
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveBuscarPorFiltro(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especidalide Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        //Ação
        List<Especialidade> especialidades = especialidadeService.buscar(especialidadeRetorno);

        //Verificação
        Assertions.assertFalse(especialidades.isEmpty());

        //Rollback
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveBuscarTodos(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especidalide Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        //Ação
        List<Especialidade> especialidades = especialidadeService.buscarTodos();

        //Verificação
        Assertions.assertNotNull(especialidades);
        Assertions.assertFalse(especialidades.isEmpty());

        //Rollback
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

}
