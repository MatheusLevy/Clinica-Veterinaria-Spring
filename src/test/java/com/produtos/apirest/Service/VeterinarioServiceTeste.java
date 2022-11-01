package com.produtos.apirest.Service;

import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.ExpertiseRepo;
import com.produtos.apirest.repository.VeterinaryRepo;
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
    public VeterinaryRepo veterinaryRepo;

    @Autowired
    public VeterinarioService veterinarioService;

    @Autowired
    public AreaRepo areaRepo;

    @Autowired
    public ExpertiseRepo expertiseRepo;


    private Veterinary generateVeterinario(Boolean initalizeFields){
        Veterinary veterinario = Veterinary.builder()
                .name("test")
                .phone(generateTelefone())
                .cpf(generateCPF())
                .expertise(generateEspecialidade(false, areaRepo))
                .build();
        if (initalizeFields) {
            Expertise especialidade = generateEspecialidade(true, areaRepo);
            veterinario.setExpertise(expertiseRepo.save(especialidade));
        }
        return veterinario;
    }

    protected static Veterinary generateVeterinario(Boolean initalizeFields, AreaRepo areaRepo,
                                                     ExpertiseRepo expertiseRepo){
        Veterinary veterinario = Veterinary.builder()
                .name("test")
                .phone(generateTelefone())
                .cpf(generateCPF())
                .expertise(generateEspecialidade(false, areaRepo))
                .build();
        if (initalizeFields) {
            Expertise especialidade = generateEspecialidade(true, areaRepo);
            veterinario.setExpertise(expertiseRepo.save(especialidade));
        }
        return veterinario;
    }

    private void rollback(Veterinary veterinario, Boolean skipVeterinario){
        if (!skipVeterinario)
            veterinaryRepo.delete(veterinario);
        expertiseRepo.delete(veterinario.getExpertise());
        areaRepo.delete(veterinario.getExpertise().getArea());
    }

    protected static void rollbackVeterinario(Veterinary veterinario, VeterinaryRepo veterinaryRepo,
                                   AreaRepo areaRepo, ExpertiseRepo expertiseRepo,
                                   Boolean skipVeterinario){
        if (!skipVeterinario)
            veterinaryRepo.delete(veterinario);
        expertiseRepo.delete(veterinario.getExpertise());
        areaRepo.delete(veterinario.getExpertise().getArea());
    }

    @Test
    public void deveSalvar(){
        Veterinary veterinarioSalvo = veterinarioService.salvar(generateVeterinario(true));
        Assertions.assertNotNull(veterinarioSalvo);
        rollback(veterinarioSalvo, false);
    }

    @Test
    public void deveAtualizar(){
        Veterinary veterinarioSalvo = veterinaryRepo.save(generateVeterinario(true));
        veterinarioSalvo.setName("Veterinario Atualizado");
        Veterinary veterinarioAtualizado = veterinarioService.atualizar(veterinarioSalvo);
        Assertions.assertNotNull(veterinarioAtualizado);
        Assertions.assertEquals(veterinarioAtualizado.getVeterinaryId(), veterinarioSalvo.getVeterinaryId());
        Assertions.assertEquals(veterinarioAtualizado.getName(), "Veterinario Atualizado");
        rollback(veterinarioAtualizado, false);
    }

    @Test
    public void deveRemover(){
        Veterinary veterinarioSalvo = veterinaryRepo.save(generateVeterinario(true));
        Long id = veterinarioSalvo.getVeterinaryId();
        veterinarioService.remover(veterinarioSalvo.getVeterinaryId());
        Assertions.assertFalse(veterinaryRepo.findById(id).isPresent());
        rollback(veterinarioSalvo, true);
    }

    @Test
    public void deveRemoverComFeedback(){
        Veterinary veterinarioSalvo = veterinaryRepo.save(generateVeterinario(true));
        Veterinary veterinarioFeedback = veterinarioService.removerComFeedback(veterinarioSalvo.getVeterinaryId());
        Assertions.assertNotNull(veterinarioFeedback);
        Assertions.assertEquals(veterinarioSalvo.getVeterinaryId(), veterinarioFeedback.getVeterinaryId());
        rollback(veterinarioFeedback, true);
    }

    @Test
    public void deveBuscarPorId(){
        Veterinary veterinarioSalvo = veterinaryRepo.save(generateVeterinario(true));
        Long id = veterinarioSalvo.getVeterinaryId();
        Veterinary veterinarioEncontrado = veterinarioService.buscarPorId(id);
        Assertions.assertNotNull(veterinarioEncontrado);
        Assertions.assertEquals(veterinarioSalvo.getVeterinaryId(), veterinarioEncontrado.getVeterinaryId());
        rollback(veterinarioSalvo, false);
    }

    @Test
    public void deveBuscarComFiltro(){
        Veterinary veterinarioSalvo = veterinaryRepo.save(generateVeterinario(true));
        List<Veterinary> veterinarioEncontradosList = veterinarioService.buscar(veterinarioSalvo);
        Assertions.assertNotNull(veterinarioEncontradosList);
        Assertions.assertFalse(veterinarioEncontradosList.isEmpty());
        rollback(veterinarioSalvo, false);
    }

    @Test
    public void deveBuscarEspecialidade(){
        Veterinary veterinarioSalvo = veterinaryRepo.save(generateVeterinario(true));
        Expertise especialidadeEncontrada = veterinarioService.buscarEspecialidade(veterinarioSalvo);
        Assertions.assertNotNull(especialidadeEncontrada);
        Assertions.assertEquals(veterinarioSalvo.getExpertise().getExpertiseId(), especialidadeEncontrada.getExpertiseId());
        rollback(veterinarioSalvo, false);
    }

    @Test
    public void deveAtualizarEspecialidade(){
        Veterinary veterinarioSalvo = veterinaryRepo.save(generateVeterinario(true));
        Expertise especialidadeAntiga = veterinarioSalvo.getExpertise();
        Expertise especialidadeNova = expertiseRepo.save(generateEspecialidade(true, areaRepo));
        Veterinary veterinarioAtualizado = veterinarioService.atualizarEspecialidade(veterinarioSalvo, especialidadeNova);
        Assertions.assertNotNull(veterinarioAtualizado);
        Assertions.assertEquals(veterinarioSalvo.getVeterinaryId(), veterinarioAtualizado.getVeterinaryId());
        rollback(veterinarioAtualizado, false);
        rollbackEspecialidade(especialidadeAntiga, expertiseRepo, areaRepo);
    }

    @Test
    public void deveBuscarTodos(){
        Veterinary veterinarioSalvo = veterinarioService.salvar(generateVeterinario(true));
        List<Veterinary> veterinarioList = veterinarioService.buscarTodos();
        Assertions.assertNotNull(veterinarioList);
        Assertions.assertFalse(veterinarioList.isEmpty());
        rollback(veterinarioSalvo, false);
    }
}