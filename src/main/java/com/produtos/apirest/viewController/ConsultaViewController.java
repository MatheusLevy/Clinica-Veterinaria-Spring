package com.produtos.apirest.viewController;

import com.produtos.apirest.models.*;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.DTO.ConsultaDTO;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.ConsultaService;
import com.produtos.apirest.service.Tipo_ConsultaService;
import com.produtos.apirest.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Tipo_ConsultaService tipo_consultaService;

    @Autowired
    public AnimalService animalService;

    @Autowired
    public VeterinarioService veterinarioService;

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

    @PostMapping("/consulta/cadastro")
    public String consultaCadastro(ConsultaDTO dto){
        Consulta consulta = Consulta.builder()
                .veterinario(dto.getVeterinario())
                .animal(dto.getAnimal())
                .tipoConsulta(dto.getTipo())
                .descricao(dto.getDescricao())
                .data(dto.getData())
                .build();

        if(dto.getId() == null){
            consultaService.salvar(consulta);
        }else{
            consulta.setConsultaId(dto.getId());
            consultaService.atualizar(consulta);
        }
        return "redirect:/consulta/consultaList";
    }

    @GetMapping("/consulta/consultaList")
    public ModelAndView consultaList(){
        List<Consulta> consultas = consultaService.buscarTodos();
        ModelAndView mv = new ModelAndView("/consulta/consultaList");
        mv.addObject("consultas", consultas);
        //System.out.println(mv);
        return mv;
    }

    @GetMapping("/consulta/atualizar/{id}")
    public ModelAndView consultaAtualizar(@PathVariable(value = "id") Long id){
        Consulta consulta = Consulta.builder().consultaId(id).build();
        Consulta consultaFind = consultaService.buscarComId(consulta);

        //Lista Veterinarios
        List<Veterinario> veterinarios = veterinarioService.buscarTodos();

        //Lista Animais
        List<Animal> animais = animalService.buscarTodos();

        //Lista Tipo Consulta
        List<TipoConsulta> tipos = tipo_consultaService.buscarTodos();

        ConsultaDTO dto = ConsultaDTO.builder()
                .id(consultaFind.getConsultaId())
                .tipo(consultaFind.getTipoConsulta())
                .animal(consultaFind.getAnimal())
                .descricao(consultaFind.getDescricao())
                .data(consultaFind.getData())
                .veterinario(consultaFind.getVeterinario())
                .animais(animais)
                .tiposConsulta(tipos)
                .veterinarios(veterinarios)
                .build();

        ModelAndView mv = new ModelAndView("/consulta/consultaCadastro");
        mv.addObject("consultadto", dto);
        return mv;
    }

    @GetMapping("/consulta/remover/{id}")
    public String consultaRemover(@PathVariable(value = "id", required = true) Long id){
        Consulta consulta = Consulta.builder().consultaId(id).build();
        Consulta consultaFind = consultaService.buscarComId(consulta);
        consultaService.remover(consultaFind);
        return "redirect:/consulta/consultaList";
    }


}
