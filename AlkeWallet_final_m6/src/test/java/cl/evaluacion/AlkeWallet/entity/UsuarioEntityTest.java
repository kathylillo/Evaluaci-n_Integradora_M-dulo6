package cl.evaluacion.AlkeWallet.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import java.time.Instant;

import cl.evaluacion.AlkeWallet.AlkeWalletApplication;
import cl.evaluacion.AlkeWallet.entity.UsuarioEntity;

@SpringBootTest(classes = AlkeWalletApplication.class)
public class UsuarioEntityTest {
	
	 private UsuarioEntity usuario;

	    @BeforeEach
	    public void setUp() {
	        usuario = new UsuarioEntity();
	    }

	   
		@SuppressWarnings("unused")
		@Test
	    public void testUserId() {
	        assertNull(usuario.getUserId());
	        usuario.setUserId(1);
	        assertEquals(1, usuario.getUserId());
	    }

	    @Test
	    public void testNombre() {
	        assertNull(usuario.getNombre());
	        usuario.setNombre("Juan Pablo");
	        assertEquals("Juan Pablo", usuario.getNombre());
	    }

	    @Test
	    public void testUsername() {
	        assertNull(usuario.getUsername());
	        usuario.setUsername("juan@mail.com");
	        assertEquals("juan@mail.com", usuario.getUsername());
	    }

	    @Test
	    public void testPassword() {
	        assertNull(usuario.getPassword());
	        usuario.setPassword("12345");
	        assertEquals("12345", usuario.getPassword());
	    }

	    @Test
	    public void testSaldo() {
	        assertEquals(0, usuario.getSaldo());
	        usuario.setSaldo(1000);
	        assertEquals(1000, usuario.getSaldo());
	    }

	    @Test
	    public void testFechaDeCreacion() {
	        assertNull(usuario.getFechaDeCreacion());
	        Timestamp now = Timestamp.from(Instant.now());
	        usuario.setFechaDeCreacion(now);
	        assertEquals(now, usuario.getFechaDeCreacion());
	    }
	}