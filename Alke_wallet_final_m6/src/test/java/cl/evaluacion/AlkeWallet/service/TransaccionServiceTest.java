package cl.evaluacion.AlkeWallet.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import cl.evaluacion.AlkeWallet.model.Transaccion;


import org.springframework.boot.test.context.SpringBootTest;
import cl.evaluacion.AlkeWallet.AlkeWalletApplication;

@SpringBootTest(classes = AlkeWalletApplication.class)
public class TransaccionServiceTest {
	
	@Mock
    private TransaccionService transaccionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba para el método depositar de TransaccionService.
     */
    @Test
    @DisplayName("Test Depositar")
    void testDepositar() {
        String correo = "juanpablo@mail.com";
        int monto = 1000;

        doNothing().when(transaccionService).depositar(correo, monto);

        transaccionService.depositar(correo, monto);

        verify(transaccionService, times(1)).depositar(correo, monto);
    }

    /**
     * Prueba para el método retirar de TransaccionService.
     */
    @Test
    @DisplayName("Test Retirar")
    void testRetirar() {
        String correo = "juanpablo@mail.com";
        int monto = 500;

        doNothing().when(transaccionService).retirar(correo, monto);

        transaccionService.retirar(correo, monto);

        verify(transaccionService, times(1)).retirar(correo, monto);
    }

    /**
     * Prueba para el método transferir de TransaccionService.
     */
    @Test
    @DisplayName("Test Transferir")
    void testTransferir() {
        String correo = "juanpablo@mail.com";
        int receiverUserId = 2;
        int valor = 300;

        doNothing().when(transaccionService).transferir(correo, receiverUserId, valor);

        transaccionService.transferir(correo, receiverUserId, valor);

        verify(transaccionService, times(1)).transferir(correo, receiverUserId, valor);
    }

    /**
     * Prueba para el método getHistorial de TransaccionService.
     */
    @Test
    @DisplayName("Test Obtener Historial")
    void testGetHistorial() {
        String correo = "juanpablo@mail.com";
        List<Transaccion> historial = new ArrayList<>();
        historial.add(new Transaccion()); 

        when(transaccionService.getHistorial(correo)).thenReturn(historial);

        List<Transaccion> result = transaccionService.getHistorial(correo);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(transaccionService, times(1)).getHistorial(correo);
    }
}