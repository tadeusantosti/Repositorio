package br.com.caelum.leilao.dominio;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.infra.dao.LeilaoDao;
import br.com.caelum.leilao.servico.EncerradorDeLeilao;
import br.com.caelum.leilao.servico.EnviadorDeEmail;
import br.com.caelum.leilao.servico.RepositorioDeLeiloes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.mockito.InOrder;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EncerradorDeLeilaoTest {

    @Test
    public void deveEncerrarLeiloesQueComecaramUmaSemanaAtras() {

        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
                .naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira")
                .naData(antiga).constroi();
        List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);

        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        when(daoFalso.correntes()).thenReturn(leiloesAntigos);

        EnviadorDeEmail carteiroFalso = mock(EnviadorDeEmail.class);

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, carteiroFalso);
        encerrador.encerra();

        assertTrue(leilao1.isEncerrado());
        assertTrue(leilao2.isEncerrado());
        assertEquals(2, encerrador.getTotalEncerrados());
    }

    @Test
    public void naoDeveEncerrarLeiloesQueComecaramMenosDeUmaSemanaAtras() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(Calendar.DAY_OF_MONTH, -1);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
                .naData(antiga).constroi();

        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        EnviadorDeEmail carteiroFalso = mock(EnviadorDeEmail.class);
        when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1));

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, carteiroFalso);
        encerrador.encerra();

        assertEquals(1, encerrador.getTotalEncerrados());
        assertTrue(leilao1.isEncerrado());

    }

    @Test
    public void naoDeveEncerrarLeiloesCasoNaoHajaNenhum() {

        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        when(daoFalso.correntes()).thenReturn(new ArrayList<Leilao>());
        EnviadorDeEmail carteiroFalso = mock(EnviadorDeEmail.class);

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, carteiroFalso);
        encerrador.encerra();
        
        assertEquals(0, encerrador.getTotalEncerrados());
    }

    @Test
    public void deveAtualizarLeiloesEncerrados() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(antiga).constroi();

        RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
        when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1));
        EnviadorDeEmail carteiroFalso = mock(EnviadorDeEmail.class);

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, carteiroFalso);
        encerrador.encerra();
        InOrder inOrder = inOrder(daoFalso, carteiroFalso);
        inOrder.verify(daoFalso, times(1)).atualiza(leilao1);
    }

    @Test
    public void naoDeveEncerrarLeiloesQueComecaramMenosDeUmaSemanaAtrasParte2() {

        Calendar ontem = Calendar.getInstance();
        ontem.add(Calendar.DAY_OF_MONTH, -1);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
                .naData(ontem).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira")
                .naData(ontem).constroi();

        RepositorioDeLeiloes daoFalso = mock(LeilaoDao.class);
        when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));
        EnviadorDeEmail carteiroFalso = mock(EnviadorDeEmail.class);

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, carteiroFalso);
        encerrador.encerra();

        assertEquals(0, encerrador.getTotalEncerrados());
        assertFalse(leilao1.isEncerrado());
        assertFalse(leilao2.isEncerrado());

        InOrder inOrder = inOrder(daoFalso, carteiroFalso);
        inOrder.verify(daoFalso, never()).atualiza(leilao1);
        inOrder.verify(daoFalso, never()).atualiza(leilao2);
    }
}
