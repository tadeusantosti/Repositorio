package br.com.caelum.leilao.dominio;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import teste_de_unidade.CriadorDeLeilao;
import teste_de_unidade.Lance;
import teste_de_unidade.Leilao;
import teste_de_unidade.Usuario;

public class LanceTest {
    @Test(expected = IllegalArgumentException.class)
    public void testarLanceIgualZero(){
        Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15").constroi();        

        leilao.propoe(new Lance(new Usuario("Steve Jobs"), 0));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testarLanceMenorQueZero(){
        Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15").constroi();        

        leilao.propoe(new Lance(new Usuario("Steve Jobs"), -200.0));
    }
    
}
