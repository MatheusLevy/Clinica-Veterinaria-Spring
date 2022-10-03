package com.produtos.apirest.viewController;

import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.DTO.VeterinarioDTO;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.models.Veterinario;
import com.produtos.apirest.service.EspecialidadeService;
import com.produtos.apirest.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class VeterinarioViewController {

    @Autowired
    public VeterinarioService veterinarioService;

    @Autowired
    public EspecialidadeService especialidadeService;

    @GetMapping("/veterinario/cadastro")
    public ModelAndView veterinarioCadastroPage(){
        VeterinarioDTO dto = new VeterinarioDTO();
        dto.setEspecialidades(especialidadeService.buscarTodos());
        ModelAndView mv = new ModelAndView("veterinario/veterinarioCadastro");
        mv.addObject("veterinarioDTO", dto);
        return mv;
    }

    @PostMapping("/veterinario/cadastro")
    public String veterinarioCadastro(VeterinarioDTO dto){
        Veterinario veterinario = Veterinario.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .telefone(dto.getTelefone())
                .especialidade(dto.getEspecialidade())
                .build();
        if(dto.getId() == null){
            veterinarioService.salvar(veterinario);
        }else{
            veterinarioService.atualizar(veterinario);

        }
        return "redirect:/veterinario/veterinarioList";
    }

    @GetMapping("/veterinario/veterinarioList")
    public ModelAndView veterinarioList(){
        List<Veterinario> veterinarios = veterinarioService.buscarTodos();
        ModelAndView mv = new ModelAndView("/veterinario/veterinarioList");
        mv.addObject("veterinarios", veterinarios);
        //System.out.println(mv);
        return mv;
    }


    @GetMapping("/veterinario/atualizar/{id}")
    public ModelAndView veterinarioAtualizar(@PathVariable(value = "id", required = true) Long id){
        //Veterinario
        Veterinario veterinario = Veterinario.builder().veterinarioId(id).build();
        Veterinario veterinarioBuscado = veterinarioService.buscarPorId(veterinario);

        //Especialidades
        List<Especialidade> especialidades = especialidadeService.buscarTodos();

        //DTO
        VeterinarioDTO veterinarioDTO = VeterinarioDTO.builder()
                .id(veterinarioBuscado.getVeterinarioId())
                .nome(veterinarioBuscado.getNome())
                .telefone(veterinarioBuscado.getTelefone())
                .cpf(veterinarioBuscado.getCpf())
                .especialidade(veterinarioBuscado.getEspecialidade())
                .especialidades(especialidades)
                .build();

        ModelAndView mv = new ModelAndView("veterinario/veterinarioCadastro");
        mv.addObject("veterinarioDTO", veterinarioDTO);
        return mv;
    }


    @GetMapping("/veterinario/remover/{id}")
    public String veterinarioRemover(@PathVariable(value = "id", required = true) Long id){
        Veterinario veterinario = Veterinario.builder().veterinarioId(id).build();
        Veterinario veterinarioBuscado = veterinarioService.buscarPorId(veterinario);
        veterinarioService.remover(veterinarioBuscado);
        return "redirect:/veterinario/veterinarioList";
    }
}
