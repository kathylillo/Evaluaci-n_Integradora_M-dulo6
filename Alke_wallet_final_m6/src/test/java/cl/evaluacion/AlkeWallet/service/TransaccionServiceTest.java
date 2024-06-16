package cl.evaluacion.AlkeWallet.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cl.evaluacion.AlkeWallet.entity.TransaccionEntity;
import cl.evaluacion.AlkeWallet.entity.UsuarioEntity;
import cl.evaluacion.AlkeWallet.model.Transaccion;
import cl.evaluacion.AlkeWallet.repository.TransaccionRepository;
import cl.evaluacion.AlkeWallet.repository.UsuarioRepository;
import cl.evaluacion.AlkeWallet.service.impl.TransaccionServiceImpl;

import org.springframework.boot.test.context.SpringBootTest;
import cl.evaluacion.AlkeWallet.AlkeWalletApplication;

@SpringBootTest(classes = AlkeWalletApplication.class)
public class TransaccionServiceTest {

	@Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TransaccionServiceImpl transaccionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDepositar() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUserId(1);
        usuario.setUsername("juanpablo@mail.com");
        usuario.setSaldo(1000);

        when(usuarioRepository.findByUsername("juanpablo@mail.com")).thenReturn(usuario);

        doNothing().when(usuarioRepository).updateSaldo(1, 1000);

        transaccionService.depositar("juanpablo@mail.com", 1000);

        verify(usuarioRepository).updateSaldo(1, 1000);
    }

    @Test
    void testDepositarUsuarioNoExistente() {
        when(usuarioRepository.findByUsername("juanpabl@mail.com")).thenReturn(null);

        assertThatThrownBy(() -> transaccionService.depositar("juanpabl@mail.com", 1000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El usuario no existe o el monto a depositar debe ser mayor a cero.");
    }

    @Test
    void testRetirar() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUserId(1);
        usuario.setUsername("juanpablo@mail.com");
        usuario.setSaldo(1000);

        when(usuarioRepository.findByUsername("juanpablo@mail.com")).thenReturn(usuario);

        doNothing().when(usuarioRepository).updateSaldo(1, -500);

        transaccionService.retirar("juanpablo@mail.com", 500);

        verify(usuarioRepository).updateSaldo(1, -500);
    }

    @Test
    void testRetirarSaldoInsuficiente() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUserId(1);
        usuario.setUsername("juanpablo@mail.com");
        usuario.setSaldo(100);

        when(usuarioRepository.findByUsername("juanpablo@mail.com")).thenReturn(usuario);

        assertThatThrownBy(() -> transaccionService.retirar("juanpablo@mail.com", 500))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El usuario no existe, el monto a retirar debe ser mayor a cero o el saldo insuficiente.");
    }

    @Test
    void testTransferir() {
        UsuarioEntity remitente = new UsuarioEntity();
        remitente.setUserId(1);
        remitente.setUsername("juanpablo@mail.com");
        remitente.setSaldo(1000);

        UsuarioEntity destinatario = new UsuarioEntity();
        destinatario.setUserId(2);
        destinatario.setUsername("mperez@mail.com");
        destinatario.setSaldo(500);

        when(usuarioRepository.findByUsername("juanpablo@mail.com")).thenReturn(remitente);

        doNothing().when(usuarioRepository).updateSaldo(1, -300);
        doNothing().when(usuarioRepository).updateSaldo(2, 300);

        transaccionService.transferir("juanpablo@mail.com", 2, 300);

        verify(usuarioRepository).updateSaldo(1, -300);
        verify(usuarioRepository).updateSaldo(2, 300);
        verify(transaccionRepository).save(any(TransaccionEntity.class));
    }

    @Test
    void testTransferirSaldoInsuficiente() {
        UsuarioEntity remitente = new UsuarioEntity();
        remitente.setUserId(1);
        remitente.setUsername("juanpablo@mail.com");
        remitente.setSaldo(100);

        when(usuarioRepository.findByUsername("juanpablo@mail.com")).thenReturn(remitente);

        assertThatThrownBy(() -> transaccionService.transferir("juanpablo@mail.com", 2, 300))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El usuario remitente no tiene suficientes fondos para completar la transferencia.");
    }

    @Test
    void testGetHistorial() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUserId(1);
        usuario.setUsername("juanpablo@mail.com");

        when(usuarioRepository.findByUsername("juanpablo@mail.com")).thenReturn(usuario);

        TransaccionEntity transaccion1 = new TransaccionEntity();
        transaccion1.setTransaccionId(1);
        transaccion1.setSenderUserId(1);
        transaccion1.setReceiverUserId(2);
        transaccion1.setValor(1000);

        TransaccionEntity transaccion2 = new TransaccionEntity();
        transaccion2.setTransaccionId(2);
        transaccion2.setSenderUserId(1);
        transaccion2.setReceiverUserId(3);
        transaccion2.setValor(500);

        List<TransaccionEntity> transacciones = Arrays.asList(transaccion1, transaccion2);

        when(transaccionRepository.findBySenderUserIdOrReceiverUserId(1, 1)).thenReturn(transacciones);

        List<Transaccion> result = transaccionService.getHistorial("juanpablo@mail.com");

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTransaction_Id()).isEqualTo(1);
        assertThat(result.get(1).getTransaction_Id()).isEqualTo(2);
    }

    @Test
    void testGetHistorialUsuarioNoExistente() {
        when(usuarioRepository.findByUsername("juanpabl@mail.com")).thenReturn(null);

        assertThatThrownBy(() -> transaccionService.getHistorial("juanpabl@mail.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No se encontró ningún usuario con el correo electrónico proporcionado.");
    }
}