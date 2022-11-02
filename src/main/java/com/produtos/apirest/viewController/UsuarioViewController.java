package com.produtos.apirest.viewController;

import com.produtos.apirest.models.User;
import com.produtos.apirest.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UsuarioViewController {

    @Autowired
    private UsuarioService usarioService;

    //TODO:
    @PreAuthorize("hasRole('A')")
    @GetMapping("/usuario/cadastro")
    public ModelAndView usuarioCadastro(){
        ModelAndView mv = new ModelAndView("usuarios/usuarioCadastro");
        User usuario = User.builder().build();
        mv.addObject("usuario", usuario);
        return mv;
    }


    //TODO: ### ** Verificar Criptografica da senha **
    @PreAuthorize("hasRole('A')")
    @PostMapping("/usuario/cadastro")
    public String usuarioCadastroControll(User usuario){

        if(usuario.getUserId() == null){
            usarioService.save(usuario);
        }else {
            usarioService.update(usuario);
        }
        return "redirect:/usuario/usuarioList";
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/usuario/atualizar/{id}")
    public ModelAndView usuarioAtualizar(@PathVariable(value = "id", required = true) Long id){
        User usuarioFind = usarioService.findById(id);
        ModelAndView mv = new ModelAndView("/usuarios/usuarioCadastro");
        mv.addObject("usuario", usuarioFind);
        return mv;
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/usuario/remover/{id}")
    public String usuarioRemover(@PathVariable(value = "id", required = true) Long id){
        User usuarioFind = usarioService.findById(id);
        usarioService.remove(usuarioFind);
        return "redirect:/usuario/usuarioList";
    }

    @PreAuthorize("hasRole('A')")
    @GetMapping("/usuario/usuarioList")
    public ModelAndView usuarioList(){
        List<User> usuarios = usarioService.findAll();
        ModelAndView mv = new ModelAndView("/usuarios/usuarioList");
        mv.addObject("usuarios", usuarios);
        return mv;
    }
}
