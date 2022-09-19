package com.produtos.apirest.Model;

import com.produtos.apirest.models.*;
import com.produtos.apirest.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

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
    public TipoConsultaRepo tipo_consultaRepo;

    @Autowired
    public TipoAnimalRepo tipo_animalRepo;

    @Autowired
    public VeterinarioRepo veterinarioRepo;

    @Test
    public void deveCriarConsulta(){
        //Cenário
        Area novaArea = Area.builder().nome("Cirurgia Geral").build();
        Area retornoArea = areaRepo.save(novaArea);
        Especialidade novaEspecialidade = Especialidade.builder().nome("Ortopedia").area(retornoArea).build();
        Especialidade retornoEspecialidade = especialidadeRepo.save(novaEspecialidade);
        Veterinario novoVeterinario = Veterinario.builder().nome("Marcos").cpf("123").especialidade(retornoEspecialidade).telefone("123").build();
        Veterinario retornoVeterinario = veterinarioRepo.save(novoVeterinario);
        TipoAnimal novoTipoAnimal = TipoAnimal.builder().nome("Gato").build();
        TipoAnimal retornoTipoAnimal = tipo_animalRepo.save(novoTipoAnimal);
        Dono novoDono = Dono.builder().nome("Marcos").cpf("123").telefone("111111").build();
        Dono retornoDono = donoRepo.save(novoDono);
        Animal novoAnimal = Animal.builder().nome("Pepper").tipoAnimal(retornoTipoAnimal).dono(retornoDono).build();
        Animal retornoAnimal = animalRepo.save(novoAnimal);
        TipoConsulta novoTipoConsulta = TipoConsulta.builder().nome("Retorno").build();
        TipoConsulta retornoTipoConsulta = tipo_consultaRepo.save(novoTipoConsulta);
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
        tipo_animalRepo.delete(retornoTipoAnimal);
        donoRepo.delete(retornoDono);
        tipo_consultaRepo.delete(retornoTipoConsulta);
    }

    @Test
    public void deveRemoverConsulta(){
        //Cenário
        Area novaArea = Area.builder().nome("Cirurgia Geral").build();
        Area retornoArea = areaRepo.save(novaArea);
        Especialidade novaEspecialidade = Especialidade.builder().nome("Ortopedia").area(retornoArea).build();
        Especialidade retornoEspecialidade = especialidadeRepo.save(novaEspecialidade);
        Veterinario novoVeterinario = Veterinario.builder().nome("Marcos").cpf("123").especialidade(retornoEspecialidade).telefone("123").build();
        Veterinario retornoVeterinario = veterinarioRepo.save(novoVeterinario);
        TipoAnimal novoTipoAnimal = TipoAnimal.builder().nome("Gato").build();
        TipoAnimal retornoTipoAnimal = tipo_animalRepo.save(novoTipoAnimal);
        Dono novoDono = Dono.builder().nome("Marcos").cpf("123").telefone("111111").build();
        Dono retornoDono = donoRepo.save(novoDono);
        Animal novoAnimal = Animal.builder().nome("Pepper").tipoAnimal(retornoTipoAnimal).dono(retornoDono).build();
        Animal retornoAnimal = animalRepo.save(novoAnimal);
        TipoConsulta novoTipoConsulta = TipoConsulta.builder().nome("Retorno").build();
        TipoConsulta retornoTipoConsulta = tipo_consultaRepo.save(novoTipoConsulta);
        Consulta novaConsulta = Consulta.builder().tipoConsulta(retornoTipoConsulta).animal(retornoAnimal).veterinario(retornoVeterinario).data(LocalDate.now()).descricao("consulta teste").build();
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
        tipo_animalRepo.delete(retornoTipoAnimal);
        donoRepo.delete(retornoDono);
        tipo_consultaRepo.delete(retornoTipoConsulta);
    }

    @Test
    public void deveBuscarConsulta(){
        //Cenário
        Area novaArea = Area.builder().nome("Cirurgia Geral").build();
        Area retornoArea = areaRepo.save(novaArea);
        Especialidade novaEspecialidade = Especialidade.builder().nome("Ortopedia").area(retornoArea).build();
        Especialidade retornoEspecialidade = especialidadeRepo.save(novaEspecialidade);
        Veterinario novoVeterinario = Veterinario.builder().nome("Marcos").cpf("123").especialidade(retornoEspecialidade).telefone("123").build();
        Veterinario retornoVeterinario = veterinarioRepo.save(novoVeterinario);
        TipoAnimal novoTipoAnimal = TipoAnimal.builder().nome("Gato").build();
        TipoAnimal retornoTipoAnimal = tipo_animalRepo.save(novoTipoAnimal);
        Dono novoDono = Dono.builder().nome("Marcos").cpf("123").telefone("111111").build();
        Dono retornoDono = donoRepo.save(novoDono);
        Animal novoAnimal = Animal.builder().nome("Pepper").tipoAnimal(retornoTipoAnimal).dono(retornoDono).build();
        Animal retornoAnimal = animalRepo.save(novoAnimal);
        TipoConsulta novoTipoConsulta = TipoConsulta.builder().nome("Retorno").build();
        TipoConsulta retornoTipoConsulta = tipo_consultaRepo.save(novoTipoConsulta);
        Consulta novaConsulta = Consulta.builder().tipoConsulta(retornoTipoConsulta).animal(retornoAnimal).veterinario(retornoVeterinario).data(LocalDate.now()).descricao("consulta teste").build();
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
        tipo_animalRepo.delete(retornoTipoAnimal);
        donoRepo.delete(retornoDono);
        tipo_consultaRepo.delete(retornoTipoConsulta);
    }
}
