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
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();

        //Ação
        Area arearetorno = areaService.salvar(area);

        //Verificação
        Assertions.assertNotNull(arearetorno);
        Assertions.assertNotNull(arearetorno.getAreaId());

        //Rollback
        areaRepo.delete(arearetorno);
    }

    @Test
    public void deveAtualizar(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area arearetorno = areaRepo.save(area);

        //Ação
        arearetorno.setNome("Area Atualizada");
        Area areaAtualizada = areaService.atualizar(arearetorno);

        //Verificação
        Assertions.assertNotNull(areaAtualizada);
        Assertions.assertEquals(arearetorno.getAreaId(), areaAtualizada.getAreaId());

        //Rollback
        areaRepo.delete(areaAtualizada);
    }

    @Test
    public void deveRemover(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area arearetorno = areaRepo.save(area);

        //Ação
        areaService.remover(arearetorno.getAreaId());

        //Verificação
        Optional<Area> areaTemp = areaRepo.findById(arearetorno.getAreaId());
        Assertions.assertFalse(areaTemp.isPresent());
    }

    @Test
    public void deveRemovercomFeedback(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area arearetorno = areaRepo.save(area);

        //Ação
        Area areaRemovida = areaService.removerFeedback(arearetorno.getAreaId());

        //Verificação
        Assertions.assertNotNull(areaRemovida);
        Assertions.assertEquals(areaRemovida.getAreaId(), arearetorno.getAreaId());
    }


    @Test
    public void deveBuscarAreaPorId(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area arearetorno = areaRepo.save(area);

        //Ação
        Area areaBuscada = areaService.buscarAreaPorId(arearetorno.getAreaId());

        //Verificação
        Assertions.assertNotNull(areaBuscada);
        Assertions.assertEquals(arearetorno.getAreaId(), areaBuscada.getAreaId());
        Assertions.assertEquals(arearetorno.getNome(), areaBuscada.getNome());

        //Rollback
        areaRepo.delete(areaBuscada);
    }

    @Test
    public void deveBuscar(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area arearetorno = areaRepo.save(area);

        //Ação
        Area filtro = Area.builder()
                .areaId(arearetorno.getAreaId())
                .nome(arearetorno.getNome())
                .build();
        List<Area> areas = areaService.buscar(filtro);

        //Verificação
        Assertions.assertFalse(areas.isEmpty());

        //Rollback
        areaRepo.delete(arearetorno);
    }

    @Test
    public void deveBuscarTodasEspecialidades(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade1 = Especialidade.builder()
                .nome("Especialidade 1T")
                .area(areaRetorno)
                .build();
        Especialidade especialidade2 = Especialidade.builder()
                .nome("Especialidade 2T")
                .area(areaRetorno)
                .build();
        Especialidade especialidade1retorno =  especialidadeRepo.save(especialidade1);
        Especialidade especialidade2retorno =  especialidadeRepo.save(especialidade2);

        //Ação
        List<Especialidade> especialidades = areaService.buscarTodasEspecialidades(areaRetorno);

        //Verificação
        Assertions.assertFalse(especialidades.isEmpty());
        especialidadeRepo.delete(especialidade1retorno);
        especialidadeRepo.delete(especialidade2retorno);
        areaRepo.delete(areaRetorno);
    }
}

