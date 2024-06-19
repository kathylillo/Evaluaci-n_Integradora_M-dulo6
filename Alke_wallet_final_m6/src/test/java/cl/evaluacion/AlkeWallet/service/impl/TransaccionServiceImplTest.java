package cl.evaluacion.AlkeWallet.service.impl;

import cl.evaluacion.AlkeWallet.entity.TransaccionEntity;
import cl.evaluacion.AlkeWallet.entity.UsuarioEntity;
import cl.evaluacion.AlkeWallet.model.Transaccion;
import cl.evaluacion.AlkeWallet.repository.TransaccionRepository;
import cl.evaluacion.AlkeWallet.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para la clase TransaccionServiceImpl.
 */
public class TransaccionServiceImplTest {

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

    /**
     * Prueba para obtener el historial de transacciones de un usuario.
     */
    @Test
    @DisplayName("Test Obtener Historial de Transacciones")
    void testGetHistorial() {
        String correo = "juanpablo@mail.com";
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUserId(1);
        when(usuarioRepository.findByUsername(correo)).thenReturn(usuario);

        TransaccionEntity transaccionEntity = new TransaccionEntity();
        transaccionEntity.setSenderUserId(1);
        transaccionEntity.setReceiverUserId(2);
        transaccionEntity.setValor(100);
        transaccionEntity.setTransactionDate(new Timestamp(System.currentTimeMillis()));
        transaccionEntity.setCurrencyId(1);

        when(transaccionRepository.findBySenderUserIdOrReceiverUserId(1, 1))
            .thenReturn(Arrays.asList(transaccionEntity));

        List<Transaccion> historial = transaccionService.getHistorial(correo);

        assertNotNull(historial);
        assertEquals(1, historial.size());
        assertEquals(100, historial.get(0).getValor());

        verify(usuarioRepository, times(1)).findByUsername(correo);
        verify(transaccionRepository, times(1)).findBySenderUserIdOrReceiverUserId(1, 1);
    }

    /**
     * Prueba para obtener el historial de transacciones cuando el usuario no se encuentra.
     */
    @Test
    @DisplayName("Test Obtener Historial de Transacciones - Usuario No Encontrado")
    void testGetHistorialUserNotFound() {
        String correo = "pepito@mail.com";
        when(usuarioRepository.findByUsername(correo)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transaccionService.getHistorial(correo);
        });

        assertEquals("No se encontró ningún usuario con el correo electrónico proporcionado.", exception.getMessage());
        verify(usuarioRepository, times(1)).findByUsername(correo);
    }

    /**
     * Prueba para depositar saldo en la cuenta de un usuario.
     */
    @Test
    @DisplayName("Test Depositar Saldo")
    void testDepositar() {
        String correo = "juanpablo@mail.com";
        int monto = 1000;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUserId(1);
        when(usuarioRepository.findByUsername(correo)).thenReturn(usuario);

        doNothing().when(usuarioRepository).updateSaldo(1, monto);

        transaccionService.depositar(correo, monto);

        verify(usuarioRepository, times(1)).findByUsername(correo);
        verify(usuarioRepository, times(1)).updateSaldo(1, monto);
    }

    /**
     * Prueba para depositar saldo cuando el usuario no se encuentra o el monto es inválido.
     */
    @Test
    @DisplayName("Test Depositar Saldo - Usuario No Encontrado o Monto Inválido")
    void testDepositarUserNotFoundOrInvalidAmount() {
        String correo = "pepito@mail.com";
        int monto = -1000;
        when(usuarioRepository.findByUsername(correo)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transaccionService.depositar(correo, monto);
        });

        assertEquals("El usuario no existe o el monto a depositar debe ser mayor a cero.", exception.getMessage());
        verify(usuarioRepository, times(1)).findByUsername(correo);
    }

    /**
     * Prueba para retirar saldo de la cuenta de un usuario.
     */
    @Test
    @DisplayName("Test Retirar Saldo")
    void testRetirar() {
        String correo = "juanpablo@mail.com";
        int monto = 500;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUserId(1);
        usuario.setSaldo(1000);
        when(usuarioRepository.findByUsername(correo)).thenReturn(usuario);

        doNothing().when(usuarioRepository).updateSaldo(1, -monto);

        transaccionService.retirar(correo, monto);

        verify(usuarioRepository, times(1)).findByUsername(correo);
        verify(usuarioRepository, times(1)).updateSaldo(1, -monto);
    }

    /**
     * Prueba para retirar saldo cuando el usuario no se encuentra, el monto es inválido o no hay suficientes fondos.
     */
    @Test
    @DisplayName("Test Retirar Saldo - Usuario No Encontrado, Monto Inválido o Saldo Insuficiente")
    void testRetirarUserNotFoundOrInvalidAmountOrInsufficientFunds() {
        String correo = "juanpablo@mail.com";
        int monto = 1500;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUserId(1);
        usuario.setSaldo(1000);
        when(usuarioRepository.findByUsername(correo)).thenReturn(usuario);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transaccionService.retirar(correo, monto);
        });

        assertEquals("El usuario no existe, el monto a retirar debe ser mayor a cero o el saldo insuficiente.", exception.getMessage());
        verify(usuarioRepository, times(1)).findByUsername(correo);
    }

    /**
     * Prueba para transferir saldo entre dos usuarios.
     */
    @Test
    @DisplayName("Test Transferir Saldo")
    void testTransferir() {
        String correo = "juanpablo@mail.com";
        int receiverUserId = 2;
        int valor = 300;
        UsuarioEntity remitente = new UsuarioEntity();
        remitente.setUserId(1);
        remitente.setSaldo(1000);
        when(usuarioRepository.findByUsername(correo)).thenReturn(remitente);

        TransaccionEntity transaccionEntity = new TransaccionEntity();
        transaccionEntity.setSenderUserId(1);
        transaccionEntity.setReceiverUserId(receiverUserId);
        transaccionEntity.setValor(valor);
        transaccionEntity.setTransactionDate(new Timestamp(System.currentTimeMillis()));
        transaccionEntity.setCurrencyId(1);

        when(transaccionRepository.save(any(TransaccionEntity.class))).thenReturn(transaccionEntity);
        doNothing().when(usuarioRepository).updateSaldo(1, -valor);
        doNothing().when(usuarioRepository).updateSaldo(receiverUserId, valor);

        transaccionService.transferir(correo, receiverUserId, valor);

        verify(usuarioRepository, times(1)).findByUsername(correo);
        verify(transaccionRepository, times(1)).save(any(TransaccionEntity.class));
        verify(usuarioRepository, times(1)).updateSaldo(1, -valor);
        verify(usuarioRepository, times(1)).updateSaldo(receiverUserId, valor);
    }

    /**
     * Prueba para transferir saldo cuando el usuario no se encuentra o el monto es inválido.
     */
    @Test
    @DisplayName("Test Transferir Saldo - Usuario No Encontrado o Monto Inválido")
    void testTransferirUserNotFoundOrInvalidAmountOrInsufficientFunds() {
        String correo = "juanpablo@mail.com";
        int receiverUserId = 2;
        int valor = 1500;
        UsuarioEntity remitente = new UsuarioEntity();
        remitente.setUserId(1);
        remitente.setSaldo(1000);
        when(usuarioRepository.findByUsername(correo)).thenReturn(remitente);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transaccionService.transferir(correo, receiverUserId, valor);
        });

        assertEquals("El usuario remitente no tiene suficientes fondos para completar la transferencia.", exception.getMessage());
        verify(usuarioRepository, times(1)).findByUsername(correo);
    }
}