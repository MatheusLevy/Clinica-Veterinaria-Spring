package com.produtos.apirest.viewController;

import com.produtos.apirest.models.DTO.EspecialidadeDTO;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.service.Tipo_ConsultaService;
import com.produtos.apirest.service.Tipo_animalService;
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
    private Tipo_animalService tipoService;

    @PreAuthorize("hasRole('A')")
    @GetMapping("/tipoAnimal/cadastro")
    public ModelAndView tipoAnimalCadastro(){
        ModelAndView mv = new ModelAndView("tipoAnimal/tipoAnimalCadastro");
        TipoAnimal tipo = TipoAnimal.builder().build();
        mv.addObject("tipoAnimal", tipo);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @PostMapping("/tipoAnimal/cadastro")
    public String tipoAnimalCadastroControll(TipoAnimal tipo){

        if(Long.valueOf(tipo.getTipoAnimalId()) == null){
            tipoService.salvar(tipo);
        }else {
            tipoService.atualizar(tipo);
        }
        return "redirect:/tipoAnimal/tipoAnimalList";
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/tipoAnimal/atualizar/{id}")
    public ModelAndView tipoAnimalAtualizar(@PathVariable(value = "id", required = true) Long id){

        TipoAnimal tipo = TipoAnimal.builder().tipoAnimalId(id).build();
        TipoAnimal tipoFind = tipoService.buscarTipo_animalPorId(tipo);
        ModelAndView mv = new ModelAndView("/tipoAnimal/tipoAnimalCadastro");
        mv.addObject("tipoAnimal", tipoFind);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/tipoAnimal/remover/{id}")
    public String tipoAnimalRemover(@PathVariable(value = "id", required = true) Long id){
        TipoAnimal tipo = TipoAnimal.builder().tipoAnimalId(id).build();
        TipoAnimal tipoFind = tipoService.buscarTipo_animalPorId(tipo);
        tipoService.removerPorId(tipoFind);
        return "redirect:/tipoAnimal/tipoAnimalList";
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/tipoAnimal/tipoAnimalList")
    public ModelAndView tipoAnimalList(){
        List<TipoAnimal> tipos = tipoService.buscarTodos();
        ModelAndView mv = new ModelAndView("/tipoAnimal/tipoAnimalList");
        mv.addObject("tipos", tipos);
        return mv;
    }

}
