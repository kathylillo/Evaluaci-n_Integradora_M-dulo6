package cl.evaluacion.AlkeWallet.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.evaluacion.AlkeWallet.entity.TipoAlerta;
import cl.evaluacion.AlkeWallet.model.Usuario;
import cl.evaluacion.AlkeWallet.service.TransaccionService;
import cl.evaluacion.AlkeWallet.service.UsuarioService;

/**
 * Controlador para manejar las operaciones de transferencia en la aplicación.
 */
@Controller
public class TransferirController {
	
	private final TransaccionService transaccionService;
	private final UsuarioService usuarioService;

	/**
	 * Constructor para inyectar los servicios de transacción y usuario.
	 *
	 * @param transaccionService Servicio para manejar transacciones.
	 * @param usuarioService Servicio para manejar los usuarios.
	 */
	public TransferirController(TransaccionService transaccionService, UsuarioService usuarioService) {
		this.transaccionService = transaccionService;
		this.usuarioService = usuarioService;
	}

	/**
	 * Maneja las solicitudes GET para la página de transferencia.
	 *
	 * @param realizada Indica si la transferencia se ha realizado.
	 * @return Modelo y vista para la página de transferencia.
	 */
	@GetMapping("/transferir")
	public ModelAndView transferenciaGet(@RequestParam(defaultValue = "false") boolean realizada) {
		ModelAndView mav = new ModelAndView("transferir.jsp");
		mav.addObject("realizada", realizada);
		System.out.println("Transferencia completada: " + realizada);

		return mav;
	}

	/**
	 * Maneja las solicitudes POST para realizar una transferencia.
	 *
	 * @param receiverUserId ID del usuario receptor.
	 * @param valor Monto a transferir.
	 * @param authentication Información de autenticación del usuario.
	 * @param redirectAttributes Atributos para redirigir con mensajes de alerta.
	 * @return Redirección a la página correspondiente.
	 */
	@PostMapping("/transferir")
	public String transferenciaPost(@RequestParam("receiverUserId") int receiverUserId,
			@RequestParam("valor") int valor, Authentication authentication, RedirectAttributes redirectAttributes) {
		Usuario usuario = usuarioService.getByUsername(authentication.getName());
		List<Usuario> usuarios = usuarioService.listado();

		// Validar que el ID del destinatario exista
		boolean isValidUserId = usuarios.stream().anyMatch(u -> u.getUser_Id() == receiverUserId);
		if (!isValidUserId) {
			redirectAttributes.addFlashAttribute("alertaTitulo", "Error");
			redirectAttributes.addFlashAttribute("alertaMensaje", "El ID del destinatario no es válido.");
			redirectAttributes.addFlashAttribute("alertaTipo", TipoAlerta.ERROR);
			return "redirect:/transferir";
		}

		if (valor <= 0) {
			redirectAttributes.addFlashAttribute("alertaTitulo", "Advertencia");
			redirectAttributes.addFlashAttribute("alertaMensaje",
					"El monto a transferir debe ser mayor a 0. Por favor, ingresa un monto válido.");
			redirectAttributes.addFlashAttribute("alertaTipo", TipoAlerta.WARNING);

			return "redirect:/transferir";
		}

		if (usuario.getSaldo() < valor) {
			redirectAttributes.addFlashAttribute("alertaTitulo", "Error");
			redirectAttributes.addFlashAttribute("alertaMensaje",
					"El usuario remitente no tiene suficientes fondos para completar la transferencia.");
			redirectAttributes.addFlashAttribute("alertaTipo", TipoAlerta.ERROR);
			return "redirect:/transferir";
		}

		transaccionService.transferir(usuario.getCorreo(), receiverUserId, valor);
		redirectAttributes.addFlashAttribute("alertaTitulo", "Éxito");
		redirectAttributes.addFlashAttribute("alertaMensaje", "La transferencia se ha realizado correctamente.");
		redirectAttributes.addFlashAttribute("alertaTipo", TipoAlerta.SUCCESS);

		return "redirect:/home";
	}
}