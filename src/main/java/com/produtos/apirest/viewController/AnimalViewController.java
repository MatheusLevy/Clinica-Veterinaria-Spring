package com.produtos.apirest.viewController;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.AnimalType;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.Owner;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.OwnerService;
import com.produtos.apirest.service.AnimalTypeService;
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
    public OwnerService ownerService;

    @Autowired
    public AnimalTypeService animalTypeService;

    @PreAuthorize("hasRole('S')")
    @GetMapping("/animal/cadastro")
    public ModelAndView animalCadastroControl(){
        ModelAndView mv = new ModelAndView("/animal/animalCadastro");
        AnimalDTO dto = new AnimalDTO();
        dto.setOwners(ownerService.findAll());
        dto.setTypes(animalTypeService.findAll());
        mv.addObject("animaldto", dto);
        return mv;
    }

    @PreAuthorize("hasRole('S')")
    @PostMapping("/animal/cadastro")
    public String animalCadastro(AnimalDTO dto){
        Animal animal = Animal.builder()
                .name(dto.getName())
                .owner(dto.getOwner())
                .animalType(dto.getType()).build();
        if(dto.getId() == null){
            animalService.save(animal);
        }else{
            animal.setAnimalId(dto.getId());
            animalService.update(animal);
        }
        return "redirect:/animal/animalList";
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/animal/animalList")
    public ModelAndView animalList(){
        List<Animal> animais = animalService.findAll();
        ModelAndView mv = new ModelAndView("/animal/animalList");
        mv.addObject("animais", animais);
        System.out.println(mv);
        return mv;
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/animal/atualizar/{id}")
    public ModelAndView animalAtualizar(@PathVariable(value = "id", required = true) Long id){
        Animal animalFind = animalService.findById(id);

        //Tipo Animal List
        List<AnimalType> tipoAnimal = animalTypeService.findAll();

        //Dono List
        List<Owner> donos = ownerService.findAll();

        //AnimalDTO
        AnimalDTO dto = AnimalDTO.builder().id(animalFind.getAnimalId()).
                name(animalFind.getName())
                .type(animalFind.getAnimalType())
                .types(tipoAnimal)
                .owner(animalFind.getOwner())
                .owners(donos)
                .build();

        ModelAndView mv = new ModelAndView("/animal/animalCadastro");
        mv.addObject("animaldto", dto);
        return mv;
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/animal/remover/{id}")
    public String animalRemover(@PathVariable(value = "id", required = true) Long id){
        Animal animalFind = animalService.findById(id);
        animalService.removeById(animalFind.getAnimalId());
        return "redirect:/animal/animalList";
    }

}
