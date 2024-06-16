package cl.evaluacion.AlkeWallet.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import java.time.Instant;

import cl.evaluacion.AlkeWallet.AlkeWalletApplication;
import cl.evaluacion.AlkeWallet.entity.TransaccionEntity;

@SpringBootTest(classes = AlkeWalletApplication.class)
public class TransaccionEntityTest {
	
	
	private TransaccionEntity transaccion;

	    @BeforeEach
	    public void setUp() {
	        transaccion = new TransaccionEntity();
	    }

	    @SuppressWarnings("unused")
		@Test
	    public void testTransaccionId() {
	        assertNull(transaccion.getTransaccionId());
	        transaccion.setTransaccionId(1);
	        assertEquals(1, transaccion.getTransaccionId());
	    }

	    @Test
	    public void testSenderUserId() {
	        assertEquals(0, transaccion.getSenderUserId());
	        transaccion.setSenderUserId(10);
	        assertEquals(10, transaccion.getSenderUserId());
	    }

	    @Test
	    public void testReceiverUserId() {
	        assertEquals(0, transaccion.getReceiverUserId());
	        transaccion.setReceiverUserId(20);
	        assertEquals(20, transaccion.getReceiverUserId());
	    }

	    @Test
	    public void testValor() {
	        assertEquals(0, transaccion.getValor());
	        transaccion.setValor(1000);
	        assertEquals(1000, transaccion.getValor());
	    }

	    @Test
	    public void testTransactionDate() {
	        assertNull(transaccion.getTransactionDate());
	        Timestamp now = Timestamp.from(Instant.now());
	        transaccion.setTransactionDate(now);
	        assertEquals(now, transaccion.getTransactionDate());
	    }

	    @Test
	    public void testCurrencyId() {
	        assertEquals(0, transaccion.getCurrencyId());
	        transaccion.setCurrencyId(1);
	        assertEquals(1, transaccion.getCurrencyId());
	    }
	}