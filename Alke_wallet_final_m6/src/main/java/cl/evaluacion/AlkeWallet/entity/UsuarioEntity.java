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
 * Entidad que representa un usuario en la base de datos.
 */
@Entity
@Table(name = "usuario")
@Data
public class UsuarioEntity {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    /**
     * Nombre completo del usuario.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Nombre de usuario o correo electrónico.
     */
    @Column(name = "correo")
    private String username;

    /**
     * Contraseña del usuario.
     */
    @Column(name = "clave")
    private String password;

    /**
     * Saldo actual del usuario.
     */
    @Column(name = "saldo")
    private int saldo;

    /**
     * Fecha y hora de creación del usuario.
     */
    @Column(name = "fecha_de_creacion")
    private Timestamp fechaDeCreacion;
}