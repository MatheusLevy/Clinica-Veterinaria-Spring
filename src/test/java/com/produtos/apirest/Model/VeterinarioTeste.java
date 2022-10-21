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
                .nome("nome")
                .cpf(generateCPF())
                .telefone(generateTelefone())
                .build();
        if (initializedEspecialidade)
            veterinario.setEspecialidade(especialidadeRepo.save(generateEspecialidade(true, areaRepo)));
        return veterinario;
    }

    protected static Veterinario generateVeterinario(Boolean initializeEspecialidade,
                                                        EspecialidadeRepo especialidadeRepo,
                                                        AreaRepo areaRepo){
        Veterinario veterinario = Veterinario.builder()
                .nome("nome")
                .cpf(generateCPF())
                .telefone(generateTelefone())
                .especialidade(generateEspecialidade(true, areaRepo))
                .build();
        if (initializeEspecialidade)
            veterinario.setEspecialidade(
                    especialidadeRepo.save(veterinario.getEspecialidade())
            );
        return veterinario;
    }

    private void rollback(Veterinario veterinario){
        veterinarioRepo.delete(veterinario);
        especialidadeRepo.delete(veterinario.getEspecialidade());
        areaRepo.delete(veterinario.getEspecialidade().getArea());
    }

    protected static void rollbackVeterinario(Veterinario veterinario,
                            VeterinarioRepo veterinarioRepo,
                            AreaRepo areaRepo,
                            EspecialidadeRepo especialidadeRepo){
        veterinarioRepo.delete(veterinario);
        especialidadeRepo.delete(veterinario.getEspecialidade());
        areaRepo.delete(veterinario.getEspecialidade().getArea());
    }

    @Test
    public void deveSalvarModel(){
        Veterinario veterinarioSalvo = veterinarioRepo.save(generateVeterinario(true));
        Assertions.assertNotNull(veterinarioSalvo);
        Assertions.assertEquals(veterinarioSalvo.getNome(), generateVeterinario(false).getNome());
        rollback(veterinarioSalvo);
    }

    @Test
    public void deveAtualizarModel(){
        Veterinario veterinarioSalvo = veterinarioRepo.save(generateVeterinario(true));
        veterinarioSalvo.setNome("Novo Nome");
        Veterinario veterinarioAtualizado = veterinarioRepo.save(veterinarioSalvo);
        Assertions.assertNotNull(veterinarioAtualizado);
        Assertions.assertEquals(veterinarioAtualizado.getVeterinarioId(), veterinarioSalvo.getVeterinarioId());
        Assertions.assertEquals(veterinarioAtualizado.getNome(), "Novo Nome");
        rollback(veterinarioAtualizado);
    }

    @Test
    public void deveAtualizarEspecialidadeModel(){
        Veterinario veterinarioSalvo = veterinarioRepo.save(generateVeterinario(true));
        Especialidade especialidadeAntiga = veterinarioSalvo.getEspecialidade();
        veterinarioSalvo.setEspecialidade(especialidadeRepo.save(generateEspecialidade(true, areaRepo)));
        Veterinario veterinarioAtualizado = veterinarioRepo.save(veterinarioSalvo);
        Assertions.assertNotNull(veterinarioAtualizado);
        Assertions.assertEquals(veterinarioAtualizado.getVeterinarioId(), veterinarioSalvo.getVeterinarioId());
        rollback(veterinarioAtualizado);
        rollbackEspecialidade(especialidadeAntiga, especialidadeRepo, areaRepo);
    }

    @Test
    public void deveRemoverModel(){
        Veterinario veterinarioSalvo = veterinarioRepo.save(generateVeterinario(true));
        Long id  = veterinarioSalvo.getVeterinarioId();
        veterinarioRepo.deleteById(id);
        Assertions.assertFalse(veterinarioRepo.findById(id).isPresent());
        rollbackEspecialidade(veterinarioSalvo.getEspecialidade(), especialidadeRepo, areaRepo);
    }

    @Test
    public void deveBuscarModel(){
        Veterinario veterinarioSalvo = veterinarioRepo.save(generateVeterinario(true));
        Long id = veterinarioSalvo.getVeterinarioId();
        Assertions.assertTrue(veterinarioRepo.findById(id).isPresent());
        rollback(veterinarioSalvo);
    }
}