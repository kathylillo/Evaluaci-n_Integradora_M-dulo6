package cl.evaluacion.AlkeWallet.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cl.evaluacion.AlkeWallet.model.Transaccion;
import cl.evaluacion.AlkeWallet.service.TransaccionService;


/**
 * Controlador para manejar las operaciones relacionadas con las transacciones en la aplicación.
 */
@Controller
public class TransaccionController {

	private final TransaccionService transaccionService;

	/**
     * Constructor para inyectar los servicios de transacción y usuario.
     *
     * @param transaccionService Servicio para manejar transacciones.
     */
	public TransaccionController(TransaccionService transaccionService) {

		this.transaccionService = transaccionService;
	}

	 /**
     * Maneja las solicitudes GET para la página de historial de transacciones.
     *
     * @param authentication Información de autenticación del usuario.
     * @param model Modelo para pasar datos a la vista.
     * @return Nombre de la vista de historial de transacciones.
     */
	@GetMapping("/historial")
	public String viewHistorial(Authentication authentication, Model model) {
		
		String correo = authentication.getName();
		List<Transaccion> historial = transaccionService.getHistorial(correo);
		model.addAttribute("historial", historial);
		return "historial.jsp";
	}
}