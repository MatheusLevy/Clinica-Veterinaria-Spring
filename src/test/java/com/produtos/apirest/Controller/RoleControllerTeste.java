package com.produtos.apirest.Controller;

import com.produtos.apirest.Util.Util;
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

import static com.produtos.apirest.Util.Util.buildRequest;
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
                .roleId(1L)
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
        Mockito.when(roleService.save(Mockito.any(Role.class))).thenReturn(generateRoleInstance());
        String json = toJson(generateRoleDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.POST, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void deveAtualizar() throws Exception {
        Mockito.when(roleService.update(Mockito.any(Role.class))).thenReturn(generateRoleInstance());
        String json = toJson(generateRoleDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveRemover() throws Exception{
        Long id = 1L;
        Mockito.doNothing().when(roleService).removeById(Mockito.anyLong());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveRemoverComFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(roleService.removeByIdWithFeedback(Mockito.anyLong())).thenReturn(generateRoleInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveBuscarPorId() throws Exception{
        Long id = 1L;
        Mockito.when(roleService.findById(Mockito.anyLong())).thenReturn(generateRoleInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}