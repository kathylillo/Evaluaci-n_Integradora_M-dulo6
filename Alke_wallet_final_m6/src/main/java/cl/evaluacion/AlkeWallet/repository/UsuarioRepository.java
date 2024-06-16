package cl.evaluacion.AlkeWallet.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.evaluacion.AlkeWallet.entity.UsuarioEntity;
import jakarta.transaction.Transactional;


/**
 * Interfaz de repositorio para operaciones CRUD y personalizadas de entidades de usuario.
 */
@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioEntity, Integer> {

	/**
     * Busca un usuario por su nombre de usuario (correo electrónico).
     * 
     * @param username Nombre de usuario (correo electrónico) del usuario a buscar
     * @return La entidad de usuario encontrada, o null si no se encuentra
     */
	@Query("SELECT u FROM UsuarioEntity u WHERE u.username = :correo")
	UsuarioEntity findByUsername(@Param("correo") String username);
	

	/**
     * Actualiza el saldo de un usuario sumando un monto específico.
     * 
     * @param userId ID del usuario cuyo saldo se actualizará
     * @param monto  Monto a sumar al saldo actual del usuario
     */
	@Modifying
	@Transactional
	@Query("UPDATE UsuarioEntity u SET u.saldo = u.saldo + :monto WHERE u.id = :userId")
	void updateSaldo(int userId, int monto);
}
