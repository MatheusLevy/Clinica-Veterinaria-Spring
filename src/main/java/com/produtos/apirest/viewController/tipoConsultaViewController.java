package com.produtos.apirest.viewController;

import com.produtos.apirest.models.AppointmentType;
import com.produtos.apirest.service.AppointmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class tipoConsultaViewController {

    @Autowired
    private AppointmentTypeService tipoService;

    @PreAuthorize("hasRole('A')")
    @GetMapping("/tipoConsulta/cadastro")
    public ModelAndView tipoConsultaCadastro(){
        ModelAndView mv = new ModelAndView("tipoConsulta/tipoConsultaCadastro");
        AppointmentType tipoConsulta = AppointmentType.builder().build();
        mv.addObject("tipoConsulta", tipoConsulta);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @PostMapping("/tipoConsulta/cadastro")
    public String tipoConsultaCadastroControll(AppointmentType tipo){

        if(Long.valueOf(tipo.getAppointmentTypeId()) == null){
            tipoService.save(tipo);
        }else {
            tipoService.update(tipo);
        }
        return "redirect:/tipoConsulta/tipoConsultaList";
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/tipoConsulta/atualizar/{id}")
    public ModelAndView tipoConsultaAtualizar(@PathVariable(value = "id", required = true) Long id){

        AppointmentType tipoFind = tipoService.findById(id);
        ModelAndView mv = new ModelAndView("/tipoConsulta/tipoConsultaCadastro");
        mv.addObject("tipoConsulta", tipoFind);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/tipoConsulta/remover/{id}")
    public String tipoConsultaRemover(@PathVariable(value = "id", required = true) Long id){
        tipoService.removeById(id);
        return "redirect:/tipoConsulta/tipoConsultaList";
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/tipoConsulta/tipoConsultaList")
    public ModelAndView tipoConsultaList(){
        List<AppointmentType> tipos = tipoService.findAll();
        ModelAndView mv = new ModelAndView("/tipoConsulta/tipoConsultaList");
        mv.addObject("tipos", tipos);
        return mv;
    }

}
