package cl.evaluacion.AlkeWallet.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import cl.evaluacion.AlkeWallet.model.Moneda;

@SpringBootTest
public class MonedaTest {

	@Test
    public void testConstructorAndGettersSetters() {
    
        int currencyId = 1;
        String currencyName = "Peso Chileno";
        String currencySymbol = " CLP $";

       
        Moneda moneda = new Moneda();
        moneda.setCurrencyId(currencyId);
        moneda.setCurrencyName(currencyName);
        moneda.setCurrencySymbol(currencySymbol);

        
        Assertions.assertEquals(currencyId, moneda.getCurrencyId());
        Assertions.assertEquals(currencyName, moneda.getCurrencyName());
        Assertions.assertEquals(currencySymbol, moneda.getCurrencySymbol());
    }
}
