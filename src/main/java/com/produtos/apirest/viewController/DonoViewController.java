package com.produtos.apirest.viewController;

import com.produtos.apirest.models.Owner;
import com.produtos.apirest.service.DonoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DonoViewController {

    @Autowired
    public DonoService donoService;

    @PreAuthorize("hasRole('S')")
    @GetMapping("/dono/donoCadastro")
    public ModelAndView dono_cadastro_page(){
        ModelAndView mv = new ModelAndView("/dono/donoCadastro");
        mv.addObject("dono", new Owner());
        return mv;
    }

    @PreAuthorize("hasRole('S')")
    @PostMapping("/dono/donoCadastro")
    public String donoCadastroControll(Owner dono){

        if(Long.valueOf(dono.getOwnerId()) == null){
            donoService.salvar(dono);
        }else {
            donoService.atualizar(dono);
        }
        return "redirect:/dono/donoList";
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/dono/donoList")
    public ModelAndView donoList(){
        List<Owner> donos = donoService.buscarTodos();
        ModelAndView mv = new ModelAndView("/dono/donoList");
        mv.addObject( "donos", donos);
        //System.out.println(mv);
        return mv;
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/dono/atualizar/{id}")
    public ModelAndView donoAtualizar(@PathVariable(value = "id", required = true) Long id){
        Owner donoFind = donoService.buscarPorId(id);
        ModelAndView mv = new ModelAndView("/dono/donoCadastro");
        mv.addObject("dono", donoFind);
        return mv;
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/dono/remover/{id}")
    public String donoRemover(@PathVariable(value = "id", required = true) Long id){
        Owner donoFind = donoService.buscarPorId(id);
        donoService.removerPorId(donoFind.getOwnerId());
        return "redirect:/dono/donoList";
    }
}
