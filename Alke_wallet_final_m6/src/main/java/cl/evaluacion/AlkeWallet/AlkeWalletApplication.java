package cl.evaluacion.AlkeWallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootApplication
public class AlkeWalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlkeWalletApplication.class, args);
		/*
		 String password = "Profe12345";
	        String hashedPassword = "$2a$10$H/kfkXxnbpoD86qnGHcwjO8Z0h6OqG01FCUgY9ppYAHWmdrR1pubO";
	        System.out.println("Contraseña en texto plano: " + password);
	        System.out.println("Hash almacenado en la base de datos: " + hashedPassword);

	        boolean passwordMatches = BCrypt.checkpw(password, hashedPassword);

	        if (passwordMatches) {
	            System.out.println("La contraseña coincide.");
	        } else {
	            System.out.println("La contraseña no coincide.");
	        }
	    }
	    */
	}
}
