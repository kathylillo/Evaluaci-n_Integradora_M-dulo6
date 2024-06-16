package cl.evaluacion.AlkeWallet.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cl.evaluacion.AlkeWallet.entity.UsuarioEntity;
import cl.evaluacion.AlkeWallet.model.Usuario;
import cl.evaluacion.AlkeWallet.repository.UsuarioRepository;
import cl.evaluacion.AlkeWallet.service.impl.UsuarioServiceImpl;

import org.springframework.boot.test.context.SpringBootTest;
import cl.evaluacion.AlkeWallet.AlkeWalletApplication;

@SpringBootTest(classes = AlkeWalletApplication.class)
public class UsuarioServiceImplTest {

	 @Mock
	    private UsuarioRepository usuarioRepository;

	    @InjectMocks
	    private UsuarioServiceImpl usuarioService;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void testCrear() throws Exception {
	        Usuario usuario = new Usuario();
	        usuario.setNombre("Antonio Marin ");
	        usuario.setCorreo("amarin@mail.com");
	        usuario.setClave("98765"); 
	        usuario.setFecha_de_creacion(null);

	        UsuarioEntity usuarioEntity = new UsuarioEntity();
	        usuarioEntity.setUserId(4);

	        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

	        int userId = usuarioService.crear(usuario);

	        assertThat(userId).isEqualTo(4);
	        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class)); 
	    }

	    @Test
	    void testCrearThrowsException() {
	        Usuario usuario = new Usuario();
	        usuario.setNombre("Antonio Marin ");
	        usuario.setCorreo("amarin@mail.com");
	        usuario.setClave("$2a$10$WBfrCS6KdAcVPq4pJCs0xO1iQuKeoALiJynKnNP8oL8rhdQtKF/3W"); //
	        usuario.setFecha_de_creacion(null);

	        when(usuarioRepository.save(any(UsuarioEntity.class))).thenThrow(new RuntimeException("Error"));

	        assertThatThrownBy(() -> usuarioService.crear(usuario))
	                .isInstanceOf(RuntimeException.class)
	                .hasMessageContaining("Error");
	    }

	    @Test
	    void testGetById() {
	        UsuarioEntity usuarioEntity = new UsuarioEntity();
	        usuarioEntity.setUserId(4);
	        usuarioEntity.setNombre("Antonio Marin ");
	        usuarioEntity.setUsername("amarin@mail.com");

	        when(usuarioRepository.findById(4)).thenReturn(Optional.of(usuarioEntity));

	        Usuario usuario = usuarioService.getById(4);

	        assertThat(usuario).isNotNull();
	        assertThat(usuario.getUser_Id()).isEqualTo(4);
	        assertThat(usuario.getNombre()).isEqualTo("Antonio Marin ");
	        assertThat(usuario.getCorreo()).isEqualTo("amarin@mail.com");
	    }

	    @Test
	    void testGetByIdNotFound() {
	        when(usuarioRepository.findById(15)).thenReturn(Optional.empty());

	        Usuario usuario = usuarioService.getById(15);

	        assertThat(usuario).isNull();
	    }

	    @Test
	    void testListado() throws Exception {
	        UsuarioEntity usuarioEntity = new UsuarioEntity();
	        usuarioEntity.setUserId(4);
	        usuarioEntity.setNombre("Antonio Marin ");
	        usuarioEntity.setUsername("amarin@mail.com");

	        List<UsuarioEntity> usuarioEntities = new ArrayList<>();
	        usuarioEntities.add(usuarioEntity);

	        when(usuarioRepository.findAll()).thenReturn(usuarioEntities);

	        List<Usuario> usuarios = usuarioService.listado();

	        assertThat(usuarios).hasSize(1);
	        assertThat(usuarios.get(0).getUser_Id()).isEqualTo(4);
	        assertThat(usuarios.get(0).getNombre()).isEqualTo("Antonio Marin ");
	        assertThat(usuarios.get(0).getCorreo()).isEqualTo("amarin@mail.com");
	    }

	    @Test
	    void testListadoThrowsException() {
	        when(usuarioRepository.findAll()).thenThrow(new RuntimeException("Error"));

	        assertThatThrownBy(() -> usuarioService.listado())
	                .isInstanceOf(RuntimeException.class)
	                .hasMessageContaining("Error");
	    }

	    @Test
	    void testLoadUserByUsername() {
	        UsuarioEntity usuarioEntity = new UsuarioEntity();
	        usuarioEntity.setUserId(4);
	        usuarioEntity.setUsername("amarin@mail.com");
	        usuarioEntity.setPassword(new BCryptPasswordEncoder().encode("98765")); 

	        when(usuarioRepository.findByUsername("amarin@mail.com ")).thenReturn(usuarioEntity);

	        UserDetails userDetails = usuarioService.loadUserByUsername("amarin@mail.com ");

	        assertThat(userDetails).isNotNull();
	        assertThat(userDetails.getUsername()).isEqualTo("amarin@mail.com ");
	        assertThat(new BCryptPasswordEncoder().matches("98765", userDetails.getPassword())).isTrue();
	    }

	    @Test
	    void testLoadUserByUsernameNotFound() {
	        when(usuarioRepository.findByUsername("marin@mail.com")).thenReturn(null);

	        assertThatThrownBy(() -> usuarioService.loadUserByUsername("marin@mail.com"))
	                .isInstanceOf(UsernameNotFoundException.class)
	                .hasMessageContaining("Usuario no encontrado con el correo electrónico: marin@mail.com");
	    }

	    @Test
	    void testGetByUsername() {
	        UsuarioEntity usuarioEntity = new UsuarioEntity();
	        usuarioEntity.setUserId(4);
	        usuarioEntity.setNombre("Antonio Marin ");
	        usuarioEntity.setUsername("amarin@mail.com");

	        when(usuarioRepository.findByUsername("amarin@mail.com")).thenReturn(usuarioEntity);

	        Usuario usuario = usuarioService.getByUsername("amarin@mail.com");

	        assertThat(usuario).isNotNull();
	        assertThat(usuario.getUser_Id()).isEqualTo(4);
	        assertThat(usuario.getNombre()).isEqualTo("Antonio Marin");
	        assertThat(usuario.getCorreo()).isEqualTo("amarin@mail.com");
	    }

	    @Test
	    void testGetByUsernameNotFound() {
	        when(usuarioRepository.findByUsername("amarin@mail.com")).thenReturn(null);

	        Usuario usuario = usuarioService.getByUsername("amarin@mail.com");

	        assertThat(usuario).isNull();
	    }

	    @Test
	    void testObtenerSaldoUsuario() {
	        UsuarioEntity usuarioEntity = new UsuarioEntity();
	        usuarioEntity.setUserId(4);
	        usuarioEntity.setUsername("amarin@mail.com");
	        usuarioEntity.setSaldo(1000);

	        when(usuarioRepository.findByUsername("amarin@mail.com")).thenReturn(usuarioEntity);

	        int saldo = usuarioService.obtenerSaldoUsuario("amarin@mail.com");

	        assertThat(saldo).isEqualTo(1000);
	    }

	    @Test
	    void testObtenerSaldoUsuarioNotFound() {
	        when(usuarioRepository.findByUsername("marin@mail.com")).thenReturn(null);

	        assertThatThrownBy(() -> usuarioService.obtenerSaldoUsuario("marin@mail.com"))
	                .isInstanceOf(IllegalArgumentException.class)
	                .hasMessageContaining("El usuario no existe");
	    }
	}