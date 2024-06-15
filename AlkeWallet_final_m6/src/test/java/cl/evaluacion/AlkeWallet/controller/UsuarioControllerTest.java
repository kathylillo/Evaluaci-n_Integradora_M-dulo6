package cl.evaluacion.AlkeWallet.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import cl.evaluacion.AlkeWallet.AlkeWalletApplication;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;


import cl.evaluacion.AlkeWallet.model.Usuario;
import cl.evaluacion.AlkeWallet.service.UsuarioService;

@AutoConfigureMockMvc
@SpringBootTest(classes = AlkeWalletApplication.class)
public class UsuarioControllerTest {
	 @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private UsuarioService usuarioService;

	    @MockBean
	    private UserDetailsService userDetailsService; 

	    @BeforeEach
	    void setUp() {
	        
	        when(usuarioService.crear(any(Usuario.class))).thenReturn(1); 
	    }

	    @Test
	    void testLoginGet() throws Exception {
	        mockMvc.perform(get("/login"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("login.jsp"));
	    }

	    @Test
	    void testLoginGetWithError() throws Exception {
	        mockMvc.perform(get("/login").param("error", "true"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("login.jsp"));
	        
	    }

	    @Test
	    void testRegistroGet() throws Exception {
	        mockMvc.perform(get("/registro"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("registro.jsp"))
	                .andExpect(model().attribute("creado", false))
	                .andExpect(model().attributeExists("usuario"));
	    }

	    @Test
	    void testRegistroPostSuccess() throws Exception {
	        Usuario usuario = new Usuario();
	        usuario.setNombre("Juan Pérez");
	        usuario.setCorreo("juanperez@mail.com");
	        usuario.setClave("123456");

	        mockMvc.perform(post("/registro")
	                .flashAttr("usuario", usuario))
	                .andExpect(status().is3xxRedirection())
	                .andExpect(redirectedUrl("/registro?creado=true"));

	       
	        verify(usuarioService, times(1)).crear(any(Usuario.class));
	    }

	    @Test
	    void testRegistroPostError() throws Exception {
	        Usuario usuario = new Usuario();
	        usuario.setNombre("Juan Pérez");
	        usuario.setCorreo("juanperez@mail.com");
	        usuario.setClave("123456");

	        
	        when(usuarioService.crear(any(Usuario.class))).thenReturn(0);

	        mockMvc.perform(post("/registro")
	                .flashAttr("usuario", usuario))
	                .andExpect(status().is3xxRedirection())
	                .andExpect(redirectedUrl("/registro?creado=false"));

	        
	        verify(usuarioService, times(1)).crear(any(Usuario.class));
	    }
	}