package br.com.caelum.leilao.dominio;

import org.junit.Assert;
import org.junit.Test;
import teste_de_unidade.AnoBissexto;

public class AnoBissextoTest {

    @Test
    public void verificarAnoBissextoTest() {
        AnoBissexto anoBissexto = new AnoBissexto();

        Assert.assertTrue(anoBissexto.ehBissexto(2016));
        Assert.assertFalse(anoBissexto.ehBissexto(2015));
    }

}
