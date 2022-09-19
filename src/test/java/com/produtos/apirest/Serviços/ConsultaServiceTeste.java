package com.produtos.apirest.Serviços;

import com.produtos.apirest.models.*;
import com.produtos.apirest.repository.*;
import com.produtos.apirest.service.ConsultaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class ConsultaServiceTeste {

    @Autowired
    public ConsultaService consultaService;

    @Autowired
    public ConsultaRepo consultaRepo;

    @Autowired
    public DonoRepo donoRepo;

    @Autowired
    public TipoAnimalRepo tipo_animalRepo;

    @Autowired
    public AnimalRepo animalRepo;

    @Autowired
    public AreaRepo areaRepo;

    @Autowired
    public EspecialidadeRepo especialidadeRepo;

    @Autowired
    public VeterinarioRepo veterinarioRepo;

    @Autowired
    public TipoConsultaRepo tipo_consultaRepo;

    @Test
    public void deveSalvar(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF Teste").telefone("Telefone Teste").build();
        Dono donoRetorno = donoRepo.save(dono);
        TipoAnimal tipo_animal = TipoAnimal.builder().nome("Tipo Animal Teste").build();
        TipoAnimal tipo_animalRetorno =  tipo_animalRepo.save(tipo_animal);
        Animal animal =  Animal.builder().tipoAnimal(tipo_animalRetorno).dono(donoRetorno).nome("Animal Teste").build();
        Animal animalRetorno = animalRepo.save(animal);
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especialidade Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        Veterinario veterinario = Veterinario.builder().nome("Veterinario Teste").cpf("cpf teste").telefone("telefone teste").especialidade(especialidadeRetorno).build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);
        TipoConsulta tipo_consulta = TipoConsulta.builder().nome("Tipo Consulta Teste").build();
        TipoConsulta tipo_consultaRetorno = tipo_consultaRepo.save(tipo_consulta);

        Consulta consulta = Consulta.builder().tipoConsulta(tipo_consultaRetorno).data(LocalDate.now()).descricao("Descricao Teste").animal(animalRetorno).veterinario(veterinarioRetorno).build();
        Consulta consultaRetorno = consultaService.salvar(consulta);

        Assertions.assertNotNull(consultaRetorno);
        Assertions.assertNotNull(consultaRetorno.getConsultaId());

        consultaRepo.delete(consultaRetorno);
        animalRepo.delete(animalRetorno);
        veterinarioRepo.delete(veterinarioRetorno);
        animalRepo.delete(animalRetorno);
        tipo_animalRepo.delete(tipo_animalRetorno);
        tipo_consultaRepo.delete(tipo_consultaRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveAtualizar(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF Teste").telefone("Telefone Teste").build();
        Dono donoRetorno = donoRepo.save(dono);
        TipoAnimal tipo_animal = TipoAnimal.builder().nome("Tipo Animal Teste").build();
        TipoAnimal tipo_animalRetorno =  tipo_animalRepo.save(tipo_animal);
        Animal animal =  Animal.builder().tipoAnimal(tipo_animalRetorno).dono(donoRetorno).nome("Animal Teste").build();
        Animal animalRetorno = animalRepo.save(animal);
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especialidade Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        Veterinario veterinario = Veterinario.builder().nome("Veterinario Teste").cpf("cpf teste").telefone("telefone teste").especialidade(especialidadeRetorno).build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);
        TipoConsulta tipo_consulta = TipoConsulta.builder().nome("Tipo Consulta Teste").build();
        TipoConsulta tipo_consultaRetorno = tipo_consultaRepo.save(tipo_consulta);
        Consulta consulta = Consulta.builder().tipoConsulta(tipo_consultaRetorno).data(LocalDate.now()).descricao("Descricao Teste").animal(animalRetorno).veterinario(veterinarioRetorno).build();
        Consulta consultaRetorno = consultaRepo.save(consulta);

        consultaRetorno.setDescricao("Nova Descrição");

        Consulta consultaAtualizada = consultaService.atualizar(consultaRetorno);

        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaRetorno.getConsultaId(), consultaAtualizada.getConsultaId());

        consultaRepo.delete(consultaRetorno);
        animalRepo.delete(animalRetorno);
        veterinarioRepo.delete(veterinarioRetorno);
        animalRepo.delete(animalRetorno);
        tipo_animalRepo.delete(tipo_animalRetorno);
        tipo_consultaRepo.delete(tipo_consultaRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveRemover(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF Teste").telefone("Telefone Teste").build();
        Dono donoRetorno = donoRepo.save(dono);
        TipoAnimal tipo_animal = TipoAnimal.builder().nome("Tipo Animal Teste").build();
        TipoAnimal tipo_animalRetorno =  tipo_animalRepo.save(tipo_animal);
        Animal animal =  Animal.builder().tipoAnimal(tipo_animalRetorno).dono(donoRetorno).nome("Animal Teste").build();
        Animal animalRetorno = animalRepo.save(animal);
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especialidade Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        Veterinario veterinario = Veterinario.builder().nome("Veterinario Teste").cpf("cpf teste").telefone("telefone teste").especialidade(especialidadeRetorno).build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);
        TipoConsulta tipo_consulta = TipoConsulta.builder().nome("Tipo Consulta Teste").build();
        TipoConsulta tipo_consultaRetorno = tipo_consultaRepo.save(tipo_consulta);
        Consulta consulta = Consulta.builder().tipoConsulta(tipo_consultaRetorno).data(LocalDate.now()).descricao("Descricao Teste").animal(animalRetorno).veterinario(veterinarioRetorno).build();
        Consulta consultaRetorno = consultaRepo.save(consulta);

        consultaService.remover(consultaRetorno);

        Optional<Consulta> consultaTemp = consultaRepo.findById(consultaRetorno.getConsultaId());
        Assertions.assertFalse(consultaTemp.isPresent());

        consultaRepo.delete(consultaRetorno);
        animalRepo.delete(animalRetorno);
        veterinarioRepo.delete(veterinarioRetorno);
        animalRepo.delete(animalRetorno);
        tipo_animalRepo.delete(tipo_animalRetorno);
        tipo_consultaRepo.delete(tipo_consultaRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveRemoverComFeedback(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF Teste").telefone("Telefone Teste").build();
        Dono donoRetorno = donoRepo.save(dono);
        TipoAnimal tipo_animal = TipoAnimal.builder().nome("Tipo Animal Teste").build();
        TipoAnimal tipo_animalRetorno =  tipo_animalRepo.save(tipo_animal);
        Animal animal =  Animal.builder().tipoAnimal(tipo_animalRetorno).dono(donoRetorno).nome("Animal Teste").build();
        Animal animalRetorno = animalRepo.save(animal);
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especialidade Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        Veterinario veterinario = Veterinario.builder().nome("Veterinario Teste").cpf("cpf teste").telefone("telefone teste").especialidade(especialidadeRetorno).build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);
        TipoConsulta tipo_consulta = TipoConsulta.builder().nome("Tipo Consulta Teste").build();
        TipoConsulta tipo_consultaRetorno = tipo_consultaRepo.save(tipo_consulta);
        Consulta consulta = Consulta.builder().tipoConsulta(tipo_consultaRetorno).data(LocalDate.now()).descricao("Descricao Teste").animal(animalRetorno).veterinario(veterinarioRetorno).build();
        Consulta consultaRetorno = consultaRepo.save(consulta);

        Consulta consultaRemovida = consultaService.removerFeedback(consultaRetorno);

        Assertions.assertNotNull(consultaRemovida);
        Assertions.assertEquals(consultaRemovida.getConsultaId(), consultaRetorno.getConsultaId());

        consultaRepo.delete(consultaRetorno);
        animalRepo.delete(animalRetorno);
        veterinarioRepo.delete(veterinarioRetorno);
        animalRepo.delete(animalRetorno);
        tipo_animalRepo.delete(tipo_animalRetorno);
        tipo_consultaRepo.delete(tipo_consultaRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveAtualizarVeterinario(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF Teste").telefone("Telefone Teste").build();
        Dono donoRetorno = donoRepo.save(dono);
        TipoAnimal tipo_animal = TipoAnimal.builder().nome("Tipo Animal Teste").build();
        TipoAnimal tipo_animalRetorno =  tipo_animalRepo.save(tipo_animal);
        Animal animal =  Animal.builder().tipoAnimal(tipo_animalRetorno).dono(donoRetorno).nome("Animal Teste").build();
        Animal animalRetorno = animalRepo.save(animal);
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especialidade Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        Veterinario veterinario = Veterinario.builder().nome("Veterinario Teste").cpf("cpf teste").telefone("telefone teste").especialidade(especialidadeRetorno).build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);
        TipoConsulta tipo_consulta = TipoConsulta.builder().nome("Tipo Consulta Teste").build();
        TipoConsulta tipo_consultaRetorno = tipo_consultaRepo.save(tipo_consulta);
        Consulta consulta = Consulta.builder().tipoConsulta(tipo_consultaRetorno).data(LocalDate.now()).descricao("Descricao Teste").animal(animalRetorno).veterinario(veterinarioRetorno).build();
        Consulta consultaRetorno = consultaRepo.save(consulta);

        Veterinario veterinarioNovo = Veterinario.builder().nome("Veterinario Novo").cpf("cpf teste").telefone("telefone teste").especialidade(especialidadeRetorno).build();
        Veterinario veterinarioNovoRetorno = veterinarioRepo.save(veterinarioNovo);
        Consulta consultaAtualizada = consultaService.atualizarVeterinario(veterinarioNovoRetorno, consultaRetorno);

        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaAtualizada.getVeterinario().getVeterinarioId(), veterinarioNovoRetorno.getVeterinarioId());

        consultaRepo.delete(consultaRetorno);
        animalRepo.delete(animalRetorno);
        veterinarioRepo.delete(veterinarioRetorno);
        veterinarioRepo.delete(veterinarioNovoRetorno);
        animalRepo.delete(animalRetorno);
        tipo_animalRepo.delete(tipo_animalRetorno);
        tipo_consultaRepo.delete(tipo_consultaRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveAtualizarAnimal(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF Teste").telefone("Telefone Teste").build();
        Dono donoRetorno = donoRepo.save(dono);
        TipoAnimal tipo_animal = TipoAnimal.builder().nome("Tipo Animal Teste").build();
        TipoAnimal tipo_animalRetorno =  tipo_animalRepo.save(tipo_animal);
        Animal animal =  Animal.builder().tipoAnimal(tipo_animalRetorno).dono(donoRetorno).nome("Animal Teste").build();
        Animal animalRetorno = animalRepo.save(animal);
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especialidade Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        Veterinario veterinario = Veterinario.builder().nome("Veterinario Teste").cpf("cpf teste").telefone("telefone teste").especialidade(especialidadeRetorno).build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);
        TipoConsulta tipo_consulta = TipoConsulta.builder().nome("Tipo Consulta Teste").build();
        TipoConsulta tipo_consultaRetorno = tipo_consultaRepo.save(tipo_consulta);
        Consulta consulta = Consulta.builder().tipoConsulta(tipo_consultaRetorno).data(LocalDate.now()).descricao("Descricao Teste").animal(animalRetorno).veterinario(veterinarioRetorno).build();
        Consulta consultaRetorno = consultaRepo.save(consulta);

        Animal animalNovo = Animal.builder().tipoAnimal(tipo_animalRetorno).dono(donoRetorno).nome("Animal Novo").build();
        Animal animalNovoRetorno = animalRepo.save(animalNovo);
        Consulta consultaAtualizada = consultaService.atualizarAnimal(animalNovoRetorno, consultaRetorno);

        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaAtualizada.getAnimal().getAnimalId(), animalNovoRetorno.getAnimalId());

        consultaRepo.delete(consultaRetorno);
        animalRepo.delete(animalRetorno);
        animalRepo.delete(animalNovoRetorno);
        veterinarioRepo.delete(veterinarioRetorno);
        animalRepo.delete(animalRetorno);
        tipo_animalRepo.delete(tipo_animalRetorno);
        tipo_consultaRepo.delete(tipo_consultaRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
        donoRepo.delete(donoRetorno);
    }

    @Test
    public void deveAtualizarTipoConsulta(){
        Dono dono = Dono.builder().nome("Dono Teste").cpf("CPF Teste").telefone("Telefone Teste").build();
        Dono donoRetorno = donoRepo.save(dono);
        TipoAnimal tipo_animal = TipoAnimal.builder().nome("Tipo Animal Teste").build();
        TipoAnimal tipo_animalRetorno =  tipo_animalRepo.save(tipo_animal);
        Animal animal =  Animal.builder().tipoAnimal(tipo_animalRetorno).dono(donoRetorno).nome("Animal Teste").build();
        Animal animalRetorno = animalRepo.save(animal);
        Area area = Area.builder().nome("Area Teste").build();
        Area areaRetorno = areaRepo.save(area);
        Especialidade especialidade = Especialidade.builder().nome("Especialidade Teste").area(areaRetorno).build();
        Especialidade especialidadeRetorno = especialidadeRepo.save(especialidade);
        Veterinario veterinario = Veterinario.builder().nome("Veterinario Teste").cpf("cpf teste").telefone("telefone teste").especialidade(especialidadeRetorno).build();
        Veterinario veterinarioRetorno = veterinarioRepo.save(veterinario);
        TipoConsulta tipo_consulta = TipoConsulta.builder().nome("Tipo Consulta Teste").build();
        TipoConsulta tipo_consultaRetorno = tipo_consultaRepo.save(tipo_consulta);
        Consulta consulta = Consulta.builder().tipoConsulta(tipo_consultaRetorno).data(LocalDate.now()).descricao("Descricao Teste").animal(animalRetorno).veterinario(veterinarioRetorno).build();
        Consulta consultaRetorno = consultaRepo.save(consulta);

        TipoConsulta tipoConsultaNova = TipoConsulta.builder().nome("Tipo Consulta Nova").build();
        TipoConsulta tipoConsultaNovaRetorno = tipo_consultaRepo.save(tipoConsultaNova);
        Consulta consultaAtualizada = consultaService.atualizarTipoConsulta(tipoConsultaNovaRetorno, consultaRetorno);

        Assertions.assertNotNull(consultaAtualizada);
        Assertions.assertEquals(consultaAtualizada.getTipoConsulta().getTipoConsultaId(), tipoConsultaNovaRetorno.getTipoConsultaId());

        consultaRepo.delete(consultaRetorno);
        animalRepo.delete(animalRetorno);
        veterinarioRepo.delete(veterinarioRetorno);
        animalRepo.delete(animalRetorno);
        tipo_animalRepo.delete(tipo_animalRetorno);
        tipo_consultaRepo.delete(tipo_consultaRetorno);
        tipo_consultaRepo.delete(tipoConsultaNovaRetorno);
        especialidadeRepo.delete(especialidadeRetorno);
        areaRepo.delete(areaRetorno);
        donoRepo.delete(donoRetorno);
    }
}
