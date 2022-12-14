package com.produtos.apirest.viewController;

import com.produtos.apirest.models.DTO.ExpertiseDTO;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.service.AreaService;
import com.produtos.apirest.service.ExpertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class EspecialidadeViewController {

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private AreaService areaService;

    @PreAuthorize("hasRole('A')")
    @GetMapping("/especialidade/cadastro")
    public ModelAndView especialidadeCadastro(){
        ModelAndView mv = new ModelAndView("especialidade/especialidadeCadastro");
        ExpertiseDTO dto = new ExpertiseDTO();
        dto.setAreas(areaService.findAll());
        mv.addObject("especialidadedto", dto);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @PostMapping("/especialidade/cadastro")
    public String especialidadeCadastroControll(Expertise especialidade){

        if(Long.valueOf(especialidade.getExpertiseId()) == null){
            expertiseService.save(especialidade);
        }else {
            expertiseService.update(especialidade);
        }
        return "redirect:/especialidade/especialidadeList";
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/especialidade/especialidadeList")
    public ModelAndView especialidadeList(){
        List<Expertise> especialidades = expertiseService.findAll();
        ModelAndView mv = new ModelAndView("/especialidade/especialidadeList");
        mv.addObject("especialidades", especialidades);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/especialidade/atualizar/{id}")
    public ModelAndView especialidadeAtualizar(@PathVariable(value = "id", required = true) Long id){
        Expertise especialidade = Expertise.builder()
                .expertiseId(id).build();
        Expertise especialidadeFind = expertiseService.findById(especialidade.getExpertiseId());

        //EspecialidadeDTO
        ExpertiseDTO dto = ExpertiseDTO.builder()
                .id(especialidadeFind.getExpertiseId())
                .name(especialidadeFind.getName())
                .areas(areaService.findAll())
                .area(especialidadeFind.getArea())
                .build();

        ModelAndView mv = new ModelAndView("/especialidade/especialidadeCadastro");
        mv.addObject("especialidadedto", dto);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/especialidade/remover/{id}")
    public String especialidadeRemover(@PathVariable(value = "id", required = true) Long id){
        Expertise especialidade = Expertise.builder().expertiseId(id).build();
        Expertise especialidadeFind = expertiseService.findById(especialidade.getExpertiseId());
        expertiseService.remove(especialidadeFind);
        return "redirect:/especialidade/especialidadeList";
    }
}
