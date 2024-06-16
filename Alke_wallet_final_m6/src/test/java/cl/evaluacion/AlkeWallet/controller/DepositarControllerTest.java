package cl.evaluacion.AlkeWallet.controller;


import cl.evaluacion.AlkeWallet.entity.TipoAlerta;
import cl.evaluacion.AlkeWallet.model.Usuario;
import cl.evaluacion.AlkeWallet.service.TransaccionService;
import cl.evaluacion.AlkeWallet.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class DepositarControllerTest {

	 @Mock
	    private TransaccionService transaccionService;

	    @Mock
	    private UsuarioService usuarioService;

	    @Mock
	    private Authentication authentication;

	    @Mock
	    private RedirectAttributes redirectAttributes;

	    @InjectMocks
	    private DepositarController depositarController;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void testDepositoGet() {
	        ModelAndView mav = depositarController.depositoGet(false);
	        assertEquals("depositar.jsp", mav.getViewName());
	        assertEquals(false, mav.getModel().get("realizada"));
	    }

	    @Test
	    void testDepositoPostSuccess() {
	        Usuario usuario = new Usuario();
	        usuario.setCorreo("juanpablo@mail.com");
	        
	        when(authentication.getName()).thenReturn("juanpablo@mail.com");
	        when(usuarioService.getByUsername("juanpablo@mail.com")).thenReturn(usuario);

	        String viewName = depositarController.depositoPost(100, authentication, redirectAttributes);

	        assertEquals("redirect:/home", viewName);
	        verify(transaccionService).depositar("juanpablo@mail.com", 100);
	        
	        ArgumentCaptor<String> titleCaptor = ArgumentCaptor.forClass(String.class);
	        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
	        ArgumentCaptor<TipoAlerta> typeCaptor = ArgumentCaptor.forClass(TipoAlerta.class);
	        
	        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTitulo"), titleCaptor.capture());
	        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaMensaje"), messageCaptor.capture());
	        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTipo"), typeCaptor.capture());
	        
	        assertEquals("Éxito", titleCaptor.getValue());
	        assertEquals("El depósito se realizó exitosamente.", messageCaptor.getValue());
	        assertEquals(TipoAlerta.SUCCESS, typeCaptor.getValue());
	    }

	    @Test
	    void testDepositoPostError() {
	        Usuario usuario = new Usuario();
	        usuario.setCorreo("juanpablo@mail.com");

	        when(authentication.getName()).thenReturn("juanpablo@mail.com");
	        when(usuarioService.getByUsername("juanpablo@mail.com")).thenReturn(usuario);
	        doThrow(new IllegalArgumentException("Error al depositar.")).when(transaccionService).depositar(anyString(), anyInt());

	        String viewName = depositarController.depositoPost(100, authentication, redirectAttributes);

	        assertEquals("redirect:/depositar", viewName);
	        
	        ArgumentCaptor<String> titleCaptor = ArgumentCaptor.forClass(String.class);
	        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
	        ArgumentCaptor<TipoAlerta> typeCaptor = ArgumentCaptor.forClass(TipoAlerta.class);
	        
	        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTitulo"), titleCaptor.capture());
	        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaMensaje"), messageCaptor.capture());
	        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTipo"), typeCaptor.capture());
	        
	        assertEquals("Error", titleCaptor.getValue());
	        assertEquals("Error al depositar.", messageCaptor.getValue());
	        assertEquals(TipoAlerta.ERROR, typeCaptor.getValue());
	    }
	}