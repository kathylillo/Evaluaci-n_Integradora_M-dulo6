package cl.evaluacion.AlkeWallet.controller;
import cl.evaluacion.AlkeWallet.model.Transaccion;
import cl.evaluacion.AlkeWallet.service.TransaccionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
/**
 * Pruebas unitarias para el controlador {@link TransaccionController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TransaccionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransaccionService transaccionService;

    @MockBean
    private Authentication authentication;

    /**
     * Configuración inicial para las pruebas.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Prueba para verificar el comportamiento del método viewHistorial en {@link TransaccionController}
     * con un usuario válido.
     *
     * @throws Exception en caso de que la ejecución de la solicitud falle.
     */
    @SuppressWarnings("unchecked")
    @DisplayName("Prueba viewHistorial con usuario válido")
    @Test
    @WithMockUser(username = "juanpablo@mail.com")
    void testViewHistorial() throws Exception {
        String correo = "juanpablo@mail.com";
        List<Transaccion> historial = new ArrayList<>();

        when(authentication.getName()).thenReturn(correo);
        when(transaccionService.getHistorial(correo)).thenReturn(historial);

        MvcResult result = mockMvc.perform(get("/historial")
                .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(view().name("historial.jsp"))
                .andReturn();

        verify(authentication, times(2)).getName();
        verify(transaccionService, times(1)).getHistorial(correo);

        List<Transaccion> historialModel = (List<Transaccion>) result.getModelAndView().getModel().get("historial");
        assertEquals(historial, historialModel);
    }
}