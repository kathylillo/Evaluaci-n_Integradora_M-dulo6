package cl.evaluacion.AlkeWallet.service.impl;

import java.sql.Timestamp; 
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cl.evaluacion.AlkeWallet.entity.TransaccionEntity;
import cl.evaluacion.AlkeWallet.entity.UsuarioEntity;
import cl.evaluacion.AlkeWallet.model.Transaccion;
import cl.evaluacion.AlkeWallet.repository.TransaccionRepository;
import cl.evaluacion.AlkeWallet.repository.UsuarioRepository;
import cl.evaluacion.AlkeWallet.service.TransaccionService;

/**
 * Implementación del servicio TransaccionService que gestiona operaciones relacionadas con transacciones.
 */
@Service
public class TransaccionServiceImpl implements TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final UsuarioRepository usuarioRepository;

    public TransaccionServiceImpl(TransaccionRepository transaccionRepository, UsuarioRepository usuarioRepository) {
        this.transaccionRepository = transaccionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Obtiene el historial de transacciones para un usuario dado su correo electrónico.
     *
     * @param correo Correo electrónico del usuario
     * @return Lista de objetos Transaccion que representan el historial de transacciones del usuario
     * @throws IllegalArgumentException Si no se encuentra ningún usuario con el correo electrónico proporcionado
     */
    @Override
    public List<Transaccion> getHistorial(String correo) {
        UsuarioEntity usuario = usuarioRepository.findByUsername(correo);

        if (usuario == null) {
            throw new IllegalArgumentException("No se encontró ningún usuario con el correo electrónico proporcionado.");
        }

        int userId = usuario.getUserId();

        List<TransaccionEntity> transaccionEntities = transaccionRepository.findBySenderUserIdOrReceiverUserId(userId, userId);

        return transaccionEntities.stream().map(this::convertToModel).collect(Collectors.toList());
    }

    /**
     * Convierte una entidad TransaccionEntity a un objeto modelo Transaccion.
     *
     * @param entity Entidad TransaccionEntity
     * @return Objeto Transaccion correspondiente a la entidad proporcionada
     */
    private Transaccion convertToModel(TransaccionEntity entity) {
        Transaccion transaccion = new Transaccion();
        transaccion.setTransaction_Id(entity.getTransaccionId());
        transaccion.setSender_User_Id(entity.getSenderUserId());
        transaccion.setReceiver_User_Id(entity.getReceiverUserId());
        transaccion.setValor(entity.getValor());
        transaccion.setCurrency_Id(entity.getCurrencyId());
        transaccion.setTransaction_Date(entity.getTransactionDate());
        return transaccion;
    }

    /**
     * Realiza un depósito en la cuenta del usuario identificado por su correo electrónico.
     *
     * @param correo Correo electrónico del usuario
     * @param monto  Monto a depositar
     * @throws IllegalArgumentException Si el usuario no existe o el monto a depositar es menor o igual a cero
     */
    @Override
    public void depositar(String correo, int monto) {
        UsuarioEntity usuario = usuarioRepository.findByUsername(correo);

        if (usuario != null && monto > 0) {
            usuarioRepository.updateSaldo(usuario.getUserId(), +monto);
        } else {
            throw new IllegalArgumentException("El usuario no existe o el monto a depositar debe ser mayor a cero.");
        }
    }

    /**
     * Realiza un retiro desde la cuenta del usuario identificado por su correo electrónico.
     *
     * @param correo Correo electrónico del usuario
     * @param monto  Monto a retirar
     * @throws IllegalArgumentException Si el usuario no existe, el monto a retirar es menor o igual a cero, o el saldo es insuficiente
     */
    @Override
    public void retirar(String correo, int monto) {
        UsuarioEntity usuario = usuarioRepository.findByUsername(correo);

        if (usuario != null && monto > 0 && usuario.getSaldo() >= monto) {
            usuarioRepository.updateSaldo(usuario.getUserId(), -monto);
        } else {
            throw new IllegalArgumentException("El usuario no existe, el monto a retirar debe ser mayor a cero o el saldo insuficiente.");
        }
    }

    /**
     * Realiza una transferencia de saldo desde el usuario remitente al usuario destinatario.
     *
     * @param correo         Correo electrónico del usuario remitente
     * @param receiverUserId ID del usuario destinatario
     * @param valor          Monto a transferir
     * @throws IllegalArgumentException Si el usuario remitente no existe, el valor es menor o igual a cero, o el saldo del remitente es insuficiente
     */
    @Override
    public void transferir(String correo, int receiverUserId, int valor) {
        UsuarioEntity remitente = usuarioRepository.findByUsername(correo);

        if (remitente == null) {
            throw new IllegalArgumentException("El usuario remitente no existe.");
        }

        if (valor <= 0) {
            throw new IllegalArgumentException("El valor a depositar debe ser mayor que cero.");
        }

        if (remitente.getSaldo() < valor) {
            throw new IllegalArgumentException("El usuario remitente no tiene suficientes fondos para completar la transferencia.");
        }

        TransaccionEntity transaccion = new TransaccionEntity();
        transaccion.setSenderUserId(remitente.getUserId());
        transaccion.setReceiverUserId(receiverUserId);
        transaccion.setValor(valor);
        transaccion.setTransactionDate(new Timestamp(System.currentTimeMillis()));
        transaccion.setCurrencyId(1);
        transaccionRepository.save(transaccion);

        usuarioRepository.updateSaldo(remitente.getUserId(), -valor);
        usuarioRepository.updateSaldo(receiverUserId, +valor);
    }
}