package cl.evaluacion.AlkeWallet.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.evaluacion.AlkeWallet.entity.TransaccionEntity;
/**
 * Interfaz de repositorio para operaciones CRUD de entidades de transacción.
 */
@Repository
public interface TransaccionRepository extends CrudRepository<TransaccionEntity, Integer> {

	/**
     * Busca transacciones por el ID del usuario emisor o receptor.
     * 
     * @param senderUserId   ID del usuario emisor
     * @param receiverUserId ID del usuario receptor
     * @return Lista de transacciones encontradas
     */
	List<TransaccionEntity> findBySenderUserIdOrReceiverUserId(int senderUserId, int receiverUserId);
}
