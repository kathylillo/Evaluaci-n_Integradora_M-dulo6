package cl.evaluacion.AlkeWallet.entity;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Timestamp;

import cl.evaluacion.AlkeWallet.AlkeWalletApplication;

/**
 * Pruebas unitarias para la clase TransaccionEntity.
 */
@SpringBootTest(classes = AlkeWalletApplication.class)
public class TransaccionEntityTest {
	
	/**
     * Prueba para los getters y setters de TransaccionEntity.
     */
    @DisplayName("Prueba para getters y setters")
    @Test
    void testGettersAndSetters() {
        TransaccionEntity transaccion = new TransaccionEntity();

        transaccion.setTransaccionId(1);
        transaccion.setSenderUserId(100);
        transaccion.setReceiverUserId(200);
        transaccion.setValor(1000);
        transaccion.setTransactionDate(new Timestamp(System.currentTimeMillis()));
        transaccion.setCurrencyId(1);

        assertEquals(1, transaccion.getTransaccionId());
        assertEquals(100, transaccion.getSenderUserId());
        assertEquals(200, transaccion.getReceiverUserId());
        assertEquals(1000, transaccion.getValor());
        assertNotNull(transaccion.getTransactionDate());
        assertEquals(1, transaccion.getCurrencyId());
    }

    /**
     * Prueba para el método toString de TransaccionEntity.
     */
    @DisplayName("Prueba para toString")
    @Test
    void testToString() {
        TransaccionEntity transaccion = new TransaccionEntity();
        transaccion.setTransaccionId(1);
        transaccion.setSenderUserId(100);
        transaccion.setReceiverUserId(200);
        transaccion.setValor(1000);
        transaccion.setTransactionDate(new Timestamp(System.currentTimeMillis()));
        transaccion.setCurrencyId(1);

        String expectedString = "TransaccionEntity(transaccionId=1, senderUserId=100, receiverUserId=200, valor=1000, transactionDate=" + transaccion.getTransactionDate() + ", currencyId=1)";
        assertEquals(expectedString, transaccion.toString());
    }

    /**
     * Prueba para los métodos equals y hashCode de TransaccionEntity.
     */
    @DisplayName("Prueba para equals y hashCode")
    @Test
    void testEqualsAndHashCode() {
        TransaccionEntity transaccion1 = new TransaccionEntity();
        transaccion1.setTransaccionId(1);
        transaccion1.setSenderUserId(100);
        transaccion1.setReceiverUserId(200);
        transaccion1.setValor(1000);
        transaccion1.setTransactionDate(new Timestamp(System.currentTimeMillis()));
        transaccion1.setCurrencyId(1);

        TransaccionEntity transaccion2 = new TransaccionEntity();
        transaccion2.setTransaccionId(1);
        transaccion2.setSenderUserId(100);
        transaccion2.setReceiverUserId(200);
        transaccion2.setValor(1000);
        transaccion2.setTransactionDate(transaccion1.getTransactionDate());
        transaccion2.setCurrencyId(1);

        assertEquals(transaccion1, transaccion2);
        assertEquals(transaccion1.hashCode(), transaccion2.hashCode());
    }
}