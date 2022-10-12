package com.produtos.apirest.Model;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.EspecialidadeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Optional;

@SpringBootTest
public class EspecialidadeTeste {
    @Autowired
    public EspecialidadeRepo repoEspecialidade;

    @Autowired
    public AreaRepo repoArea;

    @Test
    public void deveCriarEspecialidade(){
        //Cenário
        Area novaArea = Area.builder()
                .nome("Cirurgia Geral")
                .build();
        Area retornoArea = repoArea.save(novaArea);

        Especialidade novaEspecialidade = Especialidade.builder()
                .nome("Ortopedia")
                .area(retornoArea)
                .build();

        //Ação
        Especialidade retornoEspecialidade = repoEspecialidade.save(novaEspecialidade);

        //Verificação
        Assertions.assertNotNull(retornoEspecialidade);
        Assertions.assertEquals(retornoEspecialidade.getNome(), novaEspecialidade.getNome());

        //RollBack
        repoEspecialidade.delete(retornoEspecialidade);
        repoArea.delete(retornoArea);
    }

    @Test
    public void deveRemoverEspecialidade() {
        //Cenário
        Area novaArea = Area.builder()
                .nome("Cirurgia Geral")
                .build();
        Area retornoArea = repoArea.save(novaArea);

        Especialidade novaEspecialidade = Especialidade.builder()
                .nome("Ortopedia")
                .area(retornoArea)
                .build();
        Especialidade retornoEspecialidade = repoEspecialidade.save(novaEspecialidade);

        //Ação
        repoEspecialidade.delete(retornoEspecialidade);

        //Verificação
        Optional<Especialidade> temp = repoEspecialidade.findById(retornoEspecialidade.getEspecialidadeId());
        Assertions.assertFalse(temp.isPresent());

        //RollBack
        repoArea.delete(retornoArea);
    }


    @Test
    public void deveBuscar(){
        //Cenário
        Area novaArea = Area.builder()
                .nome("Cirurgia Geral")
                .build();
        Area retornoArea = repoArea.save(novaArea);

        Especialidade novaEspecialidade = Especialidade.builder()
                .nome("Ortopedia")
                .area(retornoArea)
                .build();
        Especialidade retornoEspecialidade = repoEspecialidade.save(novaEspecialidade);

        //Ação
        Optional<Especialidade> temp = repoEspecialidade.findById(retornoEspecialidade.getEspecialidadeId());

        //Verificação
        Assertions.assertTrue(temp.isPresent());

        //Rollback
        repoEspecialidade.delete(retornoEspecialidade);
        repoArea.delete(retornoArea);
    }
    @Test
    public void deveAtualizar(){
        //Cenário
        Area novaArea = Area.builder()
                .nome("Cirurgia Geral")
                .build();
        Area retornoArea = repoArea.save(novaArea);

        Especialidade novaEspecialidade = Especialidade.builder()
                .nome("Ortopedia")
                .area(retornoArea)
                .build();
        Especialidade retornoEspecialidade = repoEspecialidade.save(novaEspecialidade);

        //Ação
        retornoEspecialidade.setNome("Nome Atualizado");
        Especialidade atualizado = repoEspecialidade.save(retornoEspecialidade);

        //Verificação
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals(retornoEspecialidade.getEspecialidadeId(), atualizado.getEspecialidadeId());
        Assertions.assertEquals(atualizado.getNome(),  "Nome Atualizado");

        //Rollback
        repoEspecialidade.delete(atualizado);
        repoArea.delete(retornoArea);
    }

    @Test
    public void deveAtualizarArea(){
        //Cenário
        Area novaArea = Area.builder()
                .nome("Cirurgia Geral")
                .build();
        Area retornoArea = repoArea.save(novaArea);

        Area outraArea = Area.builder()
                .nome("Outra Area")
                .build();
        Area retornoOutraArea = repoArea.save(outraArea);

        Especialidade novaEspecialidade = Especialidade.builder()
                .nome("Ortopedia")
                .area(retornoArea)
                .build();
        Especialidade retornoEspecialidade = repoEspecialidade.save(novaEspecialidade);

        //Ação
        retornoEspecialidade.setArea(retornoOutraArea);
        Especialidade atualizado = repoEspecialidade.save(retornoEspecialidade);

        //Verificação
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals(atualizado.getEspecialidadeId(), retornoEspecialidade.getEspecialidadeId());
        Assertions.assertEquals(atualizado.getArea().getAreaId(), retornoOutraArea.getAreaId());

        //Rollback
        repoEspecialidade.delete(atualizado);
        repoArea.delete(retornoArea);
        repoArea.delete(retornoOutraArea);
    }
}
