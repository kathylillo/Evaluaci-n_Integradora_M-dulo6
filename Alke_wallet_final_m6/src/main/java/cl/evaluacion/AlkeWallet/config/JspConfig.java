package cl.evaluacion.AlkeWallet.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Clase de configuración para la resolución de vistas JSP en Spring MVC.
 * Configura el resolver de vistas internas para manejar vistas JSP.
 */

@EnableWebMvc
@Configuration
public class JspConfig {

	/**
     * Configura y devuelve un bean de tipo InternalResourceViewResolver
     * para la resolución de vistas JSP.
     *
     * @return InternalResourceViewResolver configurado para manejar vistas JSP.
     */
	@Bean
	InternalResourceViewResolver jspViewResolver(){
	    final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	    viewResolver.setOrder(10);
	    viewResolver.setViewClass(JstlView.class);
	    viewResolver.setViewNames("*.jsp");
	    viewResolver.setPrefix("/WEB-INF/jsp/"); 
	    viewResolver.setSuffix("");
	    
	    return viewResolver;
	}
}