package selenniumTest;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UsuariosPage {

    WebDriver driver;

    UsuariosPage(WebDriver driver) {
        this.driver = driver;
    }

    public void visita() {
        driver.get("http://localhost:8080/usuarios");
    }

    public NovoUsuarioPage novo() throws InterruptedException {

        driver.findElement(By.linkText("Novo Usuário")).click();
        Thread.sleep(3000);
        return new NovoUsuarioPage(driver);
    }

    public boolean existeNaListagem(String nome, String email) {
        
        return driver.getPageSource()
                .contains(nome)
                && driver.getPageSource()
                        .contains(email);

    }

    public void deletaUsuarioNaPosicao(int posicao) throws InterruptedException {
        driver.findElements(By.tagName("button")).get(posicao - 1).click();

        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public AlteraUsuarioPage altera(int posicao) throws InterruptedException {
        driver.findElements(By.linkText("editar")).get(posicao - 1).click();
        Thread.sleep(3000);
        return new AlteraUsuarioPage(driver);
    }

}
