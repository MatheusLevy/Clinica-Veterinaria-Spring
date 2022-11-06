package com.produtos.apirest.viewController;

import com.produtos.apirest.models.DTO.VeterinaryDTO;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.service.ExpertiseService;
import com.produtos.apirest.service.VeterinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class VeterinarioViewController {

    @Autowired
    public VeterinaryService veterinaryService;

    @Autowired
    public ExpertiseService expertiseService;

    @PreAuthorize("hasRole('S')")
    @GetMapping("/veterinario/cadastro")
    public ModelAndView veterinarioCadastroPage(){
        VeterinaryDTO dto = new VeterinaryDTO();
        dto.setExpertises(expertiseService.findAll());
        ModelAndView mv = new ModelAndView("veterinario/veterinarioCadastro");
        mv.addObject("veterinarioDTO", dto);
        return mv;
    }

    @PreAuthorize("hasRole('S')")
    @PostMapping("/veterinario/cadastro")
    public String veterinarioCadastro(VeterinaryDTO dto){
        Veterinary veterinario = Veterinary.builder()
                .name(dto.getName())
                .cpf(dto.getCpf())
                .phone(dto.getPhone())
                .expertise(dto.getExpertise())
                .build();
        if(dto.getId() == null){
            veterinaryService.save(veterinario);
        }else{
            veterinario.setVeterinaryId(dto.getId());
            veterinaryService.update(veterinario);

        }
        return "redirect:/veterinario/veterinarioList";
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/veterinario/veterinarioList")
    public ModelAndView veterinarioList(){
        List<Veterinary> veterinarios = veterinaryService.findAll();
        ModelAndView mv = new ModelAndView("/veterinario/veterinarioList");
        mv.addObject("veterinarios", veterinarios);
        //System.out.println(mv);
        return mv;
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/veterinario/atualizar/{id}")
    public ModelAndView veterinarioAtualizar(@PathVariable(value = "id", required = true) Long id){
        //Veterinario
        Veterinary veterinarioBuscado = veterinaryService.findById(id);

        //Especialidades
        List<Expertise> especialidades = expertiseService.findAll();

        //DTO
        VeterinaryDTO veterinarioDTO = VeterinaryDTO.builder()
                .id(veterinarioBuscado.getVeterinaryId())
                .name(veterinarioBuscado.getName())
                .phone(veterinarioBuscado.getPhone())
                .cpf(veterinarioBuscado.getCpf())
                .expertise(veterinarioBuscado.getExpertise())
                .expertises(especialidades)
                .build();

        ModelAndView mv = new ModelAndView("veterinario/veterinarioCadastro");
        mv.addObject("veterinarioDTO", veterinarioDTO);
        return mv;
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/veterinario/remover/{id}")
    public String veterinarioRemover(@PathVariable(value = "id", required = true) Long id){;
        veterinaryService.removeById(id);
        return "redirect:/veterinario/veterinarioList";
    }
}
