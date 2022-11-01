package com.produtos.apirest.Service;

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

import static com.produtos.apirest.Service.EspecialidadeServiceTeste.generateEspecialidade;
import static com.produtos.apirest.Service.EspecialidadeServiceTeste.rollbackEspecialidade;
import static com.produtos.apirest.Util.Util.generateCPF;
import static com.produtos.apirest.Util.Util.generateTelefone;

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


    private Veterinario generateVeterinario(Boolean initalizeFields){
        Veterinario veterinario = Veterinario.builder()
                .name("test")
                .phone(generateTelefone())
                .cpf(generateCPF())
                .expertise(generateEspecialidade(false, areaRepo))
                .build();
        if (initalizeFields) {
            Especialidade especialidade = generateEspecialidade(true, areaRepo);
            veterinario.setExpertise(especialidadeRepo.save(especialidade));
        }
        return veterinario;
    }

    protected static Veterinario generateVeterinario(Boolean initalizeFields, AreaRepo areaRepo,
                                                     EspecialidadeRepo especialidadeRepo){
        Veterinario veterinario = Veterinario.builder()
                .name("test")
                .phone(generateTelefone())
                .cpf(generateCPF())
                .expertise(generateEspecialidade(false, areaRepo))
                .build();
        if (initalizeFields) {
            Especialidade especialidade = generateEspecialidade(true, areaRepo);
            veterinario.setExpertise(especialidadeRepo.save(especialidade));
        }
        return veterinario;
    }

    private void rollback(Veterinario veterinario, Boolean skipVeterinario){
        if (!skipVeterinario)
            veterinarioRepo.delete(veterinario);
        especialidadeRepo.delete(veterinario.getExpertise());
        areaRepo.delete(veterinario.getExpertise().getArea());
    }

    protected static void rollbackVeterinario(Veterinario veterinario, VeterinarioRepo veterinarioRepo,
                                   AreaRepo areaRepo, EspecialidadeRepo especialidadeRepo,
                                   Boolean skipVeterinario){
        if (!skipVeterinario)
            veterinarioRepo.delete(veterinario);
        especialidadeRepo.delete(veterinario.getExpertise());
        areaRepo.delete(veterinario.getExpertise().getArea());
    }

    @Test
    public void deveSalvar(){
        Veterinario veterinarioSalvo = veterinarioService.salvar(generateVeterinario(true));
        Assertions.assertNotNull(veterinarioSalvo);
        rollback(veterinarioSalvo, false);
    }

    @Test
    public void deveAtualizar(){
        Veterinario veterinarioSalvo = veterinarioRepo.save(generateVeterinario(true));
        veterinarioSalvo.setName("Veterinario Atualizado");
        Veterinario veterinarioAtualizado = veterinarioService.atualizar(veterinarioSalvo);
        Assertions.assertNotNull(veterinarioAtualizado);
        Assertions.assertEquals(veterinarioAtualizado.getVeterinaryId(), veterinarioSalvo.getVeterinaryId());
        Assertions.assertEquals(veterinarioAtualizado.getName(), "Veterinario Atualizado");
        rollback(veterinarioAtualizado, false);
    }

    @Test
    public void deveRemover(){
        Veterinario veterinarioSalvo = veterinarioRepo.save(generateVeterinario(true));
        Long id = veterinarioSalvo.getVeterinaryId();
        veterinarioService.remover(veterinarioSalvo.getVeterinaryId());
        Assertions.assertFalse(veterinarioRepo.findById(id).isPresent());
        rollback(veterinarioSalvo, true);
    }

    @Test
    public void deveRemoverComFeedback(){
        Veterinario veterinarioSalvo = veterinarioRepo.save(generateVeterinario(true));
        Veterinario veterinarioFeedback = veterinarioService.removerComFeedback(veterinarioSalvo.getVeterinaryId());
        Assertions.assertNotNull(veterinarioFeedback);
        Assertions.assertEquals(veterinarioSalvo.getVeterinaryId(), veterinarioFeedback.getVeterinaryId());
        rollback(veterinarioFeedback, true);
    }

    @Test
    public void deveBuscarPorId(){
        Veterinario veterinarioSalvo = veterinarioRepo.save(generateVeterinario(true));
        Long id = veterinarioSalvo.getVeterinaryId();
        Veterinario veterinarioEncontrado = veterinarioService.buscarPorId(id);
        Assertions.assertNotNull(veterinarioEncontrado);
        Assertions.assertEquals(veterinarioSalvo.getVeterinaryId(), veterinarioEncontrado.getVeterinaryId());
        rollback(veterinarioSalvo, false);
    }

    @Test
    public void deveBuscarComFiltro(){
        Veterinario veterinarioSalvo = veterinarioRepo.save(generateVeterinario(true));
        List<Veterinario> veterinarioEncontradosList = veterinarioService.buscar(veterinarioSalvo);
        Assertions.assertNotNull(veterinarioEncontradosList);
        Assertions.assertFalse(veterinarioEncontradosList.isEmpty());
        rollback(veterinarioSalvo, false);
    }

    @Test
    public void deveBuscarEspecialidade(){
        Veterinario veterinarioSalvo = veterinarioRepo.save(generateVeterinario(true));
        Especialidade especialidadeEncontrada = veterinarioService.buscarEspecialidade(veterinarioSalvo);
        Assertions.assertNotNull(especialidadeEncontrada);
        Assertions.assertEquals(veterinarioSalvo.getExpertise().getExpertiseId(), especialidadeEncontrada.getExpertiseId());
        rollback(veterinarioSalvo, false);
    }

    @Test
    public void deveAtualizarEspecialidade(){
        Veterinario veterinarioSalvo = veterinarioRepo.save(generateVeterinario(true));
        Especialidade especialidadeAntiga = veterinarioSalvo.getExpertise();
        Especialidade especialidadeNova = especialidadeRepo.save(generateEspecialidade(true, areaRepo));
        Veterinario veterinarioAtualizado = veterinarioService.atualizarEspecialidade(veterinarioSalvo, especialidadeNova);
        Assertions.assertNotNull(veterinarioAtualizado);
        Assertions.assertEquals(veterinarioSalvo.getVeterinaryId(), veterinarioAtualizado.getVeterinaryId());
        rollback(veterinarioAtualizado, false);
        rollbackEspecialidade(especialidadeAntiga, especialidadeRepo, areaRepo);
    }

    @Test
    public void deveBuscarTodos(){
        Veterinario veterinarioSalvo = veterinarioService.salvar(generateVeterinario(true));
        List<Veterinario> veterinarioList = veterinarioService.buscarTodos();
        Assertions.assertNotNull(veterinarioList);
        Assertions.assertFalse(veterinarioList.isEmpty());
        rollback(veterinarioSalvo, false);
    }
}