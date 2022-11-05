package com.produtos.apirest.Controller;

import com.produtos.apirest.Util.Util;
import com.produtos.apirest.models.AppointmentType;
import com.produtos.apirest.models.DTO.AppointmentTypeDTO;
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

import static com.produtos.apirest.Util.Util.buildRequest;
import static com.produtos.apirest.Util.Util.toJson;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class TipoConsultaControllerTeste {
    private final String API = "/api/appointmentType";
    @MockBean
    private AppointmentTypeService appointmentTypeService;

    @Autowired
    MockMvc mvc;

    public static AppointmentType generateTipoConsultaInstance(){
        return AppointmentType.builder()
                .appointmentTypeId(1L)
                .name("name")
                .build();
    }

    public static AppointmentTypeDTO generateTipoConsultaDTOInstance(){
        return AppointmentTypeDTO.builder()
                .id(1L)
                .name("name")
                .build();
    }

    @WithUserDetails("Admin")
    @Test
    public void deveSalvar() throws Exception{
        Mockito.when(appointmentTypeService.save(Mockito.any(AppointmentType.class))).thenReturn(generateTipoConsultaInstance());
        String json = toJson(generateTipoConsultaDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.POST, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizar() throws Exception{
        Mockito.when(appointmentTypeService.update(Mockito.any(AppointmentType.class))).thenReturn(generateTipoConsultaInstance());
        String json = toJson(generateTipoConsultaDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverPorId() throws Exception{
        Long id = 1L;
        Mockito.doNothing().when(appointmentTypeService).removeById(Mockito.anyLong());
        Mockito.when(appointmentTypeService.findById(Mockito.anyLong())).thenReturn(generateTipoConsultaInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(appointmentTypeService.removeByIdWithFeedback(Mockito.anyLong())).thenReturn(generateTipoConsultaInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorId() throws Exception {
        Long id = 1L;
        Mockito.when(appointmentTypeService.findById(Mockito.anyLong())).thenReturn(generateTipoConsultaInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}