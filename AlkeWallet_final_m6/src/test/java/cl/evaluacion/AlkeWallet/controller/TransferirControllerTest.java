package cl.evaluacion.AlkeWallet.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cl.evaluacion.AlkeWallet.entity.TipoAlerta;
import cl.evaluacion.AlkeWallet.model.Usuario;
import cl.evaluacion.AlkeWallet.service.TransaccionService;
import cl.evaluacion.AlkeWallet.service.UsuarioService;


import cl.evaluacion.AlkeWallet.AlkeWalletApplication;

@SpringBootTest(classes = AlkeWalletApplication.class)
@AutoConfigureMockMvc
public class TransferirControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private TransaccionService transaccionService;

    @MockBean
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

       
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario1 = new Usuario();
        usuario1.setUser_Id(1);
        usuario1.setCorreo("usuario1@mail.com");
        usuario1.setSaldo(1000);
        usuarios.add(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setUser_Id(2);
        usuario2.setCorreo("usuario2@mail.com");
        usuario2.setSaldo(2000);
        usuarios.add(usuario2);

        when(usuarioService.listado()).thenReturn(usuarios);

       
        doNothing().when(transaccionService).transferir(anyString(), anyInt(), anyInt());
    }

    @Test
    void testTransferenciaGet() throws Exception {
       
        mockMvc.perform(get("/transferir").param("realizada", "false"))
                .andExpect(status().isOk()) 
                .andExpect(view().name("transferir.jsp")) 
                .andExpect(model().attributeExists("realizada")); 
    }

    @Test
    void testTransferenciaPostSuccess() throws Exception {
        
        int receiverUserId = 2;
        int valor = 500;

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("usuario1@mail.com");

        
        mockMvc.perform(post("/transferir")
                        .param("receiverUserId", String.valueOf(receiverUserId))
                        .param("valor", String.valueOf(valor))
                        .principal(authentication))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home")) 
                .andExpect(flash().attributeExists("alertaTitulo")) 
                .andExpect(flash().attributeExists("alertaMensaje"))
                .andExpect(flash().attributeExists("alertaTipo"))
                .andExpect(flash().attribute("alertaTitulo", "Éxito")) 
                .andExpect(flash().attribute("alertaMensaje", "La transferencia se ha realizado correctamente."))
                .andExpect(flash().attribute("alertaTipo", TipoAlerta.SUCCESS.toString())); 
    }

    @Test
    void testTransferenciaPostInvalidUserId() throws Exception {
        
        int receiverUserId = 999;
        int valor = 500;

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("usuario1@mail.com");

       
        mockMvc.perform(post("/transferir")
                        .param("receiverUserId", String.valueOf(receiverUserId))
                        .param("valor", String.valueOf(valor))
                        .principal(authentication))
                .andExpect(status().is3xxRedirection()) 
                .andExpect(redirectedUrl("/transferir")) 
                .andExpect(flash().attributeExists("alertaTitulo")) 
                .andExpect(flash().attributeExists("alertaMensaje"))
                .andExpect(flash().attributeExists("alertaTipo"))
                .andExpect(flash().attribute("alertaTitulo", "Error")) 
                .andExpect(flash().attribute("alertaMensaje", "El ID del destinatario no es válido."))
                .andExpect(flash().attribute("alertaTipo", TipoAlerta.ERROR.toString())); 
    }

    @Test
    void testTransferenciaPostNegativeAmount() throws Exception {
      
        int receiverUserId = 2;
        int valor = -500;

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("usuario1@mail.com");

        mockMvc.perform(post("/transferir")
                        .param("receiverUserId", String.valueOf(receiverUserId))
                        .param("valor", String.valueOf(valor))
                        .principal(authentication))
                .andExpect(status().is3xxRedirection()) 
                .andExpect(redirectedUrl("/transferir")) 
                .andExpect(flash().attributeExists("alertaTitulo")) 
                .andExpect(flash().attributeExists("alertaMensaje"))
                .andExpect(flash().attributeExists("alertaTipo"))
                .andExpect(flash().attribute("alertaTitulo", "Advertencia")) 
                .andExpect(flash().attribute("alertaMensaje", "El monto a transferir debe ser mayor a 0. Por favor, ingresa un monto válido."))
                .andExpect(flash().attribute("alertaTipo", TipoAlerta.WARNING.toString())); 
    }

    @Test
    void testTransferenciaPostInsufficientBalance() throws Exception {

        int receiverUserId = 2;
        int valor = 1500;

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("usuario1@mail.com");

       
        mockMvc.perform(post("/transferir")
                        .param("receiverUserId", String.valueOf(receiverUserId))
                        .param("valor", String.valueOf(valor))
                        .principal(authentication))
                .andExpect(status().is3xxRedirection()) 
                .andExpect(redirectedUrl("/transferir")) 
                .andExpect(flash().attributeExists("alertaTitulo")) 
                .andExpect(flash().attributeExists("alertaMensaje"))
                .andExpect(flash().attributeExists("alertaTipo"))
                .andExpect(flash().attribute("alertaTitulo", "Error")) 
                .andExpect(flash().attribute("alertaMensaje", "El usuario remitente no tiene suficientes fondos para completar la transferencia."))
                .andExpect(flash().attribute("alertaTipo", TipoAlerta.ERROR.toString())); 
    }
}