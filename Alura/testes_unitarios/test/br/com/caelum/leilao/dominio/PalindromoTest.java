package br.com.caelum.leilao.dominio;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;
import teste_de_unidade.Palindromo;

public class PalindromoTest {

    @Test
    public void main() {

        String fraseVerdadeira = "Anotaram a data da maratona";
        String fraseFalsa = "Comeram pizza em cima da mesa";

        Palindromo palindromo = new Palindromo();

        assertTrue(palindromo.ehPalindromo(fraseVerdadeira));
        assertFalse(palindromo.ehPalindromo(fraseFalsa));

    }
}
