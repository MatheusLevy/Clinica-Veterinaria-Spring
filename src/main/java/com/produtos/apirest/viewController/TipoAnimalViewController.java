package com.produtos.apirest.viewController;


import com.produtos.apirest.models.AnimalType;
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
public class TipoAnimalViewController {

    @Autowired
    private TipoAnimalService tipoService;

    @PreAuthorize("hasRole('A')")
    @GetMapping("/tipoAnimal/cadastro")
    public ModelAndView tipoAnimalCadastro(){
        ModelAndView mv = new ModelAndView("tipoAnimal/tipoAnimalCadastro");
        AnimalType tipo = AnimalType.builder().build();
        mv.addObject("tipoAnimal", tipo);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @PostMapping("/tipoAnimal/cadastro")
    public String tipoAnimalCadastroControll(AnimalType tipo){

        if(Long.valueOf(tipo.getAnimalTypeId()) == null){
            tipoService.save(tipo);
        }else {
            tipoService.update(tipo);
        }
        return "redirect:/tipoAnimal/tipoAnimalList";
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/tipoAnimal/atualizar/{id}")
    public ModelAndView tipoAnimalAtualizar(@PathVariable(value = "id", required = true) Long id){
        AnimalType tipoFind = tipoService.findById(id);
        ModelAndView mv = new ModelAndView("/tipoAnimal/tipoAnimalCadastro");
        mv.addObject("tipoAnimal", tipoFind);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/tipoAnimal/remover/{id}")
    public String tipoAnimalRemover(@PathVariable(value = "id", required = true) Long id){
        AnimalType tipoFind = tipoService.findById(id);
        tipoService.removeById(tipoFind.getAnimalTypeId());
        return "redirect:/tipoAnimal/tipoAnimalList";
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/tipoAnimal/tipoAnimalList")
    public ModelAndView tipoAnimalList(){
        List<AnimalType> tipos = tipoService.findAll();
        ModelAndView mv = new ModelAndView("/tipoAnimal/tipoAnimalList");
        mv.addObject("tipos", tipos);
        return mv;
    }

}
