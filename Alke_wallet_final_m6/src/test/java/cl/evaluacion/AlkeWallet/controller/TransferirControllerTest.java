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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;



public class TransferirControllerTest {

	@Mock
    private TransaccionService transaccionService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private Authentication authentication;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private TransferirController transferirController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTransferenciaGet() {
        ModelAndView mav = transferirController.transferenciaGet(true);
        assertEquals("transferir.jsp", mav.getViewName());
        assertEquals(true, mav.getModel().get("realizada"));
    }

    @Test
    void testTransferenciaPostInvalidUserId() {
        when(authentication.getName()).thenReturn("juanpablo@mail.com");
        when(usuarioService.getByUsername("juanpablo@mail.com")).thenReturn(new Usuario());
        when(usuarioService.listado()).thenReturn(new ArrayList<>());

        String viewName = transferirController.transferenciaPost(999, 100, authentication, redirectAttributes);

        assertEquals("redirect:/transferir", viewName);
        
        ArgumentCaptor<String> titleCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<TipoAlerta> typeCaptor = ArgumentCaptor.forClass(TipoAlerta.class);
        
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTitulo"), titleCaptor.capture());
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaMensaje"), messageCaptor.capture());
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTipo"), typeCaptor.capture());
        
        assertEquals("Error", titleCaptor.getValue());
        assertEquals("El ID del destinatario no es válido.", messageCaptor.getValue());
        assertEquals(TipoAlerta.ERROR, typeCaptor.getValue());
    }

    @Test
    void testTransferenciaPostInvalidAmount() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("juanpablo@mail.com");
        usuario.setSaldo(200);

        when(authentication.getName()).thenReturn("juanpablo@mail.com");
        when(usuarioService.getByUsername("juanpablo@mail.com")).thenReturn(usuario);
        List<Usuario> usuarios = new ArrayList<>();
        Usuario receiverUsuario = new Usuario();
        receiverUsuario.setUser_Id(1);
        usuarios.add(receiverUsuario);
        when(usuarioService.listado()).thenReturn(usuarios);

        String viewName = transferirController.transferenciaPost(1, 0, authentication, redirectAttributes);

        assertEquals("redirect:/transferir", viewName);
        
        ArgumentCaptor<String> titleCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<TipoAlerta> typeCaptor = ArgumentCaptor.forClass(TipoAlerta.class);
        
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTitulo"), titleCaptor.capture());
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaMensaje"), messageCaptor.capture());
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTipo"), typeCaptor.capture());
        
        assertEquals("Advertencia", titleCaptor.getValue());
        assertEquals("El monto a transferir debe ser mayor a 0. Por favor, ingresa un monto válido.", messageCaptor.getValue());
        assertEquals(TipoAlerta.WARNING, typeCaptor.getValue());
    }

    @Test
    void testTransferenciaPostInsufficientFunds() {
        Usuario usuario = new Usuario();
        usuario.setSaldo(50);

        List<Usuario> usuarios = new ArrayList<>();
        Usuario receiverUsuario = new Usuario();
        receiverUsuario.setUser_Id(1);
        usuarios.add(receiverUsuario);

        when(authentication.getName()).thenReturn("juanpablo@mail.com");
        when(usuarioService.getByUsername("juanpablo@mail.com")).thenReturn(usuario);
        when(usuarioService.listado()).thenReturn(usuarios);

        String viewName = transferirController.transferenciaPost(1, 100, authentication, redirectAttributes);

        assertEquals("redirect:/transferir", viewName);
        
        ArgumentCaptor<String> titleCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<TipoAlerta> typeCaptor = ArgumentCaptor.forClass(TipoAlerta.class);
        
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTitulo"), titleCaptor.capture());
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaMensaje"), messageCaptor.capture());
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTipo"), typeCaptor.capture());
        
        assertEquals("Error", titleCaptor.getValue());
        assertEquals("El usuario remitente no tiene suficientes fondos para completar la transferencia.", messageCaptor.getValue());
        assertEquals(TipoAlerta.ERROR, typeCaptor.getValue());
    }

    @Test
    void testTransferenciaPostSuccess() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("juanpablo@mail.com");
        usuario.setSaldo(200);
        
        Usuario receiverUsuario = new Usuario();
        receiverUsuario.setUser_Id(1);

        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(receiverUsuario);
        
        when(authentication.getName()).thenReturn("juanpablo@mail.com");
        when(usuarioService.getByUsername("juanpablo@mail.com")).thenReturn(usuario);
        when(usuarioService.listado()).thenReturn(usuarios);

        String viewName = transferirController.transferenciaPost(1, 100, authentication, redirectAttributes);

        assertEquals("redirect:/home", viewName);
        verify(transaccionService).transferir("juanpablo@mail.com", 1, 100);
        
        ArgumentCaptor<String> titleCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<TipoAlerta> typeCaptor = ArgumentCaptor.forClass(TipoAlerta.class);
        
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTitulo"), titleCaptor.capture());
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaMensaje"), messageCaptor.capture());
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTipo"), typeCaptor.capture());
        
        assertEquals("Éxito", titleCaptor.getValue());
        assertEquals("La transferencia se ha realizado correctamente.", messageCaptor.getValue());
        assertEquals(TipoAlerta.SUCCESS, typeCaptor.getValue());
    }
}