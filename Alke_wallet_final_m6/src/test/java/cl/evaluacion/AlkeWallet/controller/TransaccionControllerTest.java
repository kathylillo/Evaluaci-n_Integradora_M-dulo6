package cl.evaluacion.AlkeWallet.controller;

import cl.evaluacion.AlkeWallet.model.Transaccion;
import cl.evaluacion.AlkeWallet.service.TransaccionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransaccionControllerTest {
	@Mock
    private TransaccionService transaccionService;

    @Mock
    private Authentication authentication;

    @Mock
    private Model model;

    @InjectMocks
    private TransaccionController transaccionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testViewHistorial() {
        // Datos de prueba
        String email = "juanpablo@mail.com";
        List<Transaccion> mockHistorial = new ArrayList<>();

        // Crear transacciones de prueba usando setters
        Transaccion transaccion1 = new Transaccion();
        transaccion1.setSender_User_Id(1);
        transaccion1.setReceiver_User_Id(2);
        transaccion1.setCurrency_Id(1);
        transaccion1.setValor(100);

        Transaccion transaccion2 = new Transaccion();
        transaccion2.setSender_User_Id(2);
        transaccion2.setReceiver_User_Id(1);
        transaccion2.setCurrency_Id(1);
        transaccion2.setValor(150);

        mockHistorial.add(transaccion1);
        mockHistorial.add(transaccion2);

        // Configuración de los mocks
        when(authentication.getName()).thenReturn(email);
        when(transaccionService.getHistorial(email)).thenReturn(mockHistorial);

        // Ejecución del método a probar
        String viewName = transaccionController.viewHistorial(authentication, model);

        // Verificación de la vista y el modelo
        assertEquals("historial.jsp", viewName);

        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(model).addAttribute(eq("historial"), argumentCaptor.capture());

        List<Transaccion> historialInModel = argumentCaptor.getValue();
        assertEquals(mockHistorial, historialInModel);
        verify(transaccionService, times(1)).getHistorial(email);
    }
}