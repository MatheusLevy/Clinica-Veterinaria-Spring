package com.produtos.apirest.Model;

import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.repository.AreaRepo;
import com.produtos.apirest.repository.ExpertiseRepo;
import com.produtos.apirest.repository.VeterinaryRepo;
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
    public VeterinaryRepo veterinaryRepo;

    @Autowired
    public AreaRepo areaRepo;

    @Autowired
    public ExpertiseRepo expertiseRepo;

    private Veterinary generateVeterinario(Boolean initializedEspecialidade){
        Veterinary veterinario = Veterinary.builder()
                .name("name")
                .cpf(generateCPF())
                .phone(generateTelefone())
                .build();
        if (initializedEspecialidade)
            veterinario.setExpertise(expertiseRepo.save(generateEspecialidade(true, areaRepo)));
        return veterinario;
    }

    protected static Veterinary generateVeterinario(Boolean initializeEspecialidade,
                                                        ExpertiseRepo expertiseRepo,
                                                        AreaRepo areaRepo){
        Veterinary veterinario = Veterinary.builder()
                .name("name")
                .cpf(generateCPF())
                .phone(generateTelefone())
                .expertise(generateEspecialidade(true, areaRepo))
                .build();
        if (initializeEspecialidade)
            veterinario.setExpertise(
                    expertiseRepo.save(veterinario.getExpertise())
            );
        return veterinario;
    }

    private void rollback(Veterinary veterinario){
        veterinaryRepo.delete(veterinario);
        expertiseRepo.delete(veterinario.getExpertise());
        areaRepo.delete(veterinario.getExpertise().getArea());
    }

    protected static void rollbackVeterinario(Veterinary veterinario,
                            VeterinaryRepo veterinaryRepo,
                            AreaRepo areaRepo,
                            ExpertiseRepo expertiseRepo){
        veterinaryRepo.delete(veterinario);
        expertiseRepo.delete(veterinario.getExpertise());
        areaRepo.delete(veterinario.getExpertise().getArea());
    }

    @Test
    public void deveSalvar(){
        Veterinary veterinarioSalvo = veterinaryRepo.save(generateVeterinario(true));
        Assertions.assertNotNull(veterinarioSalvo);
        Assertions.assertEquals(veterinarioSalvo.getName(), generateVeterinario(false).getName());
        rollback(veterinarioSalvo);
    }

    @Test
    public void deveAtualizar(){
        Veterinary veterinarioSalvo = veterinaryRepo.save(generateVeterinario(true));
        veterinarioSalvo.setName("Novo Nome");
        Veterinary veterinarioAtualizado = veterinaryRepo.save(veterinarioSalvo);
        Assertions.assertNotNull(veterinarioAtualizado);
        Assertions.assertEquals(veterinarioAtualizado.getVeterinaryId(), veterinarioSalvo.getVeterinaryId());
        Assertions.assertEquals(veterinarioAtualizado.getName(), "Novo Nome");
        rollback(veterinarioAtualizado);
    }

    @Test
    public void deveAtualizarEspecialidade(){
        Veterinary veterinarioSalvo = veterinaryRepo.save(generateVeterinario(true));
        Expertise especialidadeAntiga = veterinarioSalvo.getExpertise();
        veterinarioSalvo.setExpertise(expertiseRepo.save(generateEspecialidade(true, areaRepo)));
        Veterinary veterinarioAtualizado = veterinaryRepo.save(veterinarioSalvo);
        Assertions.assertNotNull(veterinarioAtualizado);
        Assertions.assertEquals(veterinarioAtualizado.getVeterinaryId(), veterinarioSalvo.getVeterinaryId());
        rollback(veterinarioAtualizado);
        rollbackEspecialidade(especialidadeAntiga, expertiseRepo, areaRepo);
    }

    @Test
    public void deveRemover(){
        Veterinary veterinarioSalvo = veterinaryRepo.save(generateVeterinario(true));
        Long id  = veterinarioSalvo.getVeterinaryId();
        veterinaryRepo.deleteById(id);
        Assertions.assertFalse(veterinaryRepo.findById(id).isPresent());
        rollbackEspecialidade(veterinarioSalvo.getExpertise(), expertiseRepo, areaRepo);
    }

    @Test
    public void deveBuscar(){
        Veterinary veterinarioSalvo = veterinaryRepo.save(generateVeterinario(true));
        Long id = veterinarioSalvo.getVeterinaryId();
        Assertions.assertTrue(veterinaryRepo.findById(id).isPresent());
        rollback(veterinarioSalvo);
    }
}