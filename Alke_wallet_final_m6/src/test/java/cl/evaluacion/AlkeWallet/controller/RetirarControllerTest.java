package cl.evaluacion.AlkeWallet.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import cl.evaluacion.AlkeWallet.entity.TipoAlerta;
import cl.evaluacion.AlkeWallet.model.Usuario;
import cl.evaluacion.AlkeWallet.service.TransaccionService;
import cl.evaluacion.AlkeWallet.service.UsuarioService;

import static org.mockito.Mockito.*;


import cl.evaluacion.AlkeWallet.AlkeWalletApplication;

@SpringBootTest(classes = AlkeWalletApplication.class)
@AutoConfigureMockMvc
public class RetirarControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransaccionService transaccionService;

    @MockBean
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Pérez");
        usuario.setCorreo("juanperez@mail.com");
        usuario.setSaldo(1000);

        
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(usuario.getCorreo());

        
        when(usuarioService.getByUsername(usuario.getCorreo())).thenReturn(usuario);

        
        doNothing().when(transaccionService).retirar(usuario.getCorreo(), 500); 
        doThrow(new IllegalArgumentException("Saldo insuficiente")).when(transaccionService).retirar(usuario.getCorreo(), 1500); 
    }

    @Test
    void testRetirarPostSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/retirar")
                        .param("monto", "500")
                        .principal(mock(Authentication.class)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) 
                .andExpect(MockMvcResultMatchers.redirectedUrl("/home"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("alertaTitulo", "alertaMensaje", "alertaTipo"))
                .andExpect(MockMvcResultMatchers.flash().attribute("alertaTitulo", "Éxito"))
                .andExpect(MockMvcResultMatchers.flash().attribute("alertaMensaje", "El retiro se realizó exitosamente."))
                .andExpect(MockMvcResultMatchers.flash().attribute("alertaTipo", TipoAlerta.SUCCESS));
        
       
        verify(transaccionService, times(1)).retirar(anyString(), eq(500));
    }

    @Test
    void testRetirarPostInsufficientBalance() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/retirar")
                        .param("monto", "1500")
                        .principal(mock(Authentication.class)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) 
                .andExpect(MockMvcResultMatchers.redirectedUrl("/retirar"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("alertaTitulo", "alertaMensaje", "alertaTipo"))
                .andExpect(MockMvcResultMatchers.flash().attribute("alertaTitulo", "Error"))
                .andExpect(MockMvcResultMatchers.flash().attribute("alertaMensaje", "Saldo insuficiente"))
                .andExpect(MockMvcResultMatchers.flash().attribute("alertaTipo", TipoAlerta.ERROR));
        
        
        verify(transaccionService, times(1)).retirar(anyString(), eq(1500));
    }
}