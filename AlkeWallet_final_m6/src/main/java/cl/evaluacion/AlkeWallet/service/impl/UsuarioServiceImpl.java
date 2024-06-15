package cl.evaluacion.AlkeWallet.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.evaluacion.AlkeWallet.entity.UsuarioEntity;
import cl.evaluacion.AlkeWallet.model.Usuario;
import cl.evaluacion.AlkeWallet.repository.UsuarioRepository;
import cl.evaluacion.AlkeWallet.service.UsuarioService;
import lombok.extern.apachecommons.CommonsLog;

/**
 * Implementación del servicio UsuarioService y UserDetailsService que gestiona operaciones relacionadas con usuarios.
 */
@Service
@CommonsLog
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	 /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param usuario Objeto Usuario que contiene la información del nuevo usuario a crear
     * @return ID del usuario creado
     * @throws Exception Si ocurre algún error durante la creación del usuario
     */
	@Override
	@Transactional
	public int crear(Usuario usuario) {
		try {

			UsuarioEntity usuarioEntity = new UsuarioEntity();
			String hashPass = new BCryptPasswordEncoder().encode(usuario.getClave());

			usuarioEntity.setNombre(usuario.getNombre());
			usuarioEntity.setUsername(usuario.getCorreo());
			usuarioEntity.setFechaDeCreacion(usuario.getFecha_de_creacion());
			usuarioEntity.setPassword(hashPass);

			UsuarioEntity usuarioGuardado = usuarioRepository.save(usuarioEntity);

			return usuarioGuardado.getUserId();

		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw ex;
		}
	}


	/**
     * Obtiene un usuario por su ID.
     *
     * @param user_id ID del usuario a buscar
     * @return Objeto Usuario correspondiente al ID proporcionado, o null si no se encuentra
     */
	@Override
	public Usuario getById(int user_id) {
		UsuarioEntity usuarioEntity = usuarioRepository.findById(user_id).orElse(null);
		if (usuarioEntity == null)
			return null;

		Usuario usuario = new Usuario();
		usuario.setUser_Id(usuarioEntity.getUserId());
		usuario.setNombre(usuarioEntity.getNombre());
		usuario.setCorreo(usuarioEntity.getUsername());

		return usuario;
	}

	/**
     * Obtiene un listado de todos los usuarios registrados.
     *
     * @return Lista de objetos Usuario que representan todos los usuarios registrados
     * @throws Exception Si ocurre algún error durante la obtención del listado de usuarios
     */
	@Override
	public List<Usuario> listado() {
		try {

			List<Usuario> listado = new ArrayList<>();
			Iterable<UsuarioEntity> listadoEntities = usuarioRepository.findAll();

			for (UsuarioEntity usuarioEntity : listadoEntities) {
				Usuario usuario = new Usuario();
				usuario.setUser_Id(usuarioEntity.getUserId());
				usuario.setNombre(usuarioEntity.getNombre());
				usuario.setCorreo(usuarioEntity.getUsername());

				listado.add(usuario);
			}

			return listado;

		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw ex;
		}
	}

	/**
     * Carga un usuario por su nombre de usuario (correo electrónico).
     *
     * @param username Nombre de usuario (correo electrónico) del usuario a cargar
     * @return UserDetails que representa al usuario cargado
     * @throws UsernameNotFoundException Si no se encuentra ningún usuario con el nombre de usuario proporcionado
     */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			

			UsuarioEntity usuario = usuarioRepository.findByUsername(username);

			if (usuario != null) {
				
				List<GrantedAuthority> permissions = new ArrayList<>();
				return new User(usuario.getUsername(), usuario.getPassword(), permissions) {

				};
			} else {
				
				log.debug("Usuario no encontrado con el correo electrónico: " + username);
				throw new UsernameNotFoundException("Usuario no encontrado con el correo electrónico: " + username);
			}
			
		} catch (Exception ex) {
			log.error("Error al cargar usuario por nombre de usuario: " + ex.getMessage());
			throw new UsernameNotFoundException("Error al cargar usuario por nombre de usuario", ex);
		}
	}

	/**
     * Obtiene un usuario por su nombre de usuario (correo electrónico).
     *
     * @param correo Nombre de usuario (correo electrónico) del usuario a buscar
     * @return Objeto Usuario correspondiente al correo electrónico proporcionado, o null si no se encuentra
     */
	@Override
	public Usuario getByUsername(String correo) {
		UsuarioEntity usuarioEntity = usuarioRepository.findByUsername(correo);
		if (usuarioEntity == null)
			return null;

		Usuario usuario = new Usuario();
		usuario.setUser_Id(usuarioEntity.getUserId());
		usuario.setNombre(usuarioEntity.getNombre());
		usuario.setCorreo(usuarioEntity.getUsername());
		usuario.setSaldo(usuarioEntity.getSaldo());
		return usuario;
	}

	/**
     * Obtiene el saldo actual de un usuario por su nombre de usuario (correo electrónico).
     *
     * @param correo Nombre de usuario (correo electrónico) del usuario del cual se desea obtener el saldo
     * @return Saldo actual del usuario
     * @throws IllegalArgumentException Si no se encuentra ningún usuario con el correo electrónico proporcionado
     */
	@Override
	public int obtenerSaldoUsuario(String correo) {
		UsuarioEntity usuario = usuarioRepository.findByUsername(correo);
		if (usuario != null) {
			return usuario.getSaldo();
		} else {
			throw new IllegalArgumentException("El usuario no existe");
		}

	}
}