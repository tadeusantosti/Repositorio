package selenniumTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NovoUsuarioPage {

    private WebDriver driver;

    public NovoUsuarioPage(WebDriver driver) {
        this.driver = driver;

    }

    public void cadastra(String nome, String email) throws InterruptedException {
        WebElement txtNome = driver.findElement(By.name("usuario.nome"));
        WebElement txtEmail = driver.findElement(By.name("usuario.email"));

        txtNome.sendKeys(nome);
        txtEmail.sendKeys(email);

        txtNome.submit();

        Thread.sleep(100);

    }

    public boolean validacaoDeNomeObrigatorio() {
        return driver.getPageSource().contains("Nome obrigatorio!");
    }

    public boolean validacaoDeNomeeEmailObrigatorio() {
        return driver.getPageSource().contains("Nome obrigatorio!") && driver.getPageSource().contains("E-mail obrigatorio!");
    }
}
