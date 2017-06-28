package selenniumTest;

import static junit.framework.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class LeiloesSystemTest {

    private WebDriver driver;
    private LeiloesPage leiloes;

    @Before
    public void inicializa() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\tadpi\\Documents\\Repositorio\\Alura\\libs\\Selenium\\MicrosoftWebDriver.exe");
        driver = new EdgeDriver();
        leiloes = new LeiloesPage(driver);

        UsuariosPage usuarios = new UsuariosPage(driver);
        usuarios.visita();
        usuarios.novo().cadastra("Paulo Henrique", "paulo@henrique.com");
    }

    @After
    public void encerraTest() {
        driver.close();
    }

    @Test
    public void deveCadastrarUmLeilao() throws InterruptedException {

        leiloes.visita();
        NovoLeilaoPage novoLeilao = leiloes.novo();
        novoLeilao.preenche("Geladeira", 123, "Paulo Henrique", true);

        assertTrue(leiloes.existe("Geladeira", 123, "Paulo Henrique", true));

    }

    @Test
    public void deveInserirUmNomeEValor() throws InterruptedException {
        leiloes.visita();
        NovoLeilaoPage novoLeilao = leiloes.novo();
        novoLeilao.preenche("", 0, "Paulo Henrique", true);

        assertTrue(novoLeilao.validacaoDeNomeeValorObrigatorio());

    }
}
