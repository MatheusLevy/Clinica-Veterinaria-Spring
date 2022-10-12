package com.produtos.apirest.Model;

import com.produtos.apirest.models.*;
import com.produtos.apirest.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

@SpringBootTest
public class ConsultaTeste {
    @Autowired
    public ConsultaRepo repo;

    @Autowired
    public AnimalRepo animalRepo;

    @Autowired
    public DonoRepo donoRepo;

    @Autowired
    public AreaRepo areaRepo;

    @Autowired
    public EspecialidadeRepo especialidadeRepo;

    @Autowired
    public TipoConsultaRepo tipoConsultaRepo;

    @Autowired
    public TipoAnimalRepo tipoAnimalRepo;

    @Autowired
    public VeterinarioRepo veterinarioRepo;

    Random random = new Random();

    @Test
    public void deveCriarConsulta(){
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

        Veterinario novoVeterinario = Veterinario.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999))).
                especialidade(retornoEspecialidade)
                .telefone("123").build();
        Veterinario retornoVeterinario = veterinarioRepo.save(novoVeterinario);

        TipoAnimal novoTipoAnimal = TipoAnimal.builder()
                .nome("Gato")
                .build();
        TipoAnimal retornoTipoAnimal = tipoAnimalRepo.save(novoTipoAnimal);

        Dono novoDono = Dono.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("111111")
                .build();
        Dono retornoDono = donoRepo.save(novoDono);

        Animal novoAnimal = Animal.builder()
                .nome("Pepper")
                .tipoAnimal(retornoTipoAnimal)
                .dono(retornoDono)
                .build();
        Animal retornoAnimal = animalRepo.save(novoAnimal);

        TipoConsulta novoTipoConsulta = TipoConsulta
                .builder()
                .nome("Retorno")
                .build();
        TipoConsulta retornoTipoConsulta = tipoConsultaRepo.save(novoTipoConsulta);

        Consulta novaConsulta = Consulta.builder().tipoConsulta(retornoTipoConsulta).animal(retornoAnimal).veterinario(retornoVeterinario).data(LocalDate.now()).descricao("consulta teste").build();

        //Ação
        Consulta retornoConsulta = repo.save(novaConsulta);

        //Verificação
        Assertions.assertNotNull(retornoConsulta);

        //Rollback
        repo.delete(retornoConsulta);
        veterinarioRepo.delete(retornoVeterinario);
        especialidadeRepo.delete(retornoEspecialidade);
        areaRepo.delete(retornoArea);
        animalRepo.delete(retornoAnimal);
        tipoAnimalRepo.delete(retornoTipoAnimal);
        donoRepo.delete(retornoDono);
        tipoConsultaRepo.delete(retornoTipoConsulta);
    }

    @Test
    public void deveRemoverConsulta(){
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

        Veterinario novoVeterinario = Veterinario.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .especialidade(retornoEspecialidade)
                .telefone("123")
                .build();
        Veterinario retornoVeterinario = veterinarioRepo.save(novoVeterinario);

        TipoAnimal novoTipoAnimal = TipoAnimal.builder()
                .nome("Gato")
                .build();
        TipoAnimal retornoTipoAnimal = tipoAnimalRepo.save(novoTipoAnimal);

        Dono novoDono = Dono.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("111111")
                .build();
        Dono retornoDono = donoRepo.save(novoDono);

        Animal novoAnimal = Animal.builder()
                .nome("Pepper")
                .tipoAnimal(retornoTipoAnimal)
                .dono(retornoDono)
                .build();
        Animal retornoAnimal = animalRepo.save(novoAnimal);

        TipoConsulta novoTipoConsulta = TipoConsulta.builder()
                .nome("Retorno")
                .build();
        TipoConsulta retornoTipoConsulta = tipoConsultaRepo.save(novoTipoConsulta);

        Consulta novaConsulta = Consulta.builder()
                .tipoConsulta(retornoTipoConsulta)
                .animal(retornoAnimal)
                .veterinario(retornoVeterinario)
                .data(LocalDate.now())
                .descricao("consulta teste")
                .build();
        Consulta retornoConsulta = repo.save(novaConsulta);

        //Ação
        repo.delete(retornoConsulta);

        //Verificação
        Optional<Consulta> temp = repo.findById(retornoConsulta.getConsultaId());
        Assertions.assertFalse(temp.isPresent());

        //Rollback
        veterinarioRepo.delete(retornoVeterinario);
        especialidadeRepo.delete(retornoEspecialidade);
        areaRepo.delete(retornoArea);
        animalRepo.delete(retornoAnimal);
        tipoAnimalRepo.delete(retornoTipoAnimal);
        donoRepo.delete(retornoDono);
        tipoConsultaRepo.delete(retornoTipoConsulta);
    }

    @Test
    public void deveBuscarConsulta(){
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

        Veterinario novoVeterinario = Veterinario.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .especialidade(retornoEspecialidade)
                .telefone("123")
                .build();
        Veterinario retornoVeterinario = veterinarioRepo.save(novoVeterinario);

        TipoAnimal novoTipoAnimal = TipoAnimal.builder()
                .nome("Gato")
                .build();
        TipoAnimal retornoTipoAnimal = tipoAnimalRepo.save(novoTipoAnimal);

        Dono novoDono = Dono.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("111111")
                .build();
        Dono retornoDono = donoRepo.save(novoDono);

        Animal novoAnimal = Animal.builder()
                .nome("Pepper")
                .tipoAnimal(retornoTipoAnimal)
                .dono(retornoDono)
                .build();
        Animal retornoAnimal = animalRepo.save(novoAnimal);

        TipoConsulta novoTipoConsulta = TipoConsulta.builder()
                .nome("Retorno")
                .build();
        TipoConsulta retornoTipoConsulta = tipoConsultaRepo.save(novoTipoConsulta);

        Consulta novaConsulta = Consulta.builder()
                .tipoConsulta(retornoTipoConsulta)
                .animal(retornoAnimal)
                .veterinario(retornoVeterinario)
                .data(LocalDate.now())
                .descricao("consulta teste")
                .build();
        Consulta retornoConsulta = repo.save(novaConsulta);

        //Ação
        Optional<Consulta> temp = repo.findById(retornoConsulta.getConsultaId());
        Assertions.assertTrue(temp.isPresent());

        //Rollback
        repo.delete(retornoConsulta);
        veterinarioRepo.delete(retornoVeterinario);
        especialidadeRepo.delete(retornoEspecialidade);
        areaRepo.delete(retornoArea);
        animalRepo.delete(retornoAnimal);
        tipoAnimalRepo.delete(retornoTipoAnimal);
        donoRepo.delete(retornoDono);
        tipoConsultaRepo.delete(retornoTipoConsulta);
    }

    @Test
    public void deveAtualizarConsulta(){
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

        Veterinario novoVeterinario = Veterinario.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999))).
                especialidade(retornoEspecialidade)
                .telefone("123").build();
        Veterinario retornoVeterinario = veterinarioRepo.save(novoVeterinario);

        TipoAnimal novoTipoAnimal = TipoAnimal.builder()
                .nome("Gato")
                .build();
        TipoAnimal retornoTipoAnimal = tipoAnimalRepo.save(novoTipoAnimal);

        Dono novoDono = Dono.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("111111")
                .build();
        Dono retornoDono = donoRepo.save(novoDono);

        Animal novoAnimal = Animal.builder()
                .nome("Pepper")
                .tipoAnimal(retornoTipoAnimal)
                .dono(retornoDono)
                .build();
        Animal retornoAnimal = animalRepo.save(novoAnimal);

        TipoConsulta novoTipoConsulta = TipoConsulta
                .builder()
                .nome("Retorno")
                .build();
        TipoConsulta retornoTipoConsulta = tipoConsultaRepo.save(novoTipoConsulta);

        Consulta novaConsulta = Consulta.builder()
                .tipoConsulta(retornoTipoConsulta)
                .animal(retornoAnimal)
                .veterinario(retornoVeterinario)
                .data(LocalDate.now())
                .descricao("consulta teste")
                .build();
        Consulta retornoConsulta = repo.save(novaConsulta);

        //Ação
        retornoConsulta.setDescricao("Nova descrição");
        Consulta atualizada = repo.save(retornoConsulta);

        //Verificação
        Assertions.assertNotNull(atualizada);
        Assertions.assertEquals(retornoConsulta.getConsultaId(), atualizada.getConsultaId());
        Assertions.assertEquals(atualizada.getDescricao(), "Nova descrição");

        //Rollback
        repo.delete(atualizada);
        veterinarioRepo.delete(retornoVeterinario);
        especialidadeRepo.delete(retornoEspecialidade);
        areaRepo.delete(retornoArea);
        animalRepo.delete(retornoAnimal);
        tipoAnimalRepo.delete(retornoTipoAnimal);
        donoRepo.delete(retornoDono);
        tipoConsultaRepo.delete(retornoTipoConsulta);
    }

    @Test
    public void deveAtualizarTipoConsulta(){
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

        Veterinario novoVeterinario = Veterinario.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999))).
                especialidade(retornoEspecialidade)
                .telefone("123").build();
        Veterinario retornoVeterinario = veterinarioRepo.save(novoVeterinario);

        TipoAnimal novoTipoAnimal = TipoAnimal.builder()
                .nome("Gato")
                .build();
        TipoAnimal retornoTipoAnimal = tipoAnimalRepo.save(novoTipoAnimal);

        Dono novoDono = Dono.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("111111")
                .build();
        Dono retornoDono = donoRepo.save(novoDono);

        Animal novoAnimal = Animal.builder()
                .nome("Pepper")
                .tipoAnimal(retornoTipoAnimal)
                .dono(retornoDono)
                .build();
        Animal retornoAnimal = animalRepo.save(novoAnimal);

        TipoConsulta novoTipoConsulta = TipoConsulta.builder()
                .nome("Retorno")
                .build();
        TipoConsulta retornoTipoConsulta = tipoConsultaRepo.save(novoTipoConsulta);

        TipoConsulta outroTipoConsulta = TipoConsulta.builder()
                .nome("Novo Tipo")
                .build();
        TipoConsulta retornoOutroTipoConsulta = tipoConsultaRepo.save(outroTipoConsulta);

        Consulta novaConsulta = Consulta.builder()
                .tipoConsulta(retornoTipoConsulta)
                .animal(retornoAnimal)
                .veterinario(retornoVeterinario)
                .data(LocalDate.now())
                .descricao("consulta teste")
                .build();
        Consulta retornoConsulta = repo.save(novaConsulta);

        //Ação
        retornoConsulta.setTipoConsulta(retornoOutroTipoConsulta);
        Consulta atualizada = repo.save(retornoConsulta);

        //Verificação
        Assertions.assertNotNull(atualizada);
        Assertions.assertEquals(retornoConsulta.getConsultaId(), atualizada.getConsultaId());
        Assertions.assertEquals(atualizada.getTipoConsulta().getTipoConsultaId(), retornoOutroTipoConsulta.getTipoConsultaId());

        //Rollback
        repo.delete(retornoConsulta);
        veterinarioRepo.delete(retornoVeterinario);
        especialidadeRepo.delete(retornoEspecialidade);
        areaRepo.delete(retornoArea);
        animalRepo.delete(retornoAnimal);
        tipoAnimalRepo.delete(retornoTipoAnimal);
        donoRepo.delete(retornoDono);
        tipoConsultaRepo.delete(retornoTipoConsulta);
        tipoConsultaRepo.delete(retornoOutroTipoConsulta);
    }

    @Test
    public void deveAtualizarAnimal(){
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

        Veterinario novoVeterinario = Veterinario.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999))).
                especialidade(retornoEspecialidade)
                .telefone("123").build();
        Veterinario retornoVeterinario = veterinarioRepo.save(novoVeterinario);

        TipoAnimal novoTipoAnimal = TipoAnimal.builder()
                .nome("Gato")
                .build();
        TipoAnimal retornoTipoAnimal = tipoAnimalRepo.save(novoTipoAnimal);

        Dono novoDono = Dono.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("111111")
                .build();
        Dono retornoDono = donoRepo.save(novoDono);

        Animal novoAnimal = Animal.builder()
                .nome("Pepper")
                .tipoAnimal(retornoTipoAnimal)
                .dono(retornoDono)
                .build();
        Animal retornoAnimal = animalRepo.save(novoAnimal);

        Animal outroAnimal = Animal.builder()
                .nome("Outro Animal")
                .tipoAnimal(retornoTipoAnimal)
                .dono(retornoDono)
                .build();

        Animal retornoOutroAnimal = animalRepo.save(outroAnimal);

        TipoConsulta novoTipoConsulta = TipoConsulta.builder()
                .nome("Retorno")
                .build();
        TipoConsulta retornoTipoConsulta = tipoConsultaRepo.save(novoTipoConsulta);

        Consulta novaConsulta = Consulta.builder()
                .tipoConsulta(retornoTipoConsulta)
                .animal(retornoAnimal)
                .veterinario(retornoVeterinario)
                .data(LocalDate.now())
                .descricao("consulta teste")
                .build();
        Consulta retornoConsulta = repo.save(novaConsulta);

        //Ação
        retornoConsulta.setAnimal(retornoOutroAnimal);
        Consulta atualizada = repo.save(retornoConsulta);

        //Verificação
        Assertions.assertNotNull(atualizada);
        Assertions.assertEquals(retornoConsulta.getConsultaId(), atualizada.getConsultaId());
        Assertions.assertEquals(atualizada.getAnimal().getAnimalId(), retornoOutroAnimal.getAnimalId());

        //Rollback
        repo.delete(retornoConsulta);
        veterinarioRepo.delete(retornoVeterinario);
        especialidadeRepo.delete(retornoEspecialidade);
        areaRepo.delete(retornoArea);
        animalRepo.delete(retornoAnimal);
        animalRepo.delete(retornoOutroAnimal);
        tipoAnimalRepo.delete(retornoTipoAnimal);
        donoRepo.delete(retornoDono);
        tipoConsultaRepo.delete(retornoTipoConsulta);
    }

    @Test
    public void deveAtualizarVeterinario(){
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

        Veterinario novoVeterinario = Veterinario.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999))).
                especialidade(retornoEspecialidade)
                .telefone("123").build();
        Veterinario retornoVeterinario = veterinarioRepo.save(novoVeterinario);

        Veterinario outroVeterinario = Veterinario.builder()
                .nome("Outro Veterinario")
                .cpf(String.valueOf(random.nextInt(9999999))).
                especialidade(retornoEspecialidade)
                .telefone("123").build();
        Veterinario retornoOutroVeterinario = veterinarioRepo.save(outroVeterinario);

        TipoAnimal novoTipoAnimal = TipoAnimal.builder()
                .nome("Gato")
                .build();
        TipoAnimal retornoTipoAnimal = tipoAnimalRepo.save(novoTipoAnimal);

        Dono novoDono = Dono.builder()
                .nome("Marcos")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("111111")
                .build();
        Dono retornoDono = donoRepo.save(novoDono);

        Animal novoAnimal = Animal.builder()
                .nome("Pepper")
                .tipoAnimal(retornoTipoAnimal)
                .dono(retornoDono)
                .build();
        Animal retornoAnimal = animalRepo.save(novoAnimal);

        TipoConsulta novoTipoConsulta = TipoConsulta.builder()
                .nome("Retorno")
                .build();
        TipoConsulta retornoTipoConsulta = tipoConsultaRepo.save(novoTipoConsulta);

        Consulta novaConsulta = Consulta.builder()
                .tipoConsulta(retornoTipoConsulta)
                .animal(retornoAnimal)
                .veterinario(retornoVeterinario)
                .data(LocalDate.now())
                .descricao("consulta teste")
                .build();
        Consulta retornoConsulta = repo.save(novaConsulta);

        //Ação
        retornoConsulta.setVeterinario(retornoOutroVeterinario);
        Consulta atualizada = repo.save(retornoConsulta);

        //Verificação
        Assertions.assertNotNull(atualizada);
        Assertions.assertEquals(retornoConsulta.getConsultaId(), atualizada.getConsultaId());
        Assertions.assertEquals(atualizada.getVeterinario().getVeterinarioId(), retornoOutroVeterinario.getVeterinarioId());

        //Rollback
        repo.delete(retornoConsulta);
        veterinarioRepo.delete(retornoVeterinario);
        veterinarioRepo.delete(retornoOutroVeterinario);
        especialidadeRepo.delete(retornoEspecialidade);
        areaRepo.delete(retornoArea);
        animalRepo.delete(retornoAnimal);
        tipoAnimalRepo.delete(retornoTipoAnimal);
        donoRepo.delete(retornoDono);
        tipoConsultaRepo.delete(retornoTipoConsulta);
    }
}
