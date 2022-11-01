package com.produtos.apirest.viewController;

import com.produtos.apirest.models.*;
import com.produtos.apirest.models.DTO.AppointmentDTO;
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
        AppointmentDTO dto = AppointmentDTO.builder()
                .appointmentTypes(tipo_consultaService.buscarTodos())
                .vets(veterinarioService.buscarTodos())
                .animals(animalService.buscarTodos())
                .build();
        mv.addObject("consultadto", dto);
        return mv;
    }

    @PreAuthorize("hasRole('S')")
    @PostMapping("/consulta/cadastro")
    public String consultaCadastro(AppointmentDTO dto){
        Appointment consulta = Appointment.builder()
                .veterinary(dto.getVeterinary())
                .animal(dto.getAnimal())
                .appointmentType(dto.getType())
                .description(dto.getDescription())
                .date(dto.getDate())
                .build();

        if(dto.getId() == null){
            consultaService.salvar(consulta);
        }else{
            consulta.setAppointmentId(dto.getId());
            consultaService.atualizar(consulta);
        }
        return "redirect:/consulta/consultaList";
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/consulta/consultaList")
    public ModelAndView consultaList(){
        List<Appointment> consultas = consultaService.buscarTodos();
        ModelAndView mv = new ModelAndView("/consulta/consultaList");
        mv.addObject("consultas", consultas);
        //System.out.println(mv);
        return mv;
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/consulta/atualizar/{id}")
    public ModelAndView consultaAtualizar(@PathVariable(value = "id") Long id){
        Appointment consulta = Appointment.builder().appointmentId(id).build();
        Appointment consultaFind = consultaService.buscarPorId(consulta.getAppointmentId());

        //Lista Veterinarios
        List<Veterinary> veterinarios = veterinarioService.buscarTodos();

        //Lista Animais
        List<Animal> animais = animalService.buscarTodos();

        //Lista Tipo Consulta
        List<AppointmentType> tipos = tipo_consultaService.buscarTodos();

        AppointmentDTO dto = AppointmentDTO.builder()
                .id(consultaFind.getAppointmentId())
                .type(consultaFind.getAppointmentType())
                .animal(consultaFind.getAnimal())
                .description(consultaFind.getDescription())
                .date(consultaFind.getDate())
                .veterinary(consultaFind.getVeterinary())
                .animals(animais)
                .appointmentTypes(tipos)
                .vets(veterinarios)
                .build();

        ModelAndView mv = new ModelAndView("/consulta/consultaCadastro");
        mv.addObject("consultadto", dto);
        return mv;
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/consulta/remover/{id}")
    public String consultaRemover(@PathVariable(value = "id", required = true) Long id){
        Appointment consulta = Appointment.builder().appointmentId(id).build();
        Appointment consultaFind = consultaService.buscarPorId(consulta.getAppointmentId());
        consultaService.remover(consultaFind);
        return "redirect:/consulta/consultaList";
    }
}
