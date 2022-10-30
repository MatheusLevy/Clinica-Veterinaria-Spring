package com.produtos.apirest.Controller;

import com.produtos.apirest.enums.RoleName;
import com.produtos.apirest.models.DTO.RoleDTO;
import com.produtos.apirest.models.Role;
import com.produtos.apirest.service.RoleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.produtos.apirest.Util.Util.request;
import static com.produtos.apirest.Util.Util.toJson;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class RoleControllerTeste {

    private final String API = "/api/role";

    @MockBean
    private RoleService roleService;

    @Autowired
    MockMvc mvc;

    public Role generateRoleInstance(){
        return Role.builder()
                .RoleId(1L)
                .roleName(RoleName.ROLE_TESTE)
                .build();
    }

    public RoleDTO generateRoleDTOInstance(){
        return RoleDTO.builder()
                .id(1L)
                .roleName(RoleName.ROLE_TESTE)
                .build();
    }

    @Test
    public void deveSalvar() throws Exception{
        Mockito.when(roleService.salvar(Mockito.any(Role.class))).thenReturn(generateRoleInstance());
        String json = toJson(generateRoleDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.POST, API.concat("/salvar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void deveAtualizar() throws Exception {
        Mockito.when(roleService.atualizar(Mockito.any(Role.class))).thenReturn(generateRoleInstance());
        String json = toJson(generateRoleDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.PUT, API.concat("/atualizar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveRemover() throws Exception{
        Long id = 1L;
        Mockito.doNothing().when(roleService).removerPorId(Mockito.anyLong());
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveRemoverComFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(roleService.RemoverComFeedback(Mockito.anyLong())).thenReturn(generateRoleInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveBuscarPorId() throws Exception{
        Long id = 1L;
        Mockito.when(roleService.buscarPorId(Mockito.anyLong())).thenReturn(generateRoleInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscar/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}