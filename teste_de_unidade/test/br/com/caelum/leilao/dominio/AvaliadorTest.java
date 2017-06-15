package br.com.caelum.leilao.dominio;

import java.util.List;
import junit.framework.Assert;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import teste_de_unidade.Avaliador;
import teste_de_unidade.Lance;
import teste_de_unidade.Leilao;
import teste_de_unidade.Usuario;

public class AvaliadorTest {

    @Test
    public void main() {

        Usuario tadeu = new Usuario("Tadeu");
        Usuario roberto = new Usuario("Roberto");
        Usuario andressa = new Usuario("Andressa");

        Leilao leilao = new Leilao("TV LED SMART 50' ");

        leilao.propoe(new Lance(andressa, 250.0));
        leilao.propoe(new Lance(tadeu, 300.0));
        leilao.propoe(new Lance(roberto, 400.0));

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        double maiorEsperado = 400;
        double menorEsperado = 250;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void validaUnicoLanceTest() {

        Usuario tadeu = new Usuario("Tadeu");

        Leilao leilao = new Leilao("TV LED SMART 50' ");

        leilao.propoe(new Lance(tadeu, 200.0));

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        double maiorEsperado = 200;
        double menorEsperado = 200;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void validaLancesRandomicosTest() {

        Usuario tadeu = new Usuario("Tadeu");
        Usuario valeria = new Usuario("Valeria");
        Usuario juliana = new Usuario("Juliana");
        Usuario bianca = new Usuario("Bianca");
        Usuario luis = new Usuario("Luis");
        Usuario cleverson = new Usuario("Cleverson");

        Leilao leilao = new Leilao("TV LED SMART 50' ");

        leilao.propoe(new Lance(tadeu, 200.0));
        leilao.propoe(new Lance(valeria, 450.0));
        leilao.propoe(new Lance(juliana, 120.0));
        leilao.propoe(new Lance(bianca, 700.0));
        leilao.propoe(new Lance(luis, 630.0));
        leilao.propoe(new Lance(cleverson, 230.0));

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        double maiorEsperado = 700;
        double menorEsperado = 120;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void validaLancesOrdenadosTest() {

        Usuario tadeu = new Usuario("Tadeu");
        Usuario valeria = new Usuario("Valeria");
        Usuario juliana = new Usuario("Juliana");
        Usuario bianca = new Usuario("Bianca");
        Usuario luis = new Usuario("Luis");
        Usuario cleverson = new Usuario("Cleverson");

        Leilao leilao = new Leilao("TV LED SMART 50' ");

        leilao.propoe(new Lance(tadeu, 1000.0));
        leilao.propoe(new Lance(valeria, 900.0));
        leilao.propoe(new Lance(juliana, 800.0));
        leilao.propoe(new Lance(bianca, 700.0));
        leilao.propoe(new Lance(luis, 600.0));
        leilao.propoe(new Lance(cleverson, 500.0));

        Avaliador leiloeiro = new Avaliador();
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

        Usuario tadeu = new Usuario("Tadeu");
        Usuario valeria = new Usuario("Valeria");
        Usuario juliana = new Usuario("Juliana");
        Usuario bianca = new Usuario("Bianca");
        Usuario luis = new Usuario("Luis");

        Leilao leilao = new Leilao("TV LED SMART 50' ");

        leilao.propoe(new Lance(tadeu, 200.0));
        leilao.propoe(new Lance(valeria, 450.0));
        leilao.propoe(new Lance(juliana, 120.0));
        leilao.propoe(new Lance(bianca, 700.0));
        leilao.propoe(new Lance(luis, 630.0));

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        List<Lance> lances = leiloeiro.getTresMaiores();

        assertEquals(700.0, lances.get(0).getValor());
        assertEquals(630.0, lances.get(1).getValor());
        assertEquals(450.0, lances.get(2).getValor());
    }

    @Test
    public void validaOsDoisMaioresLancesTest() {

        Usuario tadeu = new Usuario("Tadeu");
        Usuario valeria = new Usuario("Valeria");

        Leilao leilao = new Leilao("TV LED SMART 50' ");

        leilao.propoe(new Lance(tadeu, 700.0));
        leilao.propoe(new Lance(valeria, 630.0));

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        List<Lance> lances = leiloeiro.getTresMaiores();

        assertEquals(700.0, lances.get(0).getValor());
        assertEquals(630.0, lances.get(1).getValor());
    }

    @Test
    public void validaListaVaziaTest() {

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(new Leilao(null));

        List<Lance> lances = leiloeiro.getTresMaiores();

        assertEquals(0, lances.size());

    }

}
