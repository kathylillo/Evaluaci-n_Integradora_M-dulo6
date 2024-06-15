package cl.evaluacion.AlkeWallet.model;

import lombok.Data;


/**
 * Clase que representa una moneda en el sistema.
 */
@Data
public class Moneda {
private int currencyId;
private String currencyName;
private String currencySymbol;
}
