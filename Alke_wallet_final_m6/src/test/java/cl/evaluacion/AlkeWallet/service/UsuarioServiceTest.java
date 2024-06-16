package cl.evaluacion.AlkeWallet.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cl.evaluacion.AlkeWallet.model.Usuario;
import cl.evaluacion.AlkeWallet.service.UsuarioService;

import org.springframework.boot.test.context.SpringBootTest;

import cl.evaluacion.AlkeWallet.AlkeWalletApplication;

@SpringBootTest(classes = AlkeWalletApplication.class)
public class UsuarioServiceTest {

	 @Mock
	    private UsuarioService usuarioService;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void testCrear() {
	        Usuario usuario = new Usuario();
	        usuario.setUser_Id(1);
	        usuario.setCorreo("juanpablo@mail.com");

	        when(usuarioService.crear(usuario)).thenReturn(1);

	        int result = usuarioService.crear(usuario);

	        assertThat(result).isEqualTo(1);
	    }

	    @Test
	    void testGetById() {
	        Usuario usuario = new Usuario();
	        usuario.setUser_Id(1);
	        usuario.setCorreo("juanpablo@mail.com");

	        when(usuarioService.getById(1)).thenReturn(usuario);

	        Usuario result = usuarioService.getById(1);

	        assertThat(result).isNotNull();
	        assertThat(result.getUser_Id()).isEqualTo(1);
	        assertThat(result.getCorreo()).isEqualTo("juanpablo@mail.com");
	    }

	    @Test
	    void testGetByUsername() {
	        Usuario usuario = new Usuario();
	        usuario.setCorreo("juanpablo@mail.com");

	        when(usuarioService.getByUsername("juanpablo@mail.com")).thenReturn(usuario);

	        Usuario result = usuarioService.getByUsername("juanpablo@mail.com");

	        assertThat(result).isNotNull();
	        assertThat(result.getCorreo()).isEqualTo("juanpablo@mail.com");
	    }

	    @Test
	    void testListado() {
	        Usuario usuario1 = new Usuario();
	        usuario1.setUser_Id(1);
	        usuario1.setCorreo("juanpablo@mail.com");

	        Usuario usuario2 = new Usuario();
	        usuario2.setUser_Id(2);
	        usuario2.setCorreo("mperez@mail.com");

	        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);

	        when(usuarioService.listado()).thenReturn(usuarios);

	        List<Usuario> result = usuarioService.listado();

	        assertThat(result).hasSize(2);
	        assertThat(result).contains(usuario1, usuario2);
	    }

	    @Test
	    void testObtenerSaldoUsuario() {
	        when(usuarioService.obtenerSaldoUsuario("juanpablo@mail.com")).thenReturn(1000);

	        int saldo = usuarioService.obtenerSaldoUsuario("juanpablo@mail.com");

	        assertThat(saldo).isEqualTo(1000);
	    }
	}