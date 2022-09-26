package com.produtos.apirest.viewController;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.DonoService;
import com.produtos.apirest.service.Tipo_animalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class AnimalViewController {

    @Autowired
    public AnimalService animalService;

    @Autowired
    public DonoService donoService;

    @Autowired
    public Tipo_animalService tipoAnimalService;

    @GetMapping("/animal/cadastro")
    public ModelAndView animalCadastroControl(){
        ModelAndView mv = new ModelAndView("/animal/animalCadastro");
        AnimalDTO dto = new AnimalDTO();
        dto.setDonos(donoService.buscarTodos());
        dto.setTipos(tipoAnimalService.buscarTodos());
        mv.addObject("animaldto", dto);
        return mv;
    }

    @PostMapping("/animal/cadastro")
    public String animalCadastro(AnimalDTO dto){
        Animal animal = Animal.builder().nome(dto.getNome()).dono(dto.getDono()).tipoAnimal(dto.getTipo()).build();
        if(dto.getId() == null){
            animalService.salvar(animal);
        }else{
            animal.setAnimalId(dto.getId());
            animalService.atualizar(animal);
        }
        return "redirect:/animal/animalList";
    }

    @GetMapping("/animal/animalList")
    public ModelAndView animalList(){
        List<Animal> animais = animalService.buscarTodos();
        ModelAndView mv = new ModelAndView("/animal/animalList");
        mv.addObject("animais", animais);
        System.out.println(mv);
        return mv;
    }

    @GetMapping("/animal/atualizar/{id}")
    public ModelAndView animalAtualizar(@PathVariable(value = "id", required = true) Long id){
        Animal animal = Animal.builder().animalId(id).build();
        Animal animalFind = animalService.buscarPorId(animal);
        //Tipo Animal List
        List<TipoAnimal> tipoAnimal = tipoAnimalService.buscarTodos();
        tipoAnimal.add(0, tipoAnimal.remove(tipoAnimal.indexOf(animalFind.getTipoAnimal())));

        //Dono List
        List<Dono> donos = donoService.buscarTodos();
        donos.add(0, donos.remove(donos.indexOf(animalFind.getDono())));

        AnimalDTO dto = AnimalDTO.builder().id(animalFind.getAnimalId()).
                nome(animalFind.getNome())
                .tipos(tipoAnimal)
                .donos(donos)
                .build();
        ModelAndView mv = new ModelAndView("/animal/animalCadastro");
        mv.addObject("animaldto", dto);
        return mv;
    }

    @GetMapping("/animal/remover/{id}")
    public String animalRemover(@PathVariable(value = "id", required = true) Long id){
        Animal animal = Animal.builder().animalId(id).build();
        Animal animalFind = animalService.buscarPorId(animal);
        animalService.removerPorId(animalFind);
        return "redirect:/animal/animalList";
    }

}
