package cl.evaluacion.AlkeWallet.controller;

import cl.evaluacion.AlkeWallet.entity.TipoAlerta;
import cl.evaluacion.AlkeWallet.model.Usuario;
import cl.evaluacion.AlkeWallet.service.TransaccionService;
import cl.evaluacion.AlkeWallet.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

/**
 * Pruebas unitarias para TransferirController.
 */
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

    /**
     * Configuración inicial antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba para verificar la solicitud GET a "/transferir".
     */
    @DisplayName("Prueba GET /transferir con transferencia realizada")
    @Test
    void testTransferenciaGet() {
        ModelAndView mav = transferirController.transferenciaGet(true);
        assertEquals("transferir.jsp", mav.getViewName());
        assertEquals(true, mav.getModel().get("realizada"));
    }

    /**
     * Prueba para verificar la solicitud POST a "/transferir" con ID de usuario destinatario inválido.
     */
    @DisplayName("Prueba POST /transferir con ID de usuario destinatario inválido")
    @Test
    void testTransferenciaPostInvalidUserId() {
        when(authentication.getName()).thenReturn("juanpablo@mail.com");
        when(usuarioService.getByUsername("juanpablo@mail.com")).thenReturn(new Usuario());
        when(usuarioService.listado()).thenReturn(new ArrayList<>());

        String viewName = transferirController.transferenciaPost(999, 100, authentication, redirectAttributes);

        assertEquals("redirect:/transferir", viewName);

        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTitulo"), eq("Error"));
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaMensaje"), eq("El ID del destinatario no es válido."));
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTipo"), eq(TipoAlerta.ERROR));
    }

    /**
     * Prueba para verificar la solicitud POST a "/transferir" con monto de transferencia inválido.
     */
    @DisplayName("Prueba POST /transferir con monto de transferencia inválido")
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

        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTitulo"), eq("Advertencia"));
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaMensaje"), eq("El monto a transferir debe ser mayor a 0. Por favor, ingresa un monto válido."));
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTipo"), eq(TipoAlerta.WARNING));
    }

    /**
     * Prueba para verificar la solicitud POST a "/transferir" con fondos insuficientes.
     */
    @DisplayName("Prueba POST /transferir con fondos insuficientes")
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

        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTitulo"), eq("Error"));
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaMensaje"), eq("El usuario remitente no tiene suficientes fondos para completar la transferencia."));
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTipo"), eq(TipoAlerta.ERROR));
    }

    /**
     * Prueba para verificar la solicitud POST a "/transferir" con éxito.
     */
    @DisplayName("Prueba POST /transferir con éxito")
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

        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTitulo"), eq("Éxito"));
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaMensaje"), eq("La transferencia se ha realizado correctamente."));
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("alertaTipo"), eq(TipoAlerta.SUCCESS));
    }
}