package cl.evaluacion.AlkeWallet.controller;

import cl.evaluacion.AlkeWallet.AlkeWalletApplication;
import cl.evaluacion.AlkeWallet.entity.TipoAlerta;
import cl.evaluacion.AlkeWallet.model.Usuario;

import cl.evaluacion.AlkeWallet.service.TransaccionService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AlkeWalletApplication.class)
@AutoConfigureMockMvc
public class DepositarControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private TransaccionService transaccionService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testDepositoPostSuccess() throws Exception {
        
        Usuario usuario = new Usuario();
        usuario.setCorreo("juanpablo@mail.com");

      
        Authentication authentication = mock(Authentication.class);
        SecurityContext context = mock(SecurityContext.class);
        SecurityContextHolder.setContext(context);
        when(context.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(usuario.getCorreo());

      
        when(usuarioService.getByUsername(any(String.class))).thenReturn(usuario);

        
        doNothing().when(transaccionService).depositar(any(String.class), any(Integer.class));

       
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/depositar")
                .param("monto", "100")
                .principal(authentication))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/home"))
                .andReturn();

     
        RedirectAttributes redirectAttributes = (RedirectAttributes) result.getFlashMap().get("org.springframework.web.servlet.support.SessionFlashMapManager");
        assertThat(redirectAttributes).isNotNull();

      
        assertThat(redirectAttributes.getFlashAttributes().get("alertaTitulo")).isEqualTo("Éxito");
        assertThat(redirectAttributes.getFlashAttributes().get("alertaMensaje")).isEqualTo("El depósito se realizó exitosamente.");
        assertThat(redirectAttributes.getFlashAttributes().get("alertaTipo")).isEqualTo(TipoAlerta.SUCCESS);
    }

    @Test
    void testDepositoPostException() throws Exception {
      
        Usuario usuario = new Usuario();
        usuario.setCorreo("juanpablo@mail.com");

       
        Authentication authentication = mock(Authentication.class);
        SecurityContext context = mock(SecurityContext.class);
        SecurityContextHolder.setContext(context);
        when(context.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(usuario.getCorreo());


        when(usuarioService.getByUsername(any(String.class))).thenReturn(usuario);

       
        String errorMessage = "Error en la transacción";
        doThrow(new IllegalArgumentException(errorMessage)).when(transaccionService).depositar(any(String.class), any(Integer.class));

      
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/depositar")
                .param("monto", "100")
                .principal(authentication))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/depositar"))
                .andReturn();

       
        RedirectAttributes redirectAttributes = (RedirectAttributes) result.getFlashMap().get("org.springframework.web.servlet.support.SessionFlashMapManager");
        assertThat(redirectAttributes).isNotNull();
        assertThat(redirectAttributes.getFlashAttributes().get("alertaTitulo")).isEqualTo("Error");
        assertThat(redirectAttributes.getFlashAttributes().get("alertaMensaje")).isEqualTo(errorMessage);
        assertThat(redirectAttributes.getFlashAttributes().get("alertaTipo")).isEqualTo(TipoAlerta.ERROR);
    }
}
