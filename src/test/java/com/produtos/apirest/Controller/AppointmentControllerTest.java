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

import java.util.Collections;
import java.util.List;

import static com.produtos.apirest.Controller.AnimalControllerTest.generateAnimal;
import static com.produtos.apirest.Controller.TipoAppointmentControllerTest.generateAppointmentType;
import static com.produtos.apirest.Controller.VeterinaryControllerTest.generateVeterinary;
import static com.produtos.apirest.Util.Util.buildRequest;
import static com.produtos.apirest.Util.Util.toJson;
import static org.mockito.ArgumentMatchers.isA;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class AppointmentControllerTest {

    private final String API = "/api/appointment";

    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private AnimalService animalService;

    @MockBean
    private AppointmentTypeService appointmentTypeService;

    @Autowired
    public MockMvc mvc;

    public static Appointment generateAppointment(){
        return Appointment.builder()
                .appointmentId(1L)
                .description("description")
                .appointmentType(generateAppointmentType())
                .veterinary(generateVeterinary())
                .animal(generateAnimal())
                .build();
    }

    public static AppointmentDTO generateAppointmentDTO(){
        return AppointmentDTO.builder()
                .id(1L)
                .description("description")
                .appointmentTypeId(1L)
                .vetId(1L)
                .animalId(1L)
                .build();
    }

    public static List<Appointment> generateAppointmentList(){
        return Collections.singletonList(generateAppointment());
    }

    @Test
    @WithUserDetails("Admin")
    public void save() throws Exception{
        Mockito.when(appointmentService.save(Mockito.any(Appointment.class))).thenReturn(generateAppointment());
        Mockito.when(appointmentTypeService.findById(Mockito.anyLong())).thenReturn(generateAppointmentType());
        Mockito.when(animalService.findById(Mockito.anyLong())).thenReturn(generateAnimal());
        String json = toJson(generateAppointmentDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.POST, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void update() throws Exception{
        Mockito.when(appointmentService.update(Mockito.any(Appointment.class))).thenReturn(generateAppointment());
        String json = toJson(generateAppointmentDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void removeById() throws Exception{
        Long id = 1L;
        Mockito.doNothing().when(appointmentService).removeById(isA(Long.class));
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));
    }

    @WithUserDetails("Admin")
    @Test
    public void findAll() throws Exception{
        Mockito.when(appointmentService.findAll()).thenReturn(generateAppointmentList());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void removeByIdWithFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(appointmentService.removeByIdWithFeedback(Mockito.anyLong())).thenReturn(generateAppointment());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}