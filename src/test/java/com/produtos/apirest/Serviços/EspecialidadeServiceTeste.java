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
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especidalide Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeService.salvar(especialidade);
        Assertions.assertNotNull(especialidadeRetorno);
        Assertions.assertNotNull(especialidadeRetorno.getEspecialidadeId());
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveAtualizar(){
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especidalide Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        especialidadeRetorno.setNome("Especialidade Atualizada");
        Especialidade especialidadeAtualizada = especialidadeService.atualizar(especialidadeRetorno);
        Assertions.assertNotNull(especialidadeAtualizada);
        Assertions.assertEquals(especialidadeRetorno.getEspecialidadeId(), especialidadeAtualizada.getEspecialidadeId());
        especialidadeRepo.delete(especialidadeAtualizada);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveRemover(){
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especidalide Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        especialidadeService.remover(especialidadeRetorno);
        Optional<Especialidade> especialidadeTemp = especialidadeRepo.findById(especialidadeRetorno.getEspecialidadeId());
        Assertions.assertTrue(!especialidadeTemp.isPresent());
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveRemovercomFeedback(){
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especidalide Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        Especialidade especialidadeRemovida = especialidadeService.removerFeedback(especialidadeRetorno);
        Assertions.assertNotNull(especialidadeRemovida);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveBuscarEspecialidadePorId(){
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especidalide Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        Especialidade especialidadeBuscada = especialidadeService.buscarEspecialidadePorId(especialidadeRetorno);
        Assertions.assertNotNull(especialidadeBuscada);
        Assertions.assertEquals(especialidadeRetorno.getEspecialidadeId(), especialidadeBuscada.getEspecialidadeId());
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveBuscarPorFiltro(){
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especidalide Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        List<Especialidade> especialidades = especialidadeService.buscar(especialidadeRetorno);
        Assertions.assertFalse(especialidades.isEmpty());
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }
}
