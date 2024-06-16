package cl.evaluacion.AlkeWallet.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import cl.evaluacion.AlkeWallet.AlkeWalletApplication;
import cl.evaluacion.AlkeWallet.entity.UsuarioEntity;
import cl.evaluacion.AlkeWallet.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@SpringBootTest(classes = AlkeWalletApplication.class)
public class UsuarioRepositoryTest {
	
	@Autowired
	    private UsuarioRepository usuarioRepository;

	    @PersistenceContext
	    private EntityManager entityManager;

	    @Test
	    public void testFindByUsername() {
	        
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

	    @Test
	    @Transactional
	    public void testUpdateSaldo() {
	       
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
