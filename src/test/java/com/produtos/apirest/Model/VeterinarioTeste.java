package com.produtos.apirest.Model;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.models.Veterinario;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.EspecialidadeRepo;
import com.produtos.apirest.repository.VeterinarioRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class VeterinarioTeste {
    @Autowired
    public VeterinarioRepo repo;

    @Autowired
    public AreaRepo areaRepo;

    @Autowired
    public EspecialidadeRepo especialidadeRepo;

    @Test
    public void deveCriarVeterinario(){
        //Cenário
        Area novaArea = Area.builder()
                .nome("Cirurgia Geral")
                .build();
        Area retornoArea = areaRepo.save(novaArea);

        Especialidade novaEspecialidade = Especialidade.builder()
                .nome("Ortopedia")
                .area(retornoArea).
                build();
        Especialidade retornoEspecialidade = especialidadeRepo.save(novaEspecialidade);

        Veterinario novo = Veterinario.builder()
                .nome("Marcos")
                .cpf("123")
                .especialidade(retornoEspecialidade)
                .telefone("123")
                .build();

        //Ação
        Veterinario retorno = repo.save(novo);

        //Verificação
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(novo.getNome(), retorno.getNome());

        //Rollback
        repo.delete(retorno);
        especialidadeRepo.delete(retornoEspecialidade);
        areaRepo.delete(retornoArea);
    }

    @Test
    public void deveRemoverVeterinario(){
        //Cenário
        Area novaArea = Area.builder()
                .nome("Cirurgia Geral")
                .build();
        Area retornoArea = areaRepo.save(novaArea);

        Especialidade novaEspecialidade = Especialidade.builder()
                .nome("Ortopedia")
                .area(retornoArea)
                .build();
        Especialidade retornoEspecialidade = especialidadeRepo.save(novaEspecialidade);

        Veterinario novo = Veterinario.builder()
                .nome("Marcos")
                .cpf("123")
                .especialidade(retornoEspecialidade)
                .telefone("123")
                .build();
        Veterinario retorno = repo.save(novo);

        //Ação
        repo.delete(retorno);

        //Verificação
        Optional<Veterinario> temp = repo.findById(retorno.getVeterinarioId());
        Assertions.assertFalse(temp.isPresent());

        //Rollback
        especialidadeRepo.delete(retornoEspecialidade);
        areaRepo.delete(retornoArea);
    }

    @Test
    public void deveBuscarVeterinario(){
        //Cenário
        Area novaArea = Area.builder()
                .nome("Cirurgia Geral")
                .build();
        Area retornoArea = areaRepo.save(novaArea);

        Especialidade novaEspecialidade = Especialidade.builder()
                .nome("Ortopedia")
                .area(retornoArea)
                .build();
        Especialidade retornoEspecialidade = especialidadeRepo.save(novaEspecialidade);

        Veterinario novo = Veterinario.builder()
                .nome("Marcos")
                .cpf("123")
                .especialidade(retornoEspecialidade)
                .telefone("123")
                .build();
        Veterinario retorno = repo.save(novo);

        //Ação
        Optional<Veterinario> temp = repo.findById(retorno.getVeterinarioId());

        //Verificação
        Assertions.assertTrue(temp.isPresent());

        //Rollback
        repo.delete(retorno);
        especialidadeRepo.delete(retornoEspecialidade);
        areaRepo.delete(retornoArea);
    }

    @Test
    public void deveAtualizar(){
        //Cenário
        Area novaArea = Area.builder()
                .nome("Cirurgia Geral")
                .build();
        Area retornoArea = areaRepo.save(novaArea);

        Especialidade novaEspecialidade = Especialidade.builder()
                .nome("Ortopedia")
                .area(retornoArea)
                .build();
        Especialidade retornoEspecialidade = especialidadeRepo.save(novaEspecialidade);

        Veterinario novo = Veterinario.builder()
                .nome("Marcos")
                .cpf("123")
                .especialidade(retornoEspecialidade)
                .telefone("123")
                .build();
        Veterinario retorno = repo.save(novo);

        //Ação
        retorno.setNome("Novo Nome");
        Veterinario atualizado = repo.save(retorno);

        //Verificação
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals(atualizado.getVeterinarioId(), retorno.getVeterinarioId());
        Assertions.assertEquals(atualizado.getNome(), "Novo Nome");

        //Rollback
        repo.delete(retorno);
        especialidadeRepo.delete(retornoEspecialidade);
        areaRepo.delete(retornoArea);
    }

    @Test
    public void deveAtualizarEspecialidade(){
        //Cenário
        Area novaArea = Area.builder()
                .nome("Cirurgia Geral")
                .build();
        Area retornoArea = areaRepo.save(novaArea);

        Especialidade novaEspecialidade = Especialidade.builder()
                .nome("Ortopedia")
                .area(retornoArea)
                .build();
        Especialidade retornoEspecialidade = especialidadeRepo.save(novaEspecialidade);

        Especialidade outraEspecialidade = Especialidade.builder()
                .nome("Outra Especialiade")
                .area(retornoArea)
                .build();
        Especialidade retornoOutraEspecialidade = especialidadeRepo.save(outraEspecialidade);

        Veterinario novo = Veterinario.builder()
                .nome("Marcos")
                .cpf("123")
                .especialidade(retornoEspecialidade)
                .telefone("123")
                .build();
        Veterinario retorno = repo.save(novo);

        //Ação
        retorno.setEspecialidade(retornoOutraEspecialidade);
        Veterinario atualizado = repo.save(retorno);

        //Verificação
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals(atualizado.getVeterinarioId(), retorno.getVeterinarioId());

        //Rollback
        repo.delete(retorno);
        especialidadeRepo.delete(retornoEspecialidade);
        especialidadeRepo.delete(retornoOutraEspecialidade);
        areaRepo.delete(retornoArea);

    }
}
