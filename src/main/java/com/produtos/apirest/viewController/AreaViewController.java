package com.produtos.apirest.viewController;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.DTO.AreaDTO;
import com.produtos.apirest.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AreaViewController {

    @Autowired
    private AreaService areaService;

    @PreAuthorize("hasRole('A')")
    @GetMapping("/area/cadastro")
    public ModelAndView areaCadastroControl(){
        ModelAndView mv = new ModelAndView("/area/areaCadastro");
        AreaDTO dto = new AreaDTO();
        dto.setAreas(areaService.buscarTodos());
        mv.addObject("areadto", dto);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @PostMapping("/area/cadastro")
    public String areaCadastro(AreaDTO dto){
        Area area = Area.builder().name(dto.getName()).build();
        if(dto.getId() == null){
            areaService.salvar(area);
        }else{
            area.setAreaId(dto.getId());
            areaService.atualizar(area);
        }
        return "redirect:/area/areaList";
    }
    @PreAuthorize("hasRole('A')")
    @GetMapping("/area/areaList")
    public ModelAndView areaList(){
        List<Area> areas = areaService.buscarTodos();
        ModelAndView mv = new ModelAndView("/area/areaList");
        mv.addObject("areas", areas);
        System.out.println(mv);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/area/atualizar/{id}")
    public ModelAndView animalAtualizar(@PathVariable(value = "id", required = true) Long id){
        Area areaFind = areaService.buscarPorId(id);

        //AreaDTO
        AreaDTO dto = AreaDTO.builder()
                .id(areaFind.getAreaId())
                .name(areaFind.getName())
                .build();
        ModelAndView mv = new ModelAndView("/area/areaCadastro");
        mv.addObject("areadto", dto);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/area/remover/{id}")
    public String areaRemover(@PathVariable(value = "id", required = true) Long id){
        areaService.removerPorId(id);
        return "redirect:/area/areaList";
    }

}
