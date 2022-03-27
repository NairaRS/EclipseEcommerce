package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CarrinhoPage {
	private WebDriver driver;
	private By nomeProduto = By.cssSelector("div.product-line-info a");
	private By precoProduto = By.cssSelector("span.price");
	private By tamanhoProduto = By.xpath("//div[contains(@class,'product-line-grid-body')]//div[3]/span[contains(@class, 'value')]");
	private By corProduto = By.xpath("//div[contains(@class,'product-line-grid-body')]//div[4]/span[contains(@class, 'value')]");
	private By qtdProduto = By.cssSelector("input.js-cart-line-product-quantity");
	private By subtotal = By.cssSelector("span.product-price strong");
	private By itensTotal = By.cssSelector("span.js-subtotal");
	private By subTotalPainel= By.cssSelector("#cart-subtotal-products span.value");
	private By shippingPainel = By.cssSelector("#cart-subtotal-shipping span.value");
	private By totalPainel= By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(1) span.value");
	private By totalTotal = By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(2) span.value");
	private By taxaTotal = By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(3) span.value");
	private By botaoProceed = By.cssSelector("a.btn-primary");
	
		
	public CarrinhoPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String obter_nomeProduto() {
		return driver.findElement(nomeProduto).getText();
	}
	
	public String obter_precoProduto() {
		return driver.findElement(precoProduto).getText();
	}
	public String obter_tamanhoProduto() {
		return driver.findElement(tamanhoProduto).getText();
	}
	
	public String obter_corProduto() {
		return driver.findElement(corProduto).getText();
	}
	
	public String obter_qtdProduto() {
		return driver.findElement(qtdProduto).getAttribute("value");
	}
	
	public String obter_subtotal() {
		return driver.findElement(subtotal).getText();
	}
	
	public String obter_itensTotal() {
		return driver.findElement(itensTotal).getText();
	}
	
	public String obter_subTotalPainel() {
		return driver.findElement(subTotalPainel).getText();
	}
	
	public String obter_shippingPainel() {
		return driver.findElement(shippingPainel).getText();
	}
	
	public String obter_totalPainel() {
		return driver.findElement(totalPainel).getText();
	}
	
	public String obter_totalTotal() {
		return driver.findElement(totalTotal).getText();
	}
	
	public String obter_taxaTotal() {
		return driver.findElement(taxaTotal).getText();
	}
	
	public CheckOutPage botaoCheckOut() {
		driver.findElement(botaoProceed).click();
		return new CheckOutPage(driver);
	}
	
}
