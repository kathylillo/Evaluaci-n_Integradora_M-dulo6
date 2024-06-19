package cl.evaluacion.AlkeWallet.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import cl.evaluacion.AlkeWallet.AlkeWalletApplication;
import cl.evaluacion.AlkeWallet.entity.UsuarioEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Pruebas unitarias para el repositorio de Usuario.
 */
@SpringBootTest(classes = AlkeWalletApplication.class)
public class UsuarioRepositoryTest {
	
	@Autowired
    private UsuarioRepository usuarioRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Prueba para verificar la búsqueda de usuario por nombre de usuario.
     */
    @DisplayName("Buscar usuario por nombre de usuario")
    @Test
    void testFindByUsername() {
        
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUserId(1);
        usuario.setNombre("Maria Soledad");
        usuario.setUsername("maria@mail.com");
        usuario.setPassword("54321");
        usuario.setSaldo(100);

        
        usuarioRepository.save(usuario);

       
        UsuarioEntity usuarioEncontrado = usuarioRepository.findByUsername("maria@mail.com");

        
        assertThat(usuarioEncontrado).isNotNull();
        assertThat(usuarioEncontrado.getUsername()).isEqualTo("maria@mail.com");
        assertThat(usuarioEncontrado.getUserId()).isEqualTo(1);
        assertThat(usuarioEncontrado.getNombre()).isEqualTo("Maria Soledad");
        assertThat(usuarioEncontrado.getSaldo()).isEqualTo(100);
    }

    /**
     * Prueba para actualizar el saldo del usuario.
     */
    @DisplayName("Actualizar saldo del usuario")
    @Test
    @Transactional
    void testUpdateSaldo() {
       
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUserId(1);
        usuario.setNombre("Maria Soledad");
        usuario.setUsername("maria@mail.com");
        usuario.setPassword("54321");
        usuario.setSaldo(100);

       
        usuarioRepository.save(usuario);

     
        usuarioRepository.updateSaldo(1, 50);
        entityManager.flush(); 

       
        UsuarioEntity usuarioActualizado = usuarioRepository.findById(1).orElse(null);

      
        assertThat(usuarioActualizado).isNotNull();
        assertThat(usuarioActualizado.getSaldo()).isEqualTo(100); 
    }
}