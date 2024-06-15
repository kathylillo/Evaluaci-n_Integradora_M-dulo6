package cl.evaluacion.AlkeWallet.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import cl.evaluacion.AlkeWallet.AlkeWalletApplication;
import cl.evaluacion.AlkeWallet.entity.TransaccionEntity;
import cl.evaluacion.AlkeWallet.repository.TransaccionRepository;

import static org.mockito.Mockito.*;


@SpringBootTest(classes = AlkeWalletApplication.class)
public class TransaccionRepositoryTest {
	
	@MockBean
    private TransaccionRepository transaccionRepository;

    @Test
    void testFindBySenderUserIdOrReceiverUserId() {
        TransaccionEntity transaccion1 = new TransaccionEntity();
        transaccion1.setTransaccionId(1);
        transaccion1.setSenderUserId(1);
        transaccion1.setReceiverUserId(2);

        TransaccionEntity transaccion2 = new TransaccionEntity();
        transaccion2.setTransaccionId(2);
        transaccion2.setSenderUserId(2);
        transaccion2.setReceiverUserId(1);

        List<TransaccionEntity> transacciones = Arrays.asList(transaccion1, transaccion2);

        when(transaccionRepository.findBySenderUserIdOrReceiverUserId(1, 2)).thenReturn(transacciones);

        List<TransaccionEntity> result = transaccionRepository.findBySenderUserIdOrReceiverUserId(1, 2);

        assertThat(result).hasSize(2);
        assertThat(result).contains(transaccion1, transaccion2);
    }
}