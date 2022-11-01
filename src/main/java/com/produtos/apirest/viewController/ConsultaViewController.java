package com.produtos.apirest.viewController;

import com.produtos.apirest.models.*;
import com.produtos.apirest.models.DTO.ConsultaDTO;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.ConsultaService;
import com.produtos.apirest.service.TipoConsultaService;
import com.produtos.apirest.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ConsultaViewController {

    @Autowired
    public ConsultaService  consultaService;

    @Autowired
    public TipoConsultaService tipo_consultaService;

    @Autowired
    public AnimalService animalService;

    @Autowired
    public VeterinarioService veterinarioService;

    @PreAuthorize("hasRole('S')")
    @GetMapping("/consulta/cadastro")
    public ModelAndView consultaCadastro(){
        ModelAndView mv = new ModelAndView("consulta/consultaCadastro");
        ConsultaDTO dto = ConsultaDTO.builder()
                .tiposConsulta(tipo_consultaService.buscarTodos())
                .veterinarios(veterinarioService.buscarTodos())
                .animais(animalService.buscarTodos())
                .build();
        mv.addObject("consultadto", dto);
        return mv;
    }

    @PreAuthorize("hasRole('S')")
    @PostMapping("/consulta/cadastro")
    public String consultaCadastro(ConsultaDTO dto){
        Consulta consulta = Consulta.builder()
                .veterinary(dto.getVeterinario())
                .animal(dto.getAnimal())
                .appointmentType(dto.getTipo())
                .description(dto.getDescricao())
                .date(dto.getData())
                .build();

        if(dto.getId() == null){
            consultaService.salvar(consulta);
        }else{
            consulta.setConsultaId(dto.getId());
            consultaService.atualizar(consulta);
        }
        return "redirect:/consulta/consultaList";
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/consulta/consultaList")
    public ModelAndView consultaList(){
        List<Consulta> consultas = consultaService.buscarTodos();
        ModelAndView mv = new ModelAndView("/consulta/consultaList");
        mv.addObject("consultas", consultas);
        //System.out.println(mv);
        return mv;
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/consulta/atualizar/{id}")
    public ModelAndView consultaAtualizar(@PathVariable(value = "id") Long id){
        Consulta consulta = Consulta.builder().consultaId(id).build();
        Consulta consultaFind = consultaService.buscarPorId(consulta.getConsultaId());

        //Lista Veterinarios
        List<Veterinario> veterinarios = veterinarioService.buscarTodos();

        //Lista Animais
        List<Animal> animais = animalService.buscarTodos();

        //Lista Tipo Consulta
        List<TipoConsulta> tipos = tipo_consultaService.buscarTodos();

        ConsultaDTO dto = ConsultaDTO.builder()
                .id(consultaFind.getConsultaId())
                .tipo(consultaFind.getAppointmentType())
                .animal(consultaFind.getAnimal())
                .descricao(consultaFind.getDescription())
                .data(consultaFind.getDate())
                .veterinario(consultaFind.getVeterinary())
                .animais(animais)
                .tiposConsulta(tipos)
                .veterinarios(veterinarios)
                .build();

        ModelAndView mv = new ModelAndView("/consulta/consultaCadastro");
        mv.addObject("consultadto", dto);
        return mv;
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/consulta/remover/{id}")
    public String consultaRemover(@PathVariable(value = "id", required = true) Long id){
        Consulta consulta = Consulta.builder().consultaId(id).build();
        Consulta consultaFind = consultaService.buscarPorId(consulta.getConsultaId());
        consultaService.remover(consultaFind);
        return "redirect:/consulta/consultaList";
    }
}
