package cl.evaluacion.AlkeWallet.controller;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cl.evaluacion.AlkeWallet.model.Transaccion;
import cl.evaluacion.AlkeWallet.service.TransaccionService;

import cl.evaluacion.AlkeWallet.AlkeWalletApplication;

@SpringBootTest(classes = AlkeWalletApplication.class)
public class TransaccionControllertTest {
	
	@Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private TransaccionService transaccionService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

       
        when(transaccionService.getHistorial(anyString())).thenReturn(new ArrayList<>()); 
    }

    @Test
    void testViewHistorialWithTransactions() throws Exception {
        List<Transaccion> transacciones = new ArrayList<>();
        transacciones.add(new Transaccion());
        transacciones.add(new Transaccion());

       
        when(transaccionService.getHistorial(anyString())).thenReturn(transacciones);

        
        mockMvc.perform(get("/historial").principal(mock(Authentication.class)))
                .andExpect(status().isOk()) 
                .andExpect(view().name("historial.jsp")) 
                .andExpect(model().attributeExists("historial")) 
                .andExpect(model().attribute("historial", transacciones)); 
    }

    @Test
    void testViewHistorialWithoutTransactions() throws Exception {
        
        mockMvc.perform(get("/historial").principal(mock(Authentication.class)))
                .andExpect(status().isOk()) 
                .andExpect(view().name("historial.jsp")) 
                .andExpect(model().attributeExists("historial")) 
                .andExpect(model().attribute("historial", new ArrayList<>())); 
    }
}