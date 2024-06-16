package cl.evaluacion.AlkeWallet.model;

import java.sql.Timestamp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import cl.evaluacion.AlkeWallet.model.Transaccion;

@SpringBootTest
public class TransaccionTest {
	
	@Test
    public void testConstructorAndGettersSetters() {
       
        int transactionId = 1;
        int senderUserId = 100;
        int receiverUserId = 200;
        int valor = 500;
        Timestamp transactionDate = new Timestamp(System.currentTimeMillis());
        int currencyId = 1;

       
        Transaccion transaccion = new Transaccion();
        transaccion.setTransaction_Id(transactionId);
        transaccion.setSender_User_Id(senderUserId);
        transaccion.setReceiver_User_Id(receiverUserId);
        transaccion.setValor(valor);
        transaccion.setTransaction_Date(transactionDate);
        transaccion.setCurrency_Id(currencyId);

        
        Assertions.assertEquals(transactionId, transaccion.getTransaction_Id());
        Assertions.assertEquals(senderUserId, transaccion.getSender_User_Id());
        Assertions.assertEquals(receiverUserId, transaccion.getReceiver_User_Id());
        Assertions.assertEquals(valor, transaccion.getValor());
        Assertions.assertEquals(transactionDate, transaccion.getTransaction_Date());
        Assertions.assertEquals(currencyId, transaccion.getCurrency_Id());
    }

    @Test
    public void testToString() {
       
        Transaccion transaccion = new Transaccion();
        transaccion.setTransaction_Id(1);
        transaccion.setSender_User_Id(100);
        transaccion.setReceiver_User_Id(200);
        transaccion.setValor(500);
        transaccion.setTransaction_Date(new Timestamp(System.currentTimeMillis()));
        transaccion.setCurrency_Id(1);

       
        String toStringResult = transaccion.toString();

        
        Assertions.assertNotNull(toStringResult);
        Assertions.assertTrue(toStringResult.contains("transaction_Id=" + transaccion.getTransaction_Id()));
        Assertions.assertTrue(toStringResult.contains("sender_User_Id=" + transaccion.getSender_User_Id()));
        Assertions.assertTrue(toStringResult.contains("receiver_User_Id=" + transaccion.getReceiver_User_Id()));
        Assertions.assertTrue(toStringResult.contains("valor=" + transaccion.getValor()));
        Assertions.assertTrue(toStringResult.contains("transaction_Date=" + transaccion.getTransaction_Date()));
        Assertions.assertTrue(toStringResult.contains("currency_Id=" + transaccion.getCurrency_Id()));
    
}

}
