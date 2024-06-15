package cl.evaluacion.AlkeWallet.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entidad que representa una transacción en la base de datos.
 */
@Entity
@Table(name = "transaccion")
@Data
public class TransaccionEntity {

  

	/**
     * Identificador único de la transacción.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transaccionId;

    /**
     * Identificador del usuario remitente.
     */
    @Column(name = "sender_user_id")
    private int senderUserId;

    /**
     * Identificador del usuario receptor.
     */
    @Column(name = "receiver_user_id")
    private int receiverUserId;

    /**
     * Valor de la transacción.
     */
    @Column(name = "valor")
    private int valor;

    /**
     * Fecha y hora en que se realizó la transacción.
     */
    @Column(name = "transaction_date")
    private Timestamp transactionDate;

    /**
     * Identificador de la moneda utilizada en la transacción.
     */
    @Column(name = "currency_id")
    private int currencyId;
    
    
}