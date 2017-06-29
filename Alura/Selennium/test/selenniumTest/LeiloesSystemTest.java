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
    private DetalhesDoLeilaoPage lances;

    @Before
    public void inicializa() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\tadpi\\Documents\\Repositorio\\Alura\\libs\\Selenium\\MicrosoftWebDriver.exe");
        driver = new EdgeDriver();
        driver.get("http://localhost:8080/apenas-teste/limpa");
        lances = new DetalhesDoLeilaoPage(driver);
        leiloes = new LeiloesPage(driver);

        new CriadorDeCenarios(driver)
                .umUsuario("Paulo Henrique", "paulo@henrique.com")
                .umUsuario("José Alberto", "jose@alberto.com")
                .umLeilao("Paulo Henrique", "Geladeira", 100, false);

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

    @Test
    public void deveFazerUmLance() throws InterruptedException {

        DetalhesDoLeilaoPage lances = leiloes.detalhes(1);

        lances.lance("José Alberto", 150);

        assertTrue(lances.existeLance("José Alberto", 150));
    }
}
