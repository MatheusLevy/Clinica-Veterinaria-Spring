package com.produtos.apirest.Model;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.EspecialidadeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EspecialidadeTeste {
    @Autowired
    public EspecialidadeRepo repoEspecialidade;

    @Autowired
    public AreaRepo repoArea;

    @Test
    public void deveCriarEspecialidade(){
        //Cenário
        Area novaArea = Area.builder().nome("Cirurgia Geral").build();
        Area retornoArea = repoArea.save(novaArea);
        Especialidade novaEspecialidade = Especialidade.builder().nome("Ortopedia").area(retornoArea).build();

        //Ação
        Especialidade retornoEspecialidade = repoEspecialidade.save(novaEspecialidade);

        //Verificação
        Assertions.assertNotNull(retornoEspecialidade);

        //RollBack
        repoEspecialidade.delete(retornoEspecialidade);
        repoArea.delete(retornoArea);
    }
}
