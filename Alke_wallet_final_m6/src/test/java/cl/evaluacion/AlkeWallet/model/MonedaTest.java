package cl.evaluacion.AlkeWallet.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Pruebas unitarias para la clase Moneda.
 */
@SpringBootTest
public class MonedaTest {

	/**
     * Prueba del constructor y métodos getter y setter de Moneda.
     */
    @DisplayName("Prueba del constructor y métodos getter y setter")
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
