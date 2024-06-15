package cl.evaluacion.AlkeWallet.controller;

import java.util.Map;

import org.springframework.security.core.Authentication; 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import cl.evaluacion.AlkeWallet.model.Usuario;
import cl.evaluacion.AlkeWallet.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Controlador para manejar las operaciones relacionadas con la página de inicio en la aplicación.
 */
@Controller
public class HomeController {

	private final UsuarioService usuarioService;
	
	/**
     * Constructor para inyectar el servicio de usuario.
     *
     * @param usuarioService Servicio para manejar los usuarios.
     */
    public HomeController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

    /**
     * Maneja las solicitudes GET para la página de inicio.
     *
     * @param authentication Información de autenticación del usuario.
     * @param request La solicitud HTTP.
     * @return ModelAndView para la vista de inicio.
     */
    @GetMapping("/home")
    public ModelAndView home(Authentication authentication, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("home.jsp");

        
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("alertaTitulo", flashMap.get("alertaTitulo"));
            mav.addObject("alertaMensaje", flashMap.get("alertaMensaje"));
            mav.addObject("alertaTipo", flashMap.get("alertaTipo"));
        }

        Usuario usuario = usuarioService.getByUsername(authentication.getName());
        mav.addObject("usuario", usuario);

        Integer saldo = usuarioService.obtenerSaldoUsuario(authentication.getName());
        mav.addObject("saldo", saldo);

        return mav;
    }
}
    
   
