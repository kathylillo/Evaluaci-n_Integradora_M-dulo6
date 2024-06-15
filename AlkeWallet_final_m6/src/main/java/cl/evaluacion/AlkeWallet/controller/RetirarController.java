package cl.evaluacion.AlkeWallet.controller;

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
 * Controlador para manejar las operaciones relacionadas con el retiro de fondos en la aplicación.
 */
@Controller
public class RetirarController {

	private final TransaccionService transaccionService;
	private final UsuarioService usuarioService;

	/**
     * Constructor para inyectar los servicios de transacción y usuario.
     *
     * @param transaccionService Servicio para manejar transacciones.
     * @param usuarioService Servicio para manejar los usuarios.
     */

	public RetirarController(TransaccionService transaccionService, UsuarioService usuarioService) {
		this.transaccionService = transaccionService;
		this.usuarioService = usuarioService;
	}

	/**
     * Maneja las solicitudes GET para la página de retiro.
     *
     * @param realizada Indica si la operación de retiro se realizó.
     * @return ModelAndView para la vista de retiro.
     */
	@GetMapping("/retirar")
	public ModelAndView retirarGet(@RequestParam(defaultValue = "false") boolean realizada) {
		ModelAndView mav = new ModelAndView("retirar.jsp");
		mav.addObject("realizada", realizada);
		return mav;
	}

     /**
      * Maneja las solicitudes POST para realizar un retiro.
      *
      * @param monto Monto a retirar.
      * @param authentication Información de autenticación del usuario.
      * @param redirectAttributes Atributos para redirigir con mensajes de alerta.
      * @return Redirección a la vista de inicio si el retiro es exitoso, de lo contrario, redirección a la vista de retiro.
      */
	@PostMapping("/retirar")
	public String retirarPost(@RequestParam("monto") int monto, Authentication authentication,
			RedirectAttributes redirectAttributes) {

		Usuario usuario = usuarioService.getByUsername(authentication.getName());
		String correo = usuario.getCorreo();
		try {
			transaccionService.retirar(correo, monto);
			redirectAttributes.addFlashAttribute("alertaTitulo", "Éxito");
			redirectAttributes.addFlashAttribute("alertaMensaje", "El retiro se realizó exitosamente.");
			redirectAttributes.addFlashAttribute("alertaTipo", TipoAlerta.SUCCESS);
		} catch (IllegalArgumentException e) {
			redirectAttributes.addFlashAttribute("alertaTitulo", "Error");
			redirectAttributes.addFlashAttribute("alertaMensaje", e.getMessage());
			redirectAttributes.addFlashAttribute("alertaTipo", TipoAlerta.ERROR);
			return "redirect:/retirar";
		}

		return "redirect:/home";
	}
}