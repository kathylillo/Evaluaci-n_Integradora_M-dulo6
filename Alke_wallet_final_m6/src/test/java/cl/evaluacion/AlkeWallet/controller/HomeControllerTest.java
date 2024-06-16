package cl.evaluacion.AlkeWallet.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.*;


import cl.evaluacion.AlkeWallet.model.Usuario;
import cl.evaluacion.AlkeWallet.service.UsuarioService;


import cl.evaluacion.AlkeWallet.AlkeWalletApplication;

@SpringBootTest(classes = AlkeWalletApplication.class)
@AutoConfigureMockMvc
public class HomeControllerTest {

	 @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private UsuarioService usuarioService;

	    @BeforeEach
	    void setUp() {
	       
	        Usuario usuario = new Usuario();
	        usuario.setNombre("Juan Pérez");
	        usuario.setCorreo("juanperez@mail.com");
	        usuario.setSaldo(1000);
	        when(usuarioService.getByUsername(anyString())).thenReturn(usuario);
	        when(usuarioService.obtenerSaldoUsuario(anyString())).thenReturn(1000);
	    }

	    @Test
	    void testHomeGet() throws Exception {
	      
	        Usuario usuario = new Usuario();
	        usuario.setNombre("Juan Pérez");
	        usuario.setCorreo("juanperez@mail.com");
	        usuario.setSaldo(1000);

	        
	        Authentication authentication = mock(Authentication.class);
	        when(authentication.getName()).thenReturn("juanperez@mail.com");

	        
	        mockMvc.perform(get("/home").principal(authentication).param("disableRedirect", "true"))
	                .andExpect(status().isOk()) 
	                .andExpect(view().name("home.jsp"))
	                .andExpect(model().attributeExists("usuario")) 
	                .andExpect(model().attribute("usuario", hasProperty("nombre", is("Juan Pérez")))); 
	    }
	}