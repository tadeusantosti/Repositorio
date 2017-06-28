package selenniumTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AlteraUsuarioPage {

    private WebDriver driver;

    public AlteraUsuarioPage(WebDriver driver) {
        this.driver = driver;
    }

    public UsuariosPage para(String nome, String email) throws InterruptedException {
        WebElement txtNome = driver.findElement(By.name("usuario.nome"));
        WebElement txtEmail = driver.findElement(By.name("usuario.email"));

        txtNome.clear();
        txtEmail.clear();

        txtNome.sendKeys(nome);
        txtEmail.sendKeys(email);

        txtNome.submit();
        Thread.sleep(3000);
        return new UsuariosPage(driver);
    }
}
