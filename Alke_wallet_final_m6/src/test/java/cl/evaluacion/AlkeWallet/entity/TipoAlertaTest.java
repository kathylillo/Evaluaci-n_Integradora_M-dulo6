package cl.evaluacion.AlkeWallet.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;

import cl.evaluacion.AlkeWallet.AlkeWalletApplication;
/**
 * Pruebas unitarias para la enumeración TipoAlerta.
 */
@SpringBootTest(classes = AlkeWalletApplication.class)
public class TipoAlertaTest {

	 /**
     * Prueba para verificar los valores de la enumeración TipoAlerta.
     */
    @DisplayName("Prueba para valores de enumeración")
    @Test
    public void testEnumValues() {
        assertEquals(TipoAlerta.ERROR, TipoAlerta.valueOf("ERROR"));
        assertEquals(TipoAlerta.SUCCESS, TipoAlerta.valueOf("SUCCESS"));
        assertEquals(TipoAlerta.WARNING, TipoAlerta.valueOf("WARNING"));
    }

    /**
     * Prueba para verificar el método toString de la enumeración TipoAlerta.
     */
    @DisplayName("Prueba para método toString")
    @Test
    public void testToString() {
        assertEquals("ERROR", TipoAlerta.ERROR.toString());
        assertEquals("SUCCESS", TipoAlerta.SUCCESS.toString());
        assertEquals("WARNING", TipoAlerta.WARNING.toString());
    }
}