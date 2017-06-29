package selenniumTest;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LeiloesPage {

    private WebDriver driver;

    public LeiloesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void visita() {
        driver.get(new URLDaAplicacao().getUrlBase() + "/usuarios");
    }

    public NovoLeilaoPage novo() throws InterruptedException {
        
        driver.findElement(By.linkText("Novo Leilão")).click();
        Thread.sleep(100);
        return new NovoLeilaoPage(driver);
    }

    public boolean existe(String produto, double valor, String usuario,
            boolean usado) {

        return driver.getPageSource().contains(produto)
                && driver.getPageSource().contains(String.valueOf(valor))
                && driver.getPageSource().contains(usado ? "Sim" : "Não");

    }
    
     public DetalhesDoLeilaoPage detalhes(int posicao) throws InterruptedException {
        List<WebElement> elementos = driver.findElements(By.linkText("exibir"));
        elementos.get(posicao - 1).click();
        Thread.sleep(100);

        return new DetalhesDoLeilaoPage(driver);
    }
}
