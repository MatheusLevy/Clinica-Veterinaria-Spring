package com.produtos.apirest.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.DonoService;
import com.produtos.apirest.service.TipoAnimalService;
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

import static com.produtos.apirest.Controller.DonoControllerTeste.getDonoInstance;
import static com.produtos.apirest.Controller.TipoAnimalControllerTeste.getTipoAnimalInstance;
import static com.produtos.apirest.Util.Util.request;
import static com.produtos.apirest.Util.Util.toJson;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class AnimalControllerTeste {

    private final String API = "/api/animal";

    @MockBean
    private AnimalService animalService;

    @Autowired
    public MockMvc mvc;

    @MockBean
    private DonoService donoService;

    @MockBean
    private TipoAnimalService tipoAnimalService;

    public static List<Animal> getAnimalListInstance(){
        return new ArrayList<>(){{
            add(getAnimalInstance());
        }};
    }

    public static Animal getAnimalInstance(){
        return Animal.builder()
                .animalId(Long.valueOf(1))
                .nome("nome")
                .dono(getDonoInstance())
                .tipoAnimal(getTipoAnimalInstance())
                .build();
    }

    public static AnimalDTO getAnimalDTOInstance(){
        return AnimalDTO.builder()
                .id(Long.valueOf(1))
                .idTipoAnimal(Long.valueOf(1))
                .nome("nome")
                .idDono(Long.valueOf(1))
                .idTipoAnimal(Long.valueOf(1))
                .dono(getDonoInstance())
                .tipo(getTipoAnimalInstance())
                .build();
    }
    @Test
    @WithUserDetails("Admin")
    public void deveSalvarController() throws Exception{
        Mockito.when(animalService.salvar(Mockito.any(Animal.class))).thenReturn(getAnimalInstance());
        Mockito.when(donoService.buscarPorId(Mockito.anyLong())).thenReturn(getDonoInstance());
        Mockito.when(tipoAnimalService.buscarPorId(Mockito.anyLong())).thenReturn(getTipoAnimalInstance());
        String json = new ObjectMapper().writeValueAsString(getAnimalDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.POST, API.concat("/salvar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveAtualizarController() throws Exception{
        Mockito.when(animalService.atualizar(Mockito.any(Animal.class))).thenReturn(getAnimalInstance());
        String json = toJson(getAnimalDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.POST, API.concat("/atualizar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveAtualizarDonoController() throws Exception{
        Mockito.when(donoService.buscarPorId(Mockito.anyLong())).thenReturn(getDonoInstance());
        Mockito.when(animalService.buscarPorId(Mockito.anyLong())).thenReturn(getAnimalInstance());
        Mockito.when(animalService.atualizarDono(Mockito.any(Animal.class), Mockito.any(Dono.class))).thenReturn(getAnimalInstance());
        String json = toJson(getAnimalDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.PUT, API.concat("/atualizar/dono"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveRemoverController() throws Exception{
        Long id = Long.valueOf(1);
        doNothing().when(animalService).removerPorId(isA(Long.class));
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));
    }

    @Test
    @WithUserDetails("Admin")
    public void deveRemoverComFeedbackController() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(animalService.removerComFeedback(Mockito.anyLong())).thenReturn(getAnimalInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveBuscarPorIdController() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(animalService.buscarPorId(Mockito.anyLong())).thenReturn(getAnimalInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscarId/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveBuscarDonoController() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(animalService.buscarDonoPorId(Mockito.anyLong())).thenReturn(getDonoInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscarDono/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
