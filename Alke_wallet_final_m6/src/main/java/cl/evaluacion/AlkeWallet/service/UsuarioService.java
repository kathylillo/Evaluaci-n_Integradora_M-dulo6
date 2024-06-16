package cl.evaluacion.AlkeWallet.service;

import java.util.List;

import cl.evaluacion.AlkeWallet.model.Usuario;

/**
 * Interfaz que define operaciones de servicio relacionadas con usuarios.
 */
public interface UsuarioService {

    /**
     * Crea un nuevo usuario en el sistema.
     * 
     * @param usuario Objeto Usuario que contiene los datos del nuevo usuario a crear
     * @return Valor entero que indica el resultado de la operación (generalmente un ID de usuario)
     */
    int crear(Usuario usuario);

    /**
     * Obtiene un usuario por su ID.
     * 
     * @param user_id ID del usuario a buscar
     * @return Objeto Usuario correspondiente al ID proporcionado
     */
    Usuario getById(int user_id);

    /**
     * Obtiene un usuario por su nombre de usuario (correo electrónico).
     * 
     * @param correo Nombre de usuario (correo electrónico) del usuario a buscar
     * @return Objeto Usuario correspondiente al nombre de usuario proporcionado
     */
    Usuario getByUsername(String correo);

    /**
     * Obtiene una lista de todos los usuarios registrados en el sistema.
     * 
     * @return Lista de objetos Usuario representando todos los usuarios registrados
     */
    List<Usuario> listado();

    /**
     * Obtiene el saldo actual del usuario identificado por su correo electrónico.
     * 
     * @param correo Correo electrónico del usuario para el cual se desea obtener el saldo
     * @return Saldo actual del usuario
     */
    int obtenerSaldoUsuario(String correo);
}