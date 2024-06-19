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
 * Controlador para manejar las operaciones de depósito en la aplicación.
 */
@Controller
public class DepositarController {
	
	private final TransaccionService transaccionService;
	private final UsuarioService usuarioService;

	/**
     * Constructor para inyectar las dependencias de los servicios.
     *
     * @param transaccionService Servicio para manejar las transacciones.
     * @param usuarioService Servicio para manejar los usuarios.
     */
	public DepositarController(TransaccionService transaccionService, UsuarioService usuarioService) {
		this.transaccionService = transaccionService;
		this.usuarioService = usuarioService;
	}

	/**
     * Maneja las solicitudes GET para la página de depósito.
     *
     * @param realizada Indica si la transacción de depósito se ha realizado con éxito.
     * @return ModelAndView para la vista de depósito.
     */
	@GetMapping("/depositar")
	public ModelAndView depositoGet(@RequestParam(defaultValue = "false") boolean realizada) {
		ModelAndView mav = new ModelAndView("depositar.jsp");
		mav.addObject("realizada", realizada);
		return mav;
	}

	/**
     * Maneja las solicitudes POST para realizar un depósito.
     *
     * @param monto Monto a depositar.
     * @param authentication Información de autenticación del usuario.
     * @param redirectAttributes Atributos para redireccionar con mensajes de alerta.
     * @return Redirección a la página correspondiente tras el intento de depósito.
     */
	 @PostMapping("/depositar")
	    public String depositoPost(@RequestParam("monto") int monto, Authentication authentication,
	            RedirectAttributes redirectAttributes) {
		 
	        if (monto <= 0) {
	            redirectAttributes.addFlashAttribute("alertaTitulo", "Error");
	            redirectAttributes.addFlashAttribute("alertaMensaje", "El monto no puede ser menor o igual a cero. Debe ingresar un monto válido.");
	            redirectAttributes.addFlashAttribute("alertaTipo", TipoAlerta.ERROR);
	            return "redirect:/depositar";
	        }

	        Usuario usuario = usuarioService.getByUsername(authentication.getName());
	        String correo = usuario.getCorreo();
	        try {
	            transaccionService.depositar(correo, monto);
	            redirectAttributes.addFlashAttribute("alertaTitulo", "Éxito");
	            redirectAttributes.addFlashAttribute("alertaMensaje", "El depósito se realizó exitosamente.");
	            redirectAttributes.addFlashAttribute("alertaTipo", TipoAlerta.SUCCESS);
	        } catch (IllegalArgumentException e) {
	            redirectAttributes.addFlashAttribute("alertaTitulo", "Error");
	            redirectAttributes.addFlashAttribute("alertaMensaje", e.getMessage());
	            redirectAttributes.addFlashAttribute("alertaTipo", TipoAlerta.ERROR);
	            return "redirect:/depositar";
	        }

	        return "redirect:/home";
	    }
	}