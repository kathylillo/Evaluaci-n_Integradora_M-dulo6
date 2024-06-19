package cl.evaluacion.AlkeWallet.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import java.time.Instant;

import cl.evaluacion.AlkeWallet.AlkeWalletApplication;

/**
 * Pruebas unitarias para la clase UsuarioEntity.
 */
@SpringBootTest(classes = AlkeWalletApplication.class)
public class UsuarioEntityTest {
	
	private UsuarioEntity usuario;

    /**
     * Configuración inicial para cada prueba.
     */
    @BeforeEach
    public void setUp() {
        usuario = new UsuarioEntity();
    }

    /**
     * Prueba para el campo UserId.
     */
    @DisplayName("Prueba para UserId")
    @Test
    public void testUserId() {
        assertEquals(0, usuario.getUserId());
        usuario.setUserId(1);
        assertEquals(1, usuario.getUserId());
    }

    /**
     * Prueba para el campo Nombre.
     */
    @DisplayName("Prueba para Nombre")
    @Test
    public void testNombre() {
        assertNull(usuario.getNombre());
        usuario.setNombre("Juan Pablo");
        assertEquals("Juan Pablo", usuario.getNombre());
    }

    /**
     * Prueba para el campo Username.
     */
    @DisplayName("Prueba para Username")
    @Test
    public void testUsername() {
        assertNull(usuario.getUsername());
        usuario.setUsername("juan@mail.com");
        assertEquals("juan@mail.com", usuario.getUsername());
    }

    /**
     * Prueba para el campo Password.
     */
    @DisplayName("Prueba para Password")
    @Test
    public void testPassword() {
        assertNull(usuario.getPassword());
        usuario.setPassword("12345");
        assertEquals("12345", usuario.getPassword());
    }

    /**
     * Prueba para el campo Saldo.
     */
    @DisplayName("Prueba para Saldo")
    @Test
    public void testSaldo() {
        assertEquals(0, usuario.getSaldo());
        usuario.setSaldo(1000);
        assertEquals(1000, usuario.getSaldo());
    }

    /**
     * Prueba para el campo FechaDeCreacion.
     */
    @DisplayName("Prueba para FechaDeCreacion")
    @Test
    public void testFechaDeCreacion() {
        assertNull(usuario.getFechaDeCreacion());
        Timestamp now = Timestamp.from(Instant.now());
        usuario.setFechaDeCreacion(now);
        assertEquals(now, usuario.getFechaDeCreacion());
    }
}