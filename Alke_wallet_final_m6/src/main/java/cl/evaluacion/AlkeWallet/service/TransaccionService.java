package cl.evaluacion.AlkeWallet.service;

import java.util.List;

import cl.evaluacion.AlkeWallet.model.Transaccion;
/**
 * Interfaz que define operaciones de servicio relacionadas con transacciones financieras.
 */
public interface TransaccionService {

    /**
     * Realiza un depósito de fondos en la cuenta asociada al correo especificado.
     * 
     * @param correo Correo electrónico asociado a la cuenta donde se realizará el depósito
     * @param monto  Monto a depositar
     */
    void depositar(String correo, int monto);

    /**
     * Realiza un retiro de fondos desde la cuenta asociada al correo especificado.
     * 
     * @param correo Correo electrónico asociado a la cuenta desde donde se realizará el retiro
     * @param monto  Monto a retirar
     */
    void retirar(String correo, int monto);

    /**
     * Realiza una transferencia de fondos desde la cuenta asociada al correo del remitente
     * a la cuenta identificada por el ID del receptor.
     * 
     * @param correo        Correo electrónico asociado a la cuenta del remitente
     * @param receiverUserId ID del usuario receptor de la transferencia
     * @param valor         Monto a transferir
     */
    void transferir(String correo, int receiverUserId, int valor);

    /**
     * Obtiene el historial de transacciones para el usuario identificado por el correo especificado.
     * 
     * @param correo Correo electrónico del usuario para el cual se obtendrá el historial de transacciones
     * @return Lista de objetos Transaccion representando el historial de transacciones
     */
    List<Transaccion> getHistorial(String correo);

}