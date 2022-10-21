package com.produtos.apirest.viewController;

import com.produtos.apirest.models.DTO.EspecialidadeDTO;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.service.AreaService;
import com.produtos.apirest.service.EspecialidadeService;
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
    private EspecialidadeService especialidadeService;

    @Autowired
    private AreaService areaService;

    @PreAuthorize("hasRole('A')")
    @GetMapping("/especialidade/cadastro")
    public ModelAndView especialidadeCadastro(){
        ModelAndView mv = new ModelAndView("especialidade/especialidadeCadastro");
        EspecialidadeDTO dto = new EspecialidadeDTO();
        dto.setAreas(areaService.buscarTodos());
        mv.addObject("especialidadedto", dto);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @PostMapping("/especialidade/cadastro")
    public String especialidadeCadastroControll(Especialidade especialidade){

        if(Long.valueOf(especialidade.getEspecialidadeId()) == null){
            especialidadeService.salvar(especialidade);
        }else {
            especialidadeService.atualizar(especialidade);
        }
        return "redirect:/especialidade/especialidadeList";
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/especialidade/especialidadeList")
    public ModelAndView especialidadeList(){
        List<Especialidade> especialidades = especialidadeService.buscarTodos();
        ModelAndView mv = new ModelAndView("/especialidade/especialidadeList");
        mv.addObject("especialidades", especialidades);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/especialidade/atualizar/{id}")
    public ModelAndView especialidadeAtualizar(@PathVariable(value = "id", required = true) Long id){
        Especialidade especialidade = Especialidade.builder().especialidadeId(id).build();
        Especialidade especialidadeFind = especialidadeService.buscarPorId(especialidade.getEspecialidadeId());

        //EspecialidadeDTO
        EspecialidadeDTO dto = EspecialidadeDTO.builder()
                .id(especialidadeFind.getEspecialidadeId())
                .nome(especialidadeFind.getNome())
                .areas(areaService.buscarTodos())
                .area(especialidadeFind.getArea())
                .build();

        ModelAndView mv = new ModelAndView("/especialidade/especialidadeCadastro");
        mv.addObject("especialidadedto", dto);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/especialidade/remover/{id}")
    public String especialidadeRemover(@PathVariable(value = "id", required = true) Long id){
        Especialidade especialidade = Especialidade.builder().especialidadeId(id).build();
        Especialidade especialidadeFind = especialidadeService.buscarPorId(especialidade.getEspecialidadeId());
        especialidadeService.remover(especialidadeFind);
        return "redirect:/especialidade/especialidadeList";
    }
}
