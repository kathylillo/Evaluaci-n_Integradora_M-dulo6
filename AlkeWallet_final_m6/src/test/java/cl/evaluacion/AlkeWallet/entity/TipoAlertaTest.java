package cl.evaluacion.AlkeWallet.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cl.evaluacion.AlkeWallet.AlkeWalletApplication;
import cl.evaluacion.AlkeWallet.entity.TipoAlerta;

@SpringBootTest(classes = AlkeWalletApplication.class)
public class TipoAlertaTest {

	@Test
    public void testEnumValues() {
        assertEquals(TipoAlerta.ERROR, TipoAlerta.valueOf("ERROR"));
        assertEquals(TipoAlerta.SUCCESS, TipoAlerta.valueOf("SUCCESS"));
        assertEquals(TipoAlerta.WARNING, TipoAlerta.valueOf("WARNING"));
    }

    @Test
    public void testToString() {
        assertEquals("ERROR", TipoAlerta.ERROR.toString());
        assertEquals("SUCCESS", TipoAlerta.SUCCESS.toString());
        assertEquals("WARNING", TipoAlerta.WARNING.toString());
    }
}
