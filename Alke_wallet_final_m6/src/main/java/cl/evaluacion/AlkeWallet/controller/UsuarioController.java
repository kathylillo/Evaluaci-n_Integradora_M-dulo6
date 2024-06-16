package cl.evaluacion.AlkeWallet.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import cl.evaluacion.AlkeWallet.model.Usuario;
import cl.evaluacion.AlkeWallet.service.UsuarioService;

/**
 * Controlador para manejar las operaciones relacionadas con los usuarios en la aplicación.
 */
@Controller
public class UsuarioController {
	
	
	private final UsuarioService usuarioService;

	/**
	 * Constructor para inyectar el servicio de usuario.
	 *
	 * @param usuarioService Servicio para manejar los usuarios.
	 */
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService=usuarioService;
	}
	
	/**
	 * Maneja las solicitudes GET para la página de inicio de sesión.
	 *
	 * @param model Modelo para la vista.
	 * @param error Parámetro opcional para indicar si hubo un error en el inicio de sesión.
	 * @return Nombre de la vista para el inicio de sesión.
	 */
	@GetMapping("/login")
	public String login(Model model, @RequestParam(name = "error", required = false) String error) {
		if (error != null) {
			System.out.println("Error en el inicio de sesión: " + error);

		}
		return "login.jsp";
	}
	
	/**
	 * Maneja las solicitudes GET para la página de registro.
	 *
	 * @param creado Indica si el usuario fue creado exitosamente.
	 * @return Modelo y vista para la página de registro.
	 */
	@GetMapping("/registro")
	public ModelAndView formGet(@RequestParam(defaultValue = "false") boolean creado) {
		ModelAndView mav = new ModelAndView("registro.jsp");
		mav.addObject("creado", creado);
		mav.addObject("usuario", new Usuario());
		return mav;
	}

	/**
	 * Maneja las solicitudes POST para registrar un nuevo usuario.
	 *
	 * @param usuario Datos del usuario a registrar.
	 * @param model Modelo para la vista.
	 * @return Redirección a la página de registro con un parámetro que indica si el usuario fue creado.
	 */
	@PostMapping("/registro")
	public String formPost(@ModelAttribute Usuario usuario, Model model) {
		int userCreado = usuarioService.crear(usuario);
		model.addAttribute("creado", userCreado > 0);
		return "redirect:/registro?creado=" + (userCreado > 0);
	}

}
