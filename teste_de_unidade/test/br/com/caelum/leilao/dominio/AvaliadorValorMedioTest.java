package br.com.caelum.leilao.dominio;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import teste_de_unidade.Avaliador;
import teste_de_unidade.Lance;
import teste_de_unidade.Leilao;
import teste_de_unidade.Usuario;

public class AvaliadorValorMedioTest {
    	@Test
    public void main() {

        Usuario tadeu = new Usuario("Tadeu");        
        Usuario andressa = new Usuario("Andressa");

        Leilao leilao = new Leilao("TV LED SMART 50' ");

        leilao.propoe(new Lance(tadeu,200.0));
        leilao.propoe(new Lance(andressa,375.0));

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);
        leiloeiro.avaliador(leilao);


        double valorMedioEsperado = 287.50;        

        assertEquals(valorMedioEsperado, leiloeiro.getvalorMedio(), 0.001);
        
    }

}
