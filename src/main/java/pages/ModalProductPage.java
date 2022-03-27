package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class ModalProductPage {
	private WebDriver driver;
	private By mensagemProdutoADD = By.id("myModalLabel");
	private By descricaoProduto = By.cssSelector("div.col-md-6 h6");
	private By descricaoPreco = By.cssSelector("div.modal-body p.product-price");
	private By descricaoValoresLista = By.cssSelector("div.divide-right span strong");
	private By totalValoresLista = By.cssSelector("div.cart-content span.value");
	private By checkout = By.cssSelector("a.btn-primary");
	
	
	public ModalProductPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String obterConfirmacao() {
		FluentWait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(5))
				.pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(mensagemProdutoADD));
		//ESPERA EXPL�CITA
		return driver.findElement(mensagemProdutoADD).getText();
	}
	
	public String obterDescricaoProduto() {
		return driver.findElement(descricaoProduto).getText();
	}
	
	public String obterDescricaoPreco() {
		return driver.findElement(descricaoPreco).getText();
	}
	
	public String obterTamanhoProduto() {
		return driver.findElements(descricaoValoresLista).get(0).getText();
	}
	
	public String obterCorProduto() {
		if(driver.findElements(descricaoValoresLista).size()==3) {
			return driver.findElements(descricaoValoresLista).get(1).getText();
		}
		else {
			return "N/A";
		}
	}
	
	public String obterQtdProduto() {
		if(driver.findElements(descricaoValoresLista).size()==3) {
			return driver.findElements(descricaoValoresLista).get(2).getText();
		}
		else {
			return driver.findElements(descricaoValoresLista).get(1).getText();
		}
	}
	
	public String obterSubTotal() {
		return driver.findElements(totalValoresLista).get(0).getText();
	}
	
	public String obterEnvio() {
		return driver.findElements(totalValoresLista).get(1).getText();
	}
	
	public String obterTotal() {
		return driver.findElements(totalValoresLista).get(2).getText();
	}
	
	public String obterImposto() {
		return driver.findElements(totalValoresLista).get(3).getText();
	}
	
	public CarrinhoPage clicarBotaoProceedToCheckout() {
		driver.findElement(checkout).click();
		return new CarrinhoPage(driver);
	}
}
