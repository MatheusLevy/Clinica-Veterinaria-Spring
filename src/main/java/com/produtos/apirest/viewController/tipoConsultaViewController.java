package com.produtos.apirest.viewController;

import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.service.Tipo_ConsultaService;
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
    private Tipo_ConsultaService tipoService;

    @PreAuthorize("hasRole('A')")
    @GetMapping("/tipoConsulta/cadastro")
    public ModelAndView tipoConsultaCadastro(){
        ModelAndView mv = new ModelAndView("tipoConsulta/tipoConsultaCadastro");
        TipoConsulta tipoConsulta = TipoConsulta.builder().build();
        mv.addObject("tipoConsulta", tipoConsulta);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @PostMapping("/tipoConsulta/cadastro")
    public String tipoConsultaCadastroControll(TipoConsulta tipo){

        if(Long.valueOf(tipo.getTipoConsultaId()) == null){
            tipoService.salvar(tipo);
        }else {
            tipoService.atualizar(tipo);
        }
        return "redirect:/tipoConsulta/tipoConsultaList";
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/tipoConsulta/atualizar/{id}")
    public ModelAndView tipoConsultaAtualizar(@PathVariable(value = "id", required = true) Long id){

        TipoConsulta tipo = TipoConsulta.builder().tipoConsultaId(id).build();
        TipoConsulta tipoFind = tipoService.buscarTipo_consultaPorId(tipo);
        ModelAndView mv = new ModelAndView("/tipoConsulta/tipoConsultaCadastro");
        mv.addObject("tipoConsulta", tipoFind);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/tipoConsulta/remover/{id}")
    public String tipoConsultaRemover(@PathVariable(value = "id", required = true) Long id){
        TipoConsulta tipo = TipoConsulta.builder().tipoConsultaId(id).build();
        TipoConsulta tipoFind = tipoService.buscarTipo_consultaPorId(tipo);
        tipoService.remover(tipoFind);
        return "redirect:/tipoConsulta/tipoConsultaList";
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/tipoConsulta/tipoConsultaList")
    public ModelAndView tipoConsultaList(){
        List<TipoConsulta> tipos = tipoService.buscarTodos();
        ModelAndView mv = new ModelAndView("/tipoConsulta/tipoConsultaList");
        mv.addObject("tipos", tipos);
        return mv;
    }

}
