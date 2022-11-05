package com.produtos.apirest.Controller;

import com.produtos.apirest.Util.Util;
import com.produtos.apirest.models.DTO.VeterinaryDTO;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.service.ExpertiseService;
import com.produtos.apirest.service.VeterinarioService;
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

import static com.produtos.apirest.Controller.ExpertiseControllerTeste.generateEspecialidadeInstance;
import static com.produtos.apirest.Util.Util.buildRequest;
import static com.produtos.apirest.Util.Util.toJson;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class VeterinaryControllerTeste {

    private final String API = "/api/veterinary";

    @MockBean
    private VeterinarioService veterinarioService;

    @MockBean
    private ExpertiseService expertiseService;

    @Autowired
    MockMvc mvc;

    public static Veterinary generateVeterinarioInstance(){
        return Veterinary.builder()
                .veterinaryId(1L)
                .name("nome")
                .phone("telefone")
                .cpf("cpf")
                .expertise(generateEspecialidadeInstance())
                .build();
    }

    public static VeterinaryDTO generateVeterinarioDTOInstance(){
        return VeterinaryDTO.builder()
                .id(1L)
                .name("nome")
                .phone("telefone")
                .cpf("cpf")
                .expertise(generateEspecialidadeInstance())
                .build();
    }

    public static List<Veterinary> generateVeterinarioListInstance(){
        return new ArrayList<>(){{
           add(generateVeterinarioInstance());
        }};
    }

    @WithUserDetails("Admin")
    @Test
    public void deveSalvar() throws Exception{
        Mockito.when(expertiseService.findById(Mockito.anyLong())).thenReturn(generateEspecialidadeInstance());
        Mockito.when(veterinarioService.save(Mockito.any(Veterinary.class))).thenReturn(generateVeterinarioInstance());
        String json = toJson(generateVeterinarioDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.POST, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizar() throws Exception{
        Mockito.when(veterinarioService.update(Mockito.any(Veterinary.class))).thenReturn(generateVeterinarioInstance());
        String json = toJson(generateVeterinarioDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverPorId() throws Exception{
        Long id = 1L;
        Mockito.doNothing().when(veterinarioService).removeById(Mockito.anyLong());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(veterinarioService.removeByIdWithFeedback(Mockito.anyLong())).thenReturn(generateVeterinarioInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorId() throws Exception{
        Long id = 1L;
        Mockito.when(veterinarioService.findById(Mockito.anyLong())).thenReturn(generateVeterinarioInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodos() throws Exception{
        Mockito.when(veterinarioService.findAll()).thenReturn(generateVeterinarioListInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}