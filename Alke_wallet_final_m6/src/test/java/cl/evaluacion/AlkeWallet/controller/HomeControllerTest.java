package cl.evaluacion.AlkeWallet.controller;

import cl.evaluacion.AlkeWallet.model.Usuario;
import cl.evaluacion.AlkeWallet.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UsuarioService usuarioService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testHome() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setCorreo("juanpablo@mail.com");
        usuario.setNombre("Juan Pablo Reyes");

        Authentication authentication = mock(Authentication.class);
        SecurityContext context = mock(SecurityContext.class);
        SecurityContextHolder.setContext(context);
        when(context.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(usuario.getCorreo());

        when(usuarioService.getByUsername(any(String.class))).thenReturn(usuario);
        when(usuarioService.obtenerSaldoUsuario(any(String.class))).thenReturn(1000);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/home")
                .principal(authentication))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home.jsp"))
                .andReturn();

        Map<String, Object> model = result.getModelAndView().getModel();
        Usuario usuarioModel = (Usuario) model.get("usuario");
        Integer saldo = (Integer) model.get("saldo");

        verify(usuarioService, times(1)).getByUsername(usuario.getCorreo());
        verify(usuarioService, times(1)).obtenerSaldoUsuario(usuario.getCorreo());

        // Verificar que los atributos del modelo sean los esperados
        assert usuarioModel != null;
        assert saldo != null;
        assert usuarioModel.getNombre().equals("Juan Pablo Reyes");
        assert saldo.equals(1000);
    }
    @Test
    void testHomeWithFlashAttributes() throws Exception {
        Map<String, Object> flashMap = new HashMap<>();
        flashMap.put("alertaTitulo", "Éxito");
        flashMap.put("alertaMensaje", "Mensaje de éxito");
        flashMap.put("alertaTipo", "SUCCESS");

        Authentication authentication = mock(Authentication.class);
        SecurityContext context = mock(SecurityContext.class);
        SecurityContextHolder.setContext(context);
        when(context.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("juanpablo@mail.com");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/home")
                .flashAttrs(flashMap)
                .principal(authentication))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home.jsp"))
                .andReturn();

        Map<String, Object> model = result.getModelAndView().getModel();

        // Verificar que los atributos flash se han pasado correctamente al modelo
        assert model.get("alertaTitulo").equals("Éxito");
        assert model.get("alertaMensaje").equals("Mensaje de éxito");
        assert model.get("alertaTipo").equals("SUCCESS");
    }
}