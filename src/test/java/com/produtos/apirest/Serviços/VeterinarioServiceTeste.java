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

import java.util.List;
import java.util.Optional;

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

    @Test
    public void deveSalvar(){
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especidalidade Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        Veterinario veterinario = Veterinario.builder().nome("Veterinário Teste").telefone("Telefone Teste").cpf("CPF Teste").especialidade(especialidadeRetorno).build();

        Veterinario veterinarioRetorno = veterinarioService.salvar(veterinario);

        Assertions.assertNotNull(veterinarioRetorno);
        Assertions.assertNotNull(veterinarioRetorno.getVeterinarioId());

        veterinarioRepo.delete(veterinarioRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveAtualizar(){
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especidalidade Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        Veterinario veterinario = Veterinario.builder().nome("Veterinário Teste").telefone("Telefone Teste").cpf("CPF Teste").especialidade(especialidadeRetorno).build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        veterinarioRetorno.setNome("Veterinario Atualizado");
        Veterinario veterinarioAtualizado = veterinarioService.atualizar(veterinarioRetorno);

        Assertions.assertNotNull(veterinarioAtualizado);
        Assertions.assertEquals(veterinarioAtualizado.getVeterinarioId(), veterinarioRetorno.getVeterinarioId());

        veterinarioRepo.delete(veterinarioRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveRemover(){
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especidalidade Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        Veterinario veterinario = Veterinario.builder().nome("Veterinário Teste").telefone("Telefone Teste").cpf("CPF Teste").especialidade(especialidadeRetorno).build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        veterinarioService.remover(veterinarioRetorno);

        Optional<Veterinario> veterinarios = veterinarioRepo.findById(veterinarioRetorno.getVeterinarioId());
        Assertions.assertNotNull(veterinarios);
        Assertions.assertFalse(veterinarios.isPresent());

        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveRemoverComFeedback(){
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especidalidade Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        Veterinario veterinario = Veterinario.builder().nome("Veterinário Teste").telefone("Telefone Teste").cpf("CPF Teste").especialidade(especialidadeRetorno).build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        Veterinario veterinarioFeedback = veterinarioService.removerFeedback(veterinarioRetorno);

        Assertions.assertNotNull(veterinarioFeedback);
        Assertions.assertEquals(veterinarioRetorno.getVeterinarioId(), veterinarioFeedback.getVeterinarioId());

        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveBuscarPorId(){
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especidalidade Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        Veterinario veterinario = Veterinario.builder().nome("Veterinário Teste").telefone("Telefone Teste").cpf("CPF Teste").especialidade(especialidadeRetorno).build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        Veterinario veterinarioBuscado = veterinarioService.buscarDonoPorId(veterinarioRetorno);

        Assertions.assertNotNull(veterinarioBuscado);
        Assertions.assertEquals(veterinarioRetorno.getVeterinarioId(), veterinarioBuscado.getVeterinarioId());


        veterinarioRepo.delete(veterinarioRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveBuscarComFiltro(){
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especidalidade Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        Veterinario veterinario = Veterinario.builder().nome("Veterinário Teste").telefone("Telefone Teste").cpf("CPF Teste").especialidade(especialidadeRetorno).build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        List<Veterinario> veterinarios = veterinarioService.buscar(veterinarioRetorno);
        Assertions.assertNotNull(veterinarios);
        Assertions.assertFalse(veterinarios.isEmpty());

        veterinarioRepo.delete(veterinarioRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveBuscarEspecialidade(){
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especidalidade Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        Veterinario veterinario = Veterinario.builder().nome("Veterinário Teste").telefone("Telefone Teste").cpf("CPF Teste").especialidade(especialidadeRetorno).build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        Especialidade especialidadeBuscada = veterinarioService.buscarEspecialidade(veterinarioRetorno);

        Assertions.assertNotNull(especialidadeBuscada);
        Assertions.assertEquals(especialidadeRetorno.getEspecialidadeId(), especialidadeBuscada.getEspecialidadeId());

        veterinarioRepo.delete(veterinarioRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
    }

    @Test
    public void deveAtualizarEspecialidade(){
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especidalidade Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        Veterinario veterinario = Veterinario.builder().nome("Veterinário Teste").telefone("Telefone Teste").cpf("CPF Teste").especialidade(especialidadeRetorno).build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        Especialidade especialidadeNova = Especialidade.builder().nome("Especidalidade Nova").area(areaRetorno).build();
        Especialidade especialidadeNovaRetorno = especialidadeRepo.save(especialidadeNova);
        Veterinario veterinarioAtualizado = veterinarioService.atualizarEspecialidade(especialidadeNovaRetorno, veterinarioRetorno);

        Assertions.assertNotNull(veterinarioAtualizado);
        Assertions.assertNotNull(veterinarioAtualizado.getVeterinarioId());
        Assertions.assertEquals(veterinarioRetorno.getVeterinarioId(), veterinarioAtualizado.getVeterinarioId());

        veterinarioRepo.delete(veterinarioRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        especialidadeRepo.delete(especialidadeNovaRetorno);
        areaRepo.delete(areaRetorno);
    }
}
