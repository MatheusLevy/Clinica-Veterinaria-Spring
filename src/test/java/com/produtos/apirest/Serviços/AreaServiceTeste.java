package com.produtos.apirest.Serviços;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.EspecialidadeRepo;
import com.produtos.apirest.service.AreaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AreaServiceTeste {
    @Autowired
    public AreaRepo areaRepo;

    @Autowired
    public AreaService areaService;

    @Autowired
    public EspecialidadeRepo especialidadeRepo;

    @Test
    public void deveSalvar(){
        Area area = Area.builder().nome("Area Teste").build();
        Area arearetorno = areaService.salvar(area);
        Assertions.assertNotNull(arearetorno);
        Assertions.assertNotNull(arearetorno.getAreaId());
        areaRepo.delete(arearetorno);
    }

    @Test
    public void deveAtualizar(){
        Area area = Area.builder().nome("Area Teste").build();
        Area arearetorno = areaRepo.save(area);
        arearetorno.setNome("Area Atualizada");
        Area areaAtualizada = areaService.atualizar(arearetorno);
        Assertions.assertNotNull(areaAtualizada);
        Assertions.assertEquals(arearetorno.getAreaId(), areaAtualizada.getAreaId());
        areaRepo.delete(areaAtualizada);
    }

    @Test
    public void deveRemover(){
        Area area = Area.builder().nome("Area Teste").build();
        Area arearetorno = areaRepo.save(area);
        areaService.remover(arearetorno);
        Optional<Area> areaTemp = areaRepo.findById(arearetorno.getAreaId());
        Assertions.assertTrue(!areaTemp.isPresent());
    }

    @Test
    public void deveRemovercomFeedback(){
        Area area = Area.builder().nome("Area Teste").build();
        Area arearetorno = areaRepo.save(area);
        Area areaRemovida = areaService.removerFeedback(arearetorno);
        Assertions.assertNotNull(areaRemovida);
    }

    @Test
    public void deveBuscarAreaPorId(){
        Area area = Area.builder().nome("Area Teste").build();
        Area arearetorno = areaRepo.save(area);
        Area areaBuscada = areaService.buscarAreaPorId(arearetorno);
        Assertions.assertNotNull(areaBuscada);
        Assertions.assertEquals(arearetorno.getAreaId(), areaBuscada.getAreaId());
        Assertions.assertEquals(arearetorno.getNome(), areaBuscada.getNome());
        areaRepo.delete(areaBuscada);
    }

    @Test
    public void deveBuscar(){
        Area area = Area.builder().nome("Area Teste").build();
        Area arearetorno = areaRepo.save(area);
        List<Area> areas = areaService.buscar(arearetorno);
        Assertions.assertFalse(areas.isEmpty());
        areaRepo.delete(arearetorno);
    }

    @Test
    public void deveBuscarTodasEspecialidades(){
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade1 = Especialidade.builder().nome("Especialidade 1T").area(areaRetorno).build();
        Especialidade especialidade2 = Especialidade.builder().nome("Especialidade 2T").area(areaRetorno).build();
        Especialidade especialidade1retorno =  especialidadeRepo.save(especialidade1);
        Especialidade especialidade2retorno =  especialidadeRepo.save(especialidade2);
        List<Especialidade> especialidades = areaService.buscarTodasEspecialidades(areaRetorno);
        Assertions.assertFalse(especialidades.isEmpty());
        especialidadeRepo.delete(especialidade1retorno);
        especialidadeRepo.delete(especialidade2retorno);
        areaRepo.delete(areaRetorno);
    }
}

