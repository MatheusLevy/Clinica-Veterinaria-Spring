package com.produtos.apirest.Model;

import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.models.Veterinario;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.EspecialidadeRepo;
import com.produtos.apirest.repository.VeterinarioRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.produtos.apirest.Model.EspecialidadeTeste.generateEspecialidade;
import static com.produtos.apirest.Model.EspecialidadeTeste.rollbackEspecialidade;
import static com.produtos.apirest.Util.Util.generateCPF;
import static com.produtos.apirest.Util.Util.generateTelefone;

@SpringBootTest
public class VeterinarioTeste {
    @Autowired
    public VeterinarioRepo veterinarioRepo;

    @Autowired
    public AreaRepo areaRepo;

    @Autowired
    public EspecialidadeRepo especialidadeRepo;

    private Veterinario generateVeterinario(Boolean initializedEspecialidade){
        Veterinario veterinario = Veterinario.builder()
                .name("name")
                .cpf(generateCPF())
                .phone(generateTelefone())
                .build();
        if (initializedEspecialidade)
            veterinario.setExpertise(especialidadeRepo.save(generateEspecialidade(true, areaRepo)));
        return veterinario;
    }

    protected static Veterinario generateVeterinario(Boolean initializeEspecialidade,
                                                        EspecialidadeRepo especialidadeRepo,
                                                        AreaRepo areaRepo){
        Veterinario veterinario = Veterinario.builder()
                .name("name")
                .cpf(generateCPF())
                .phone(generateTelefone())
                .expertise(generateEspecialidade(true, areaRepo))
                .build();
        if (initializeEspecialidade)
            veterinario.setExpertise(
                    especialidadeRepo.save(veterinario.getExpertise())
            );
        return veterinario;
    }

    private void rollback(Veterinario veterinario){
        veterinarioRepo.delete(veterinario);
        especialidadeRepo.delete(veterinario.getExpertise());
        areaRepo.delete(veterinario.getExpertise().getArea());
    }

    protected static void rollbackVeterinario(Veterinario veterinario,
                            VeterinarioRepo veterinarioRepo,
                            AreaRepo areaRepo,
                            EspecialidadeRepo especialidadeRepo){
        veterinarioRepo.delete(veterinario);
        especialidadeRepo.delete(veterinario.getExpertise());
        areaRepo.delete(veterinario.getExpertise().getArea());
    }

    @Test
    public void deveSalvar(){
        Veterinario veterinarioSalvo = veterinarioRepo.save(generateVeterinario(true));
        Assertions.assertNotNull(veterinarioSalvo);
        Assertions.assertEquals(veterinarioSalvo.getName(), generateVeterinario(false).getName());
        rollback(veterinarioSalvo);
    }

    @Test
    public void deveAtualizar(){
        Veterinario veterinarioSalvo = veterinarioRepo.save(generateVeterinario(true));
        veterinarioSalvo.setName("Novo Nome");
        Veterinario veterinarioAtualizado = veterinarioRepo.save(veterinarioSalvo);
        Assertions.assertNotNull(veterinarioAtualizado);
        Assertions.assertEquals(veterinarioAtualizado.getVeterinaryId(), veterinarioSalvo.getVeterinaryId());
        Assertions.assertEquals(veterinarioAtualizado.getName(), "Novo Nome");
        rollback(veterinarioAtualizado);
    }

    @Test
    public void deveAtualizarEspecialidade(){
        Veterinario veterinarioSalvo = veterinarioRepo.save(generateVeterinario(true));
        Especialidade especialidadeAntiga = veterinarioSalvo.getExpertise();
        veterinarioSalvo.setExpertise(especialidadeRepo.save(generateEspecialidade(true, areaRepo)));
        Veterinario veterinarioAtualizado = veterinarioRepo.save(veterinarioSalvo);
        Assertions.assertNotNull(veterinarioAtualizado);
        Assertions.assertEquals(veterinarioAtualizado.getVeterinaryId(), veterinarioSalvo.getVeterinaryId());
        rollback(veterinarioAtualizado);
        rollbackEspecialidade(especialidadeAntiga, especialidadeRepo, areaRepo);
    }

    @Test
    public void deveRemover(){
        Veterinario veterinarioSalvo = veterinarioRepo.save(generateVeterinario(true));
        Long id  = veterinarioSalvo.getVeterinaryId();
        veterinarioRepo.deleteById(id);
        Assertions.assertFalse(veterinarioRepo.findById(id).isPresent());
        rollbackEspecialidade(veterinarioSalvo.getExpertise(), especialidadeRepo, areaRepo);
    }

    @Test
    public void deveBuscar(){
        Veterinario veterinarioSalvo = veterinarioRepo.save(generateVeterinario(true));
        Long id = veterinarioSalvo.getVeterinaryId();
        Assertions.assertTrue(veterinarioRepo.findById(id).isPresent());
        rollback(veterinarioSalvo);
    }
}