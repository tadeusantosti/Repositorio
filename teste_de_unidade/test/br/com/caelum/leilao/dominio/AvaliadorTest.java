package br.com.caelum.leilao.dominio;

import java.util.List;
import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import teste_de_unidade.Avaliador;
import teste_de_unidade.CriadorDeLeilao;
import teste_de_unidade.Lance;
import teste_de_unidade.Leilao;
import teste_de_unidade.Usuario;

public class AvaliadorTest {

    private Avaliador leiloeiro;

    @Before
    public void setUp() {
        this.leiloeiro = new Avaliador();
        System.out.println("inicializando teste!");
    }

    @After
    public void finaliza() {
        System.out.println("fim");
    }

    @BeforeClass
    public static void testandoBeforeClass() {
        System.out.println("before class");
    }

    @AfterClass
    public static void testandoAfterClass() {
        System.out.println("after class");
    }

    @Test(expected = RuntimeException.class)
    public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 3 Novo")
                .constroi();

        leiloeiro.avalia(leilao);
    }

    @Test
    public void deveReceberUmLance() {
        Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15").constroi();
        assertEquals(0, leilao.getLances().size());

        leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000));

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void deveReceberVariosLances() {

        Leilao leilao = new CriadorDeLeilao()
                .para("Macbook Pro 15")
                .lance(new Usuario("Steve Jobs"), 2000)
                .lance(new Usuario("Steve Wozniak"), 3000)
                .constroi();

        assertEquals(2, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
        assertEquals(3000.0, leilao.getLances().get(1).getValor(), 0.00001);
    }

    @Test
    public void validaUnicoLanceTest() {

        Leilao leilao = new CriadorDeLeilao().para("TV LED SMART 50' ").lance(new Usuario("Tadeu"), 200.0).constroi();

        setUp();
        leiloeiro.avalia(leilao);

        double maiorEsperado = 200;
        double menorEsperado = 200;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void validaLancesRandomicosTest() {

        Leilao leilao = new CriadorDeLeilao().para("TV LED SMART 50' ")
                .lance(new Usuario("Tadeu"), 200.0)
                .lance(new Usuario("Valeria"), 450.0)
                .lance(new Usuario("Juliana"), 120.0)
                .lance(new Usuario("Bianca"), 700.0)
                .lance(new Usuario("Luis"), 630.0)
                .lance(new Usuario("Cleverson"), 230.0).constroi();

        setUp();
        leiloeiro.avalia(leilao);

        double maiorEsperado = 700;
        double menorEsperado = 120;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
        Usuario steveJobs = new Usuario("Steve Jobs");
        Leilao leilao = new CriadorDeLeilao()
                .para("Macbook Pro 15")
                .lance(steveJobs, 2000.0)
                .lance(steveJobs, 3000.0)
                .constroi();

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void validaLancesOrdenadosTest() {
        Leilao leilao = new CriadorDeLeilao().para("TV LED SMART 50' ")
                .lance(new Usuario("Tadeu"), 1000.0)
                .lance(new Usuario("Valeria"), 900.0)
                .lance(new Usuario("Juliana"), 800.0)
                .lance(new Usuario("Bianca"), 700.0)
                .lance(new Usuario("Luis"), 600.0)
                .lance(new Usuario("Cleverson"), 500.0).constroi();

        setUp();
        List<Double> resultados = leiloeiro.armazenarLances(leilao);

        assertEquals(1000.0, resultados.get(0));
        assertEquals(900.0, resultados.get(1));
        assertEquals(800.0, resultados.get(2));
        assertEquals(700.0, resultados.get(3));
        assertEquals(600.0, resultados.get(4));
        assertEquals(500.0, resultados.get(5));

    }

    @Test
    public void validaOsTresMaioresLancesTest() {

        Leilao leilao = new CriadorDeLeilao().para("TV LED SMART 50' ")
                .lance(new Usuario("Tadeu"), 200.0)
                .lance(new Usuario("Valeria"), 450.0)
                .lance(new Usuario("Juliana"), 120.0)
                .lance(new Usuario("Bianca"), 700.0)
                .lance(new Usuario("Luis"), 630.0).constroi();

        setUp();
        leiloeiro.avalia(leilao);

        List<Lance> lances = leiloeiro.getTresMaiores();

        assertEquals(700.0, lances.get(0).getValor());
        assertEquals(630.0, lances.get(1).getValor());
        assertEquals(450.0, lances.get(2).getValor());
    }

    @Test
    public void validaOsDoisMaioresLancesTest() {

        Leilao leilao = new CriadorDeLeilao().para("TV LED SMART 50' ")
                .lance(new Usuario("Tadeu"), 700.0)
                .lance(new Usuario("Valeria"), 630.0).constroi();

        setUp();
        leiloeiro.avalia(leilao);

        List<Lance> lances = leiloeiro.getTresMaiores();

        assertEquals(700.0, lances.get(0).getValor());
        assertEquals(630.0, lances.get(1).getValor());
    }

}
