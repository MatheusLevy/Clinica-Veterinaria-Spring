package com.produtos.apirest.Serviços;

import com.produtos.apirest.models.*;
import com.produtos.apirest.repository.*;
import com.produtos.apirest.service.ConsultaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@SpringBootTest
public class ConsultaServiceTeste {

    @Autowired
    public ConsultaService consultaService;

    @Autowired
    public ConsultaRepo consultaRepo;

    @Autowired
    public DonoRepo donoRepo;

    @Autowired
    public TipoAnimalRepo tipoAnimalRepo;

    @Autowired
    public AnimalRepo animalRepo;

    @Autowired
    public AreaRepo areaRepo;

    @Autowired
    public EspecialidadeRepo especialidadeRepo;

    @Autowired
    public VeterinarioRepo veterinarioRepo;

    @Autowired
    public TipoConsultaRepo tipoConsultaRepo;

    Random random = new Random();

    @Test
    public void deveSalvar(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        TipoAnimal tipoAnimalRetorno =  tipoAnimalRepo.save(tipoAnimal);

        Animal animal =  Animal.builder(

        ).tipoAnimal(tipoAnimalRetorno)
                .dono(donoRetorno)
                .nome("Animal Teste")
                .build();
        Animal animalRetorno = animalRepo.save(animal);

        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especialidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinario Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("telefone teste")
                .especialidade(especialidadeRetorno)
                .build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        TipoConsulta tipoConsulta = TipoConsulta.builder()
                .nome("Tipo Consulta Teste")
                .build();
        TipoConsulta tipoConsultaRetorno = tipoConsultaRepo.save(tipoConsulta);

        Consulta consulta = Consulta.builder()
                .tipoConsulta(tipoConsultaRetorno)
                .data(LocalDate.now())
                .descricao("Descricao Teste")
                .animal(animalRetorno)
                .veterinario(veterinarioRetorno)
                .build();

        //Ação
        Consulta consultaRetorno = consultaService.salvar(consulta);

        //Verificação
        Assertions.assertNotNull(consultaRetorno);
        Assertions.assertNotNull(consultaRetorno.getConsultaId());

        //Rollback
        consultaRepo.delete(consultaRetorno);
        animalRepo.delete(animalRetorno);
        veterinarioRepo.delete(veterinarioRetorno);
        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoAnimalRetorno);
        tipoConsultaRepo.delete(tipoConsultaRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveAtualizar(){
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        TipoAnimal tipoAnimalRetorno =  tipoAnimalRepo.save(tipoAnimal);

        Animal animal =  Animal.builder()
                .tipoAnimal(tipoAnimalRetorno)
                .dono(donoRetorno)
                .nome("Animal Teste")
                .build();
        Animal animalRetorno = animalRepo.save(animal);

        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade
                .builder()
                .nome("Especialidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinario Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("telefone teste")
                .especialidade(especialidadeRetorno)
                .build();

        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        TipoConsulta tipoConsulta = TipoConsulta.builder()
                .nome("Tipo Consulta Teste")
                .build();
        TipoConsulta tipoConsultaRetorno = tipoConsultaRepo.save(tipoConsulta);

        Consulta consulta = Consulta.builder()
                .tipoConsulta(tipoConsultaRetorno)
                .data(LocalDate.now())
                .descricao("Descricao Teste")
                .animal(animalRetorno)
                .veterinario(veterinarioRetorno)
                .build();
        Consulta consultaRetorno = consultaRepo.save(consulta);

        //Ação
        consultaRetorno.setDescricao("Nova Descrição");
        Consulta consultaAtualizada = consultaService.atualizar(consultaRetorno);

        //Verificação
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaRetorno.getConsultaId(), consultaAtualizada.getConsultaId());
        Assertions.assertEquals(consultaAtualizada.getDescricao(), "Nova Descrição");

        //Rollback
        consultaRepo.delete(consultaRetorno);
        animalRepo.delete(animalRetorno);
        veterinarioRepo.delete(veterinarioRetorno);
        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoAnimalRetorno);
        tipoConsultaRepo.delete(tipoConsultaRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveRemover(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        TipoAnimal tipoAnimalRetorno =  tipoAnimalRepo.save(tipoAnimal);

        Animal animal =  Animal.builder()
                .tipoAnimal(tipoAnimalRetorno)
                .dono(donoRetorno)
                .nome("Animal Teste")
                .build();
        Animal animalRetorno = animalRepo.save(animal);

        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especialidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinario Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("telefone teste")
                .especialidade(especialidadeRetorno)
                .build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        TipoConsulta tipoConsulta = TipoConsulta.builder()
                .nome("Tipo Consulta Teste")
                .build();
        TipoConsulta tipoConsultaRetorno = tipoConsultaRepo.save(tipoConsulta);

        Consulta consulta = Consulta.builder()
                .tipoConsulta(tipoConsultaRetorno)
                .data(LocalDate.now())
                .descricao("Descricao Teste")
                .animal(animalRetorno)
                .veterinario(veterinarioRetorno)
                .build();
        Consulta consultaRetorno = consultaRepo.save(consulta);

        //Ação
        consultaService.remover(consultaRetorno);

        //Verificação
        Optional<Consulta> consultaTemp = consultaRepo.findById(consultaRetorno.getConsultaId());
        Assertions.assertFalse(consultaTemp.isPresent());

        //Rollback
        consultaRepo.delete(consultaRetorno);
        animalRepo.delete(animalRetorno);
        veterinarioRepo.delete(veterinarioRetorno);
        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoAnimalRetorno);
        tipoConsultaRepo.delete(tipoConsultaRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveRemoverComFeedback(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        TipoAnimal tipoAnimalRetorno =  tipoAnimalRepo.save(tipoAnimal);

        Animal animal =  Animal.builder()
                .tipoAnimal(tipoAnimalRetorno)
                .dono(donoRetorno)
                .nome("Animal Teste")
                .build();
        Animal animalRetorno = animalRepo.save(animal);

        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especialidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinario Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("telefone teste")
                .especialidade(especialidadeRetorno)
                .build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        TipoConsulta tipoConsulta = TipoConsulta.builder()
                .nome("Tipo Consulta Teste")
                .build();
        TipoConsulta tipoConsultaRetorno = tipoConsultaRepo.save(tipoConsulta);

        Consulta consulta = Consulta.builder()
                .tipoConsulta(tipoConsultaRetorno)
                .data(LocalDate.now())
                .descricao("Descricao Teste")
                .animal(animalRetorno)
                .veterinario(veterinarioRetorno)
                .build();
        Consulta consultaRetorno = consultaRepo.save(consulta);

        //Ação
        Consulta consultaRemovida = consultaService.removerFeedback(consultaRetorno);

        //Verificação
        Assertions.assertNotNull(consultaRemovida);
        Assertions.assertEquals(consultaRemovida.getConsultaId(), consultaRetorno.getConsultaId());

        //Rollback
        consultaRepo.delete(consultaRetorno);
        animalRepo.delete(animalRetorno);
        veterinarioRepo.delete(veterinarioRetorno);
        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoAnimalRetorno);
        tipoConsultaRepo.delete(tipoConsultaRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveAtualizarVeterinario(){

        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        TipoAnimal tipoAnimalRetorno =  tipoAnimalRepo.save(tipoAnimal);

        Animal animal =  Animal.builder()
                .tipoAnimal(tipoAnimalRetorno)
                .dono(donoRetorno)
                .nome("Animal Teste")
                .build();
        Animal animalRetorno = animalRepo.save(animal);

        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especialidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinario Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("telefone teste")
                .especialidade(especialidadeRetorno)
                .build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        TipoConsulta tipoConsulta = TipoConsulta.builder()
                .nome("Tipo Consulta Teste")
                .build();
        TipoConsulta tipoConsultaRetorno = tipoConsultaRepo.save(tipoConsulta);

        Consulta consulta = Consulta.builder()
                .tipoConsulta(tipoConsultaRetorno)
                .data(LocalDate.now())
                .descricao("Descricao Teste")
                .animal(animalRetorno)
                .veterinario(veterinarioRetorno)
                .build();
        Consulta consultaRetorno = consultaRepo.save(consulta);

        Veterinario veterinarioNovo = Veterinario.builder()
                .nome("Veterinario Novo")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("telefone teste")
                .especialidade(especialidadeRetorno)
                .build();
        Veterinario veterinarioNovoRetorno = veterinarioRepo.save(veterinarioNovo);

        //Ação
        Consulta consultaAtualizada = consultaService.atualizarVeterinario(veterinarioNovoRetorno, consultaRetorno);

        //Verificação
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaAtualizada.getVeterinario().getVeterinarioId(), veterinarioNovoRetorno.getVeterinarioId());

        //Rollback
        consultaRepo.delete(consultaRetorno);
        animalRepo.delete(animalRetorno);
        veterinarioRepo.delete(veterinarioRetorno);
        veterinarioRepo.delete(veterinarioNovoRetorno);
        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoAnimalRetorno);
        tipoConsultaRepo.delete(tipoConsultaRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveAtualizarAnimal(){
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        TipoAnimal tipoAnimalRetorno =  tipoAnimalRepo.save(tipoAnimal);

        Animal animal =  Animal.builder()
                .tipoAnimal(tipoAnimalRetorno)
                .dono(donoRetorno)
                .nome("Animal Teste")
                .build();
        Animal animalRetorno = animalRepo.save(animal);

        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especialidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinario Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("telefone teste")
                .especialidade(especialidadeRetorno)
                .build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        TipoConsulta tipoConsulta = TipoConsulta.builder()
                .nome("Tipo Consulta Teste")
                .build();
        TipoConsulta tipoConsultaRetorno = tipoConsultaRepo.save(tipoConsulta);

        Consulta consulta = Consulta.builder()
                .tipoConsulta(tipoConsultaRetorno)
                .data(LocalDate.now())
                .descricao("Descricao Teste")
                .animal(animalRetorno)
                .veterinario(veterinarioRetorno)
                .build();
        Consulta consultaRetorno = consultaRepo.save(consulta);

        Animal animalNovo = Animal.builder()
                .tipoAnimal(tipoAnimalRetorno)
                .dono(donoRetorno)
                .nome("Animal Novo")
                .build();
        Animal animalNovoRetorno = animalRepo.save(animalNovo);

        //Ação
        Consulta consultaAtualizada = consultaService.atualizarAnimal(animalNovoRetorno, consultaRetorno);

        //Verificação
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaAtualizada.getAnimal().getAnimalId(), animalNovoRetorno.getAnimalId());

        //Rollback
        consultaRepo.delete(consultaRetorno);
        animalRepo.delete(animalRetorno);
        animalRepo.delete(animalNovoRetorno);
        veterinarioRepo.delete(veterinarioRetorno);
        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoAnimalRetorno);
        tipoConsultaRepo.delete(tipoConsultaRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveAtualizarTipoConsulta(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        TipoAnimal tipoAnimalRetorno =  tipoAnimalRepo.save(tipoAnimal);

        Animal animal =  Animal.builder()
                .tipoAnimal(tipoAnimalRetorno)
                .dono(donoRetorno)
                .nome("Animal Teste")
                .build();
        Animal animalRetorno = animalRepo.save(animal);

        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especialidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinario Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("telefone teste")
                .especialidade(especialidadeRetorno)
                .build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        TipoConsulta tipoConsulta = TipoConsulta.builder()
                .nome("Tipo Consulta Teste")
                .build();
        TipoConsulta tipoConsultaRetorno = tipoConsultaRepo.save(tipoConsulta);

        Consulta consulta = Consulta.builder()
                .tipoConsulta(tipoConsultaRetorno)
                .data(LocalDate.now())
                .descricao("Descricao Teste")
                .animal(animalRetorno)
                .veterinario(veterinarioRetorno)
                .build();
        Consulta consultaRetorno = consultaRepo.save(consulta);

        TipoConsulta tipoConsultaNova = TipoConsulta.builder()
                .nome("Tipo Consulta Nova")
                .build();
        TipoConsulta tipoConsultaNovaRetorno = tipoConsultaRepo.save(tipoConsultaNova);

        //Ação
        Consulta consultaAtualizada = consultaService.atualizarTipoConsulta(tipoConsultaNovaRetorno, consultaRetorno);

        //Verificação
        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaAtualizada.getTipoConsulta().getTipoConsultaId(), tipoConsultaNovaRetorno.getTipoConsultaId());

        //Rollback
        consultaRepo.delete(consultaRetorno);
        animalRepo.delete(animalRetorno);
        veterinarioRepo.delete(veterinarioRetorno);
        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoAnimalRetorno);
        tipoConsultaRepo.delete(tipoConsultaRetorno);
        tipoConsultaRepo.delete(tipoConsultaNovaRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveBuscarPorId(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        TipoAnimal tipoAnimalRetorno =  tipoAnimalRepo.save(tipoAnimal);

        Animal animal =  Animal.builder(

                ).tipoAnimal(tipoAnimalRetorno)
                .dono(donoRetorno)
                .nome("Animal Teste")
                .build();
        Animal animalRetorno = animalRepo.save(animal);

        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especialidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinario Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("telefone teste")
                .especialidade(especialidadeRetorno)
                .build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        TipoConsulta tipoConsulta = TipoConsulta.builder()
                .nome("Tipo Consulta Teste")
                .build();
        TipoConsulta tipoConsultaRetorno = tipoConsultaRepo.save(tipoConsulta);

        Consulta consulta = Consulta.builder()
                .tipoConsulta(tipoConsultaRetorno)
                .data(LocalDate.now())
                .descricao("Descricao Teste")
                .animal(animalRetorno)
                .veterinario(veterinarioRetorno)
                .build();
        Consulta consultaRetorno = consultaService.salvar(consulta);

        //Ação
        Consulta consutaBuscada = consultaService.buscarComId(consultaRetorno.getConsultaId());

        //Verificação
        Assertions.assertNotNull(consutaBuscada);
        Assertions.assertEquals(consutaBuscada.getConsultaId(), consultaRetorno.getConsultaId());

        //Rollback
        consultaRepo.delete(consultaRetorno);
        animalRepo.delete(animalRetorno);
        veterinarioRepo.delete(veterinarioRetorno);
        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoAnimalRetorno);
        tipoConsultaRepo.delete(tipoConsultaRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveBuscarTodos(){
        //Cenário
        Dono dono = Dono.builder()
                .nome("Dono Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("Telefone Teste")
                .build();
        Dono donoRetorno = donoRepo.save(dono);

        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("Tipo Animal Teste")
                .build();
        TipoAnimal tipoAnimalRetorno =  tipoAnimalRepo.save(tipoAnimal);

        Animal animal =  Animal.builder(

                ).tipoAnimal(tipoAnimalRetorno)
                .dono(donoRetorno)
                .nome("Animal Teste")
                .build();
        Animal animalRetorno = animalRepo.save(animal);

        Area area = Area.builder()
                .nome("Area Teste")
                .build();
        Area areaRetorno = areaRepo.save(area);

        Especialidade especialidade = Especialidade.builder()
                .nome("Especialidade Teste")
                .area(areaRetorno)
                .build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);

        Veterinario veterinario = Veterinario.builder()
                .nome("Veterinario Teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("telefone teste")
                .especialidade(especialidadeRetorno)
                .build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);

        TipoConsulta tipoConsulta = TipoConsulta.builder()
                .nome("Tipo Consulta Teste")
                .build();
        TipoConsulta tipoConsultaRetorno = tipoConsultaRepo.save(tipoConsulta);

        Consulta consulta = Consulta.builder()
                .tipoConsulta(tipoConsultaRetorno)
                .data(LocalDate.now())
                .descricao("Descricao Teste")
                .animal(animalRetorno)
                .veterinario(veterinarioRetorno)
                .build();
        Consulta consultaRetorno = consultaService.salvar(consulta);

        //Ação
        List<Consulta> consultas = consultaService.buscarTodos();

        //Verificação
        Assertions.assertNotNull(consultas);
        Assertions.assertFalse(consultas.isEmpty());

        //Rollback
        consultaRepo.delete(consultaRetorno);
        animalRepo.delete(animalRetorno);
        veterinarioRepo.delete(veterinarioRetorno);
        animalRepo.delete(animalRetorno);
        tipoAnimalRepo.delete(tipoAnimalRetorno);
        tipoConsultaRepo.delete(tipoConsultaRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
        donoRepo.delete(donoRetorno);
    }

}
