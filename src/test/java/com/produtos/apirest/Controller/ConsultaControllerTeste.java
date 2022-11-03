package com.produtos.apirest.Controller;

import com.produtos.apirest.Util.Util;
import com.produtos.apirest.models.Appointment;
import com.produtos.apirest.models.DTO.AppointmentDTO;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.AppointmentService;
import com.produtos.apirest.service.AppointmentTypeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static com.produtos.apirest.Controller.AnimalControllerTeste.generateAnimalInstance;
import static com.produtos.apirest.Controller.TipoConsultaControllerTeste.generateTipoConsultaInstance;
import static com.produtos.apirest.Controller.VeterinarioControllerTeste.generateVeterinarioInstance;
import static com.produtos.apirest.Util.Util.buildRequest;
import static com.produtos.apirest.Util.Util.toJson;
import static org.mockito.ArgumentMatchers.isA;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class ConsultaControllerTeste {

    private final String API = "/api/consulta";

    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private AnimalService animalService;

    @MockBean
    private AppointmentTypeService appointmentTypeService;

    @Autowired
    public MockMvc mvc;

    public static Appointment generateConsultaInstance(){
        return Appointment.builder()
                .appointmentId(1L)
                .description("descrição")
                .appointmentType(generateTipoConsultaInstance())
                .veterinary(generateVeterinarioInstance())
                .animal(generateAnimalInstance())
                .build();
    }

    public static AppointmentDTO generateConsultaDTOInstance(){
        return AppointmentDTO.builder()
                .id(1L)
                .description("descrição")
                .appointmentTypeId(1L)
                .vetId(1L)
                .animalId(1L)
                .build();
    }

    public static List<Appointment> generateConsultaListInstance(){
        return new ArrayList<>(){
            {
                add(generateConsultaInstance());
            }
        };
    }

    @Test
    @WithUserDetails("Admin")
    public void deveSalvar() throws Exception{
        Mockito.when(appointmentService.save(Mockito.any(Appointment.class))).thenReturn(generateConsultaInstance());
        Mockito.when(appointmentTypeService.findById(Mockito.anyLong())).thenReturn(generateTipoConsultaInstance());
        Mockito.when(animalService.findById(Mockito.anyLong())).thenReturn(generateAnimalInstance());
        String json = toJson(generateConsultaDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.POST, API.concat("/salvar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizar() throws Exception{
        Mockito.when(appointmentService.update(Mockito.any(Appointment.class))).thenReturn(generateConsultaInstance());
        String json = toJson(generateConsultaDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API.concat("/atualizar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverPorId() throws Exception{
        Long id = 1L;
        Mockito.doNothing().when(appointmentService).removeById(isA(Long.class));
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/remover/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodos() throws Exception{
        Mockito.when(appointmentService.findAll()).thenReturn(generateConsultaListInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/buscarTodos"));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedback() throws Exception{

        Long id = 1L;
        Mockito.when(appointmentService.removeByIdWithFeedback(Mockito.anyLong())).thenReturn(generateConsultaInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/remover/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}