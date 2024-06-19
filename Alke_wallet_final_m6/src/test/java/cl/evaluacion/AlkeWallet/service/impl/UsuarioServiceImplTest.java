package cl.evaluacion.AlkeWallet.service.impl;

import cl.evaluacion.AlkeWallet.entity.UsuarioEntity;
import cl.evaluacion.AlkeWallet.model.Usuario;
import cl.evaluacion.AlkeWallet.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * Pruebas unitarias para la clase UsuarioServiceImpl.
 */
public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba para la creación de un usuario.
     */
    @Test
    @DisplayName("Test Crear Usuario")
    void testCrear() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Pablo Reyes");
        usuario.setCorreo("juanpablo@mail.com");
        usuario.setClave("Profe12345");
        usuario.setFecha_de_creacion(new java.sql.Timestamp(System.currentTimeMillis()));

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setUserId(1);
        usuarioEntity.setNombre(usuario.getNombre());
        usuarioEntity.setUsername(usuario.getCorreo());
        usuarioEntity.setFechaDeCreacion(usuario.getFecha_de_creacion());
        usuarioEntity.setPassword(new BCryptPasswordEncoder().encode(usuario.getClave()));

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        int userId = usuarioService.crear(usuario);

        assertEquals(1, userId);
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    /**
     * Prueba para la creación de un usuario que lanza una excepción.
     */
    @Test
    @DisplayName("Test Crear Usuario Lanza Excepción")
    void testCrearThrowsException() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Pablo Reyes");
        usuario.setCorreo("juanpablo@mail.com");
        usuario.setClave("Profe12345");

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenThrow(new RuntimeException("Error al guardar usuario"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.crear(usuario);
        });

        assertEquals("Error al guardar usuario", exception.getMessage());
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    /**
     * Prueba para obtener un usuario por su ID.
     */
    @Test
    @DisplayName("Test Obtener Usuario por ID")
    void testGetById() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setUserId(1);
        usuarioEntity.setNombre("Juan Pablo Reyes");
        usuarioEntity.setUsername("juanpablo@mail.com");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioEntity));

        Usuario usuario = usuarioService.getById(1);

        assertNotNull(usuario);
        assertEquals(1, usuario.getUser_Id());
        assertEquals("Juan Pablo Reyes", usuario.getNombre());
        assertEquals("juanpablo@mail.com", usuario.getCorreo());
        verify(usuarioRepository, times(1)).findById(1);
    }

    /**
     * Prueba para obtener un usuario por su ID cuando no se encuentra.
     */
    @Test
    @DisplayName("Test Obtener Usuario por ID No Encontrado")
    void testGetByIdNotFound() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        Usuario usuario = usuarioService.getById(1);

        assertNull(usuario);
        verify(usuarioRepository, times(1)).findById(1);
    }

    /**
     * Prueba para obtener un listado de usuarios.
     */
    @Test
    @DisplayName("Test Listado de Usuarios")
    void testListado() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setUserId(1);
        usuarioEntity.setNombre("Juan Pablo Reyes");
        usuarioEntity.setUsername("juanpablo@mail.com");

        List<UsuarioEntity> usuarioEntities = new ArrayList<>();
        usuarioEntities.add(usuarioEntity);

        when(usuarioRepository.findAll()).thenReturn(usuarioEntities);

        List<Usuario> usuarios = usuarioService.listado();

        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals(1, usuarios.get(0).getUser_Id());
        verify(usuarioRepository, times(1)).findAll();
    }

    /**
     * Prueba para obtener un listado de usuarios que lanza una excepción.
     */
    @Test
    @DisplayName("Test Listado de Usuarios Lanza Excepción")
    void testListadoThrowsException() {
        when(usuarioRepository.findAll()).thenThrow(new RuntimeException("Error al obtener listado"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.listado();
        });

        assertEquals("Error al obtener listado", exception.getMessage());
        verify(usuarioRepository, times(1)).findAll();
    }

    /**
     * Prueba para cargar un usuario por su nombre de usuario.
     */
    @Test
    @DisplayName("Test Cargar Usuario por Nombre de Usuario")
    void testLoadUserByUsername() {
        String username = "juanpablo@mail.com";
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setUsername(username);
        usuarioEntity.setPassword("Profe12345");

        when(usuarioRepository.findByUsername(username)).thenReturn(usuarioEntity);

        UserDetails userDetails = usuarioService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        verify(usuarioRepository, times(1)).findByUsername(username);
    }

    /**
     * Prueba para cargar un usuario por su nombre de usuario cuando no se encuentra.
     */
    @Test
    @DisplayName("Test Cargar Usuario por Nombre de Usuario No Encontrado")
    void testLoadUserByUsernameNotFound() {
        String username = "pepito@mail.com";
        when(usuarioRepository.findByUsername(username)).thenReturn(null);

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            usuarioService.loadUserByUsername(username);
        });

        assertEquals("Error al cargar usuario por nombre de usuario", exception.getMessage());
        verify(usuarioRepository, times(1)).findByUsername(username);
    }

    /**
     * Prueba para obtener un usuario por su nombre de usuario.
     */
    @Test
    @DisplayName("Test Obtener Usuario por Nombre de Usuario")
    void testGetByUsername() {
        String correo = "juanpablo@mail.com";
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setUserId(1);
        usuarioEntity.setNombre("Juan Pablo Reyes");
        usuarioEntity.setUsername(correo);
        usuarioEntity.setSaldo(1000);

        when(usuarioRepository.findByUsername(correo)).thenReturn(usuarioEntity);

        Usuario usuario = usuarioService.getByUsername(correo);

        assertNotNull(usuario);
        assertEquals(1, usuario.getUser_Id());
        assertEquals("Juan Pablo Reyes", usuario.getNombre());
        assertEquals(correo, usuario.getCorreo());
        assertEquals(1000, usuario.getSaldo());
        verify(usuarioRepository, times(1)).findByUsername(correo);
    }

    /**
     * Prueba para obtener un usuario por su nombre de usuario cuando no se encuentra.
     */
    @Test
    @DisplayName("Test Obtener Usuario por Nombre de Usuario No Encontrado")
    void testGetByUsernameNotFound() {
        String correo = "pepito@mail.com";
        when(usuarioRepository.findByUsername(correo)).thenReturn(null);

        Usuario usuario = usuarioService.getByUsername(correo);

        assertNull(usuario);
        verify(usuarioRepository, times(1)).findByUsername(correo);
    }

    /**
     * Prueba para obtener el saldo de un usuario por su nombre de usuario.
     */
    @Test
    @DisplayName("Test Obtener Saldo de Usuario")
    void testObtenerSaldoUsuario() {
        String correo = "juanpablo@mail.com";
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setSaldo(1000);

        when(usuarioRepository.findByUsername(correo)).thenReturn(usuarioEntity);

        int saldo = usuarioService.obtenerSaldoUsuario(correo);

        assertEquals(1000, saldo);
        verify(usuarioRepository, times(1)).findByUsername(correo);
    }

    /**
     * Prueba para obtener el saldo de un usuario por su nombre de usuario cuando no se encuentra.
     */
    @Test
    @DisplayName("Test Obtener Saldo de Usuario No Encontrado")
    void testObtenerSaldoUsuarioNotFound() {
        String correo = "pepito@mail.com";
        when(usuarioRepository.findByUsername(correo)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.obtenerSaldoUsuario(correo);
        });

        assertEquals("El usuario no existe", exception.getMessage());
        verify(usuarioRepository, times(1)).findByUsername(correo);
    }
}