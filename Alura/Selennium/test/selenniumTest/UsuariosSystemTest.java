package selenniumTest;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class UsuariosSystemTest {

    private WebDriver driver;
    private UsuariosPage usuarios;

    @Before
    public void inicializaTest() {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\tadpi\\Documents\\Repositorio\\Alura\\libs\\Selenium\\MicrosoftWebDriver.exe");
        driver = new EdgeDriver();
        driver.get("http://localhost:8080/apenas-teste/limpa");
        this.usuarios = new UsuariosPage(driver);
        usuarios.visita();
    }

    @After
    public void encerraTest() {
        driver.close();
    }

    @Test
    public void deveAdicionarUmUsuario() throws InterruptedException {
        usuarios.novo()
                .cadastra("Adriano Xavier", "axavier@empresa.com.br");
        assertTrue(usuarios.existeNaListagem("Adriano Xavier", "Adriano Xavier"));
    }

    @Test
    public void naoDeveAdicionarUmUsuarioSemNome() throws InterruptedException {

        NovoUsuarioPage form = usuarios.novo();

        form.cadastra("", "ronaldo2009@terra.com.br");

        assertTrue(form.validacaoDeNomeObrigatorio());
    }

    @Test
    public void deveInserirUmNomeEEmail() throws InterruptedException {
        NovoUsuarioPage form = usuarios.novo();
        form.cadastra("", "");

        assertTrue(form.validacaoDeNomeeEmailObrigatorio());

    }

    @Test
    public void deveNavegarParaListagem() throws InterruptedException {
        driver.get("http://localhost:8080/usuarios");

        driver.findElement(By.linkText("Novo Usuário")).click();

    }

    @Test
    public void deveDeletarUmUsuario() throws InterruptedException {

        usuarios.novo().cadastra("Ronaldo Luiz de Albuquerque", "ronaldo2009@terra.com.br");
        assertTrue(usuarios.existeNaListagem("Ronaldo Luiz de Albuquerque", "ronaldo2009@terra.com.br"));

        usuarios.deletaUsuarioNaPosicao(1);

        assertFalse(usuarios.existeNaListagem("Ronaldo Luiz de Albuquerque", "ronaldo2009@terra.com.br"));
    }

    @Test
    public void deveAlterarUmUsuario() throws InterruptedException {

        usuarios.novo()
                .cadastra("Ronaldo Luiz de Albuquerque", "ronaldo2009@terra.com.br");
        usuarios.altera(1).para("José da Silva", "jose@silva.com");

        assertFalse(usuarios.existeNaListagem("Ronaldo Luiz de Albuquerque", "ronaldo2009@terra.com.br"));
        assertTrue(usuarios.existeNaListagem("José da Silva", "jose@silva.com"));
    }

}
