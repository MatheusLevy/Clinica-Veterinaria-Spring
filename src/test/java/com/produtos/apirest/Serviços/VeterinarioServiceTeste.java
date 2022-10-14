package com.produtos.apirest.Serviços;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.models.Veterinario;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.EspecialidadeRepo;
import com.produtos.apirest.repository.VeterinarioRepo;
import com.produtos.apirest.service.VeterinarioService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@SpringBootTest
public class VeterinarioServiceTeste {

    @Autowired
    public VeterinarioRepo veterinarioRepo;

    @Autowired
    public VeterinarioService veterinarioService;

    @Autowired
    public AreaRepo areaRepo;

    @Autowired
    public EspecialidadeRepo especialidadeRepo;

    Random random = new Random();

    @Test
    public void deveSalvar(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especidalidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinário Teste")
                .telefone("Telefone Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .especialidade(especialidadeRetorno)
                .build();

        //Ação
        Veterinario veterinarioRetorno = veterinarioService.salvar(veterinario);

        //Verificação
        Assertions.assertNotNull(veterinarioRetorno);
        Assertions.assertNotNull(veterinarioRetorno.getVeterinarioId());

        //Rollback
        veterinarioRepo.delete(veterinarioRetorno);
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
                .nome("Especidalidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinário Teste")
                .telefone("Telefone Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .especialidade(especialidadeRetorno)
                .build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        //Ação
        veterinarioRetorno.setNome("Veterinario Atualizado");
        Veterinario veterinarioAtualizado = veterinarioService.atualizar(veterinarioRetorno);

        //Verificação
        Assertions.assertNotNull(veterinarioAtualizado);
        Assertions.assertEquals(veterinarioAtualizado.getVeterinarioId(), veterinarioRetorno.getVeterinarioId());
        Assertions.assertEquals(veterinarioAtualizado.getNome(), "Veterinario Atualizado");

        //Rollback
        veterinarioRepo.delete(veterinarioRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveRemover(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especidalidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinário Teste")
                .telefone("Telefone Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .especialidade(especialidadeRetorno)
                .build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        //Ação
        veterinarioService.remover(veterinarioRetorno);

        //Verificação
        Optional<Veterinario> veterinarios = veterinarioRepo.findById(veterinarioRetorno.getVeterinarioId());
        Assertions.assertNotNull(veterinarios);
        Assertions.assertFalse(veterinarios.isPresent());

        //Rollback
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveRemoverComFeedback(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especidalidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinário Teste")
                .telefone("Telefone Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .especialidade(especialidadeRetorno)
                .build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        //Ação
        Veterinario veterinarioFeedback = veterinarioService.removerFeedback(veterinarioRetorno);

        //Verificação
        Assertions.assertNotNull(veterinarioFeedback);
        Assertions.assertEquals(veterinarioRetorno.getVeterinarioId(), veterinarioFeedback.getVeterinarioId());

        //Rollback
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveBuscarPorId(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especidalidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinário Teste")
                .telefone("Telefone Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .especialidade(especialidadeRetorno)
                .build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        //Ação
        Veterinario veterinarioBuscado = veterinarioService.buscarPorId(veterinarioRetorno);

        //Verificação
        Assertions.assertNotNull(veterinarioBuscado);
        Assertions.assertEquals(veterinarioRetorno.getVeterinarioId(), veterinarioBuscado.getVeterinarioId());

        //Ação
        veterinarioRepo.delete(veterinarioRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveBuscarComFiltro(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especidalidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinário Teste")
                .telefone("Telefone Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .especialidade(especialidadeRetorno)
                .build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        //Ação
        List<Veterinario> veterinarios = veterinarioService.buscar(veterinarioRetorno);

        //Verificação
        Assertions.assertNotNull(veterinarios);
        Assertions.assertFalse(veterinarios.isEmpty());

        //Rollback
        veterinarioRepo.delete(veterinarioRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveBuscarEspecialidade(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especidalidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinário Teste")
                .telefone("Telefone Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .especialidade(especialidadeRetorno)
                .build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        //Ação
        Especialidade especialidadeBuscada = veterinarioService.buscarEspecialidade(veterinarioRetorno);

        //Verificação
        Assertions.assertNotNull(especialidadeBuscada);
        Assertions.assertEquals(especialidadeRetorno.getEspecialidadeId(), especialidadeBuscada.getEspecialidadeId());

        //Rollback
        veterinarioRepo.delete(veterinarioRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveAtualizarEspecialidade(){
        //Cenário
        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especidalidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinário Teste")
                .telefone("Telefone Teste")
                .cpf("CPF Teste")
                .especialidade(especialidadeRetorno)
                .build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        Especialidade especialidadeNova = Especialidade.builder()
                .nome("Especidalidade Nova")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeNovaRetorno = especialidadeRepo.save(especialidadeNova);

        //Ação
        Veterinario veterinarioAtualizado = veterinarioService.atualizarEspecialidade(especialidadeNovaRetorno, veterinarioRetorno);

        //Verificação
        Assertions.assertNotNull(veterinarioAtualizado);
        Assertions.assertNotNull(veterinarioAtualizado.getVeterinarioId());
        Assertions.assertEquals(veterinarioRetorno.getVeterinarioId(), veterinarioAtualizado.getVeterinarioId());

        //Rollback
        veterinarioRepo.delete(veterinarioRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        especialidadeRepo.delete(especialidadeNovaRetorno);
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
                .nome("Especidalidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinário Teste")
                .telefone("Telefone Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .especialidade(especialidadeRetorno)
                .build();
        Veterinario veterinarioRetorno = veterinarioService.salvar(veterinario);

        //Ação
        List<Veterinario> veterinarios = veterinarioService.buscarTodos();

        //Verificação
        Assertions.assertNotNull(veterinarios);
        Assertions.assertFalse(veterinarios.isEmpty());

        //Rollback
        veterinarioRepo.delete(veterinarioRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

}
