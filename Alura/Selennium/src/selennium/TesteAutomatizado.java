package selennium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

public class TesteAutomatizado {  
    
    public static void main(String[] args) {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\tadpi\\Documents\\Repositorio\\Alura\\libs\\Selenium\\MicrosoftWebDriver.exe");

        // abre MS Edge
        WebDriver driver = new EdgeDriver();

        // acessa o site do google
        driver.get("http://www.bing.com/");

        // digita no campo com nome "q" do google
        WebElement campoDeTexto = driver.findElement(By.name("q"));
        campoDeTexto.sendKeys("Caelum");

        // submete o form
        campoDeTexto.submit();
        
        //C:\ProgramData\Oracle\Java\javapath

    }
   
}
