package selenniumTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LeiloesPage {

    private WebDriver driver;

    public LeiloesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void visita() {
        driver.get("http://localhost:8080/leiloes");
    }

    public NovoLeilaoPage novo() throws InterruptedException {
        
        driver.findElement(By.linkText("Novo Leilão")).click();
        Thread.sleep(3000);
        return new NovoLeilaoPage(driver);
    }

    public boolean existe(String produto, double valor, String usuario,
            boolean usado) {

        return driver.getPageSource().contains(produto)
                && driver.getPageSource().contains(String.valueOf(valor))
                && driver.getPageSource().contains(usado ? "Sim" : "Não");

    }
}
