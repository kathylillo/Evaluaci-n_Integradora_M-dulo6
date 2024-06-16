package cl.evaluacion.AlkeWallet.model;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Clase que representa un Usuario en el sistema.
 */
@Data
public class Usuario {
private int user_Id;
private String nombre;
private String correo;
private String clave;
private int saldo;
private Timestamp fecha_de_creacion;
}
