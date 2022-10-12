package com.produtos.apirest.viewController;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.DonoService;
import com.produtos.apirest.service.TipoAnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AnimalViewController {

    @Autowired
    public AnimalService animalService;

    @Autowired
    public DonoService donoService;

    @Autowired
    public TipoAnimalService tipoAnimalService;

    @PreAuthorize("hasRole('S')")
    @GetMapping("/animal/cadastro")
    public ModelAndView animalCadastroControl(){
        ModelAndView mv = new ModelAndView("/animal/animalCadastro");
        AnimalDTO dto = new AnimalDTO();
        dto.setDonos(donoService.buscarTodos());
        dto.setTipos(tipoAnimalService.buscarTodos());
        mv.addObject("animaldto", dto);
        return mv;
    }

    @PreAuthorize("hasRole('S')")
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

    @PreAuthorize("hasRole('S')")
    @GetMapping("/animal/animalList")
    public ModelAndView animalList(){
        List<Animal> animais = animalService.buscarTodos();
        ModelAndView mv = new ModelAndView("/animal/animalList");
        mv.addObject("animais", animais);
        System.out.println(mv);
        return mv;
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/animal/atualizar/{id}")
    public ModelAndView animalAtualizar(@PathVariable(value = "id", required = true) Long id){
        Animal animalFind = animalService.buscarPorId(id);

        //Tipo Animal List
        List<TipoAnimal> tipoAnimal = tipoAnimalService.buscarTodos();

        //Dono List
        List<Dono> donos = donoService.buscarTodos();

        //AnimalDTO
        AnimalDTO dto = AnimalDTO.builder().id(animalFind.getAnimalId()).
                nome(animalFind.getNome())
                .tipo(animalFind.getTipoAnimal())
                .tipos(tipoAnimal)
                .dono(animalFind.getDono())
                .donos(donos)
                .build();

        ModelAndView mv = new ModelAndView("/animal/animalCadastro");
        mv.addObject("animaldto", dto);
        return mv;
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/animal/remover/{id}")
    public String animalRemover(@PathVariable(value = "id", required = true) Long id){
        Animal animalFind = animalService.buscarPorId(id);
        animalService.removerPorId(animalFind.getAnimalId());
        return "redirect:/animal/animalList";
    }

}
