package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import util.Funcoes;

public class PedidoPage {
	private WebDriver driver;
	private By confirmacao = By.cssSelector("#content-hook_order_confirmation h3");
	private By validarEmail = By.cssSelector("#content-hook_order_confirmation p");
	private By validarSoma= By.cssSelector("div.order-confirmation-table div.order-line div.row div.bold");
	private By validarTotal = By.cssSelector("div.order-confirmation-table table tr.total-value td:nth-child(2)");
	private By validarFormaPagamento = By.cssSelector("#order-details ul li:nth-child(2)");
	
	public PedidoPage (WebDriver driver){
		this.driver = driver;
	}
	
	public String obter_confirmacao() {
		return driver.findElement(confirmacao).getText();
	}
	
	public String validarEmail() {
		String texto = driver.findElement(validarEmail).getText();
		texto = Funcoes.removeTexto(texto, "An email has been sent to the ");
		texto = Funcoes.removeTexto(texto, " address.");
		return texto;
	}
	
	public Double validarSoma() {
		return Funcoes.removeCifraoDevolveDouble(driver.findElement(validarSoma).getText());
	}
	
	public Double validarTotal(){
		return Funcoes.removeCifraoDevolveDouble(driver.findElement(validarTotal).getText());
	}
	
	public String validarFormaPagamento() {
		String texto = driver.findElement(validarFormaPagamento).getText();
		texto = Funcoes.removeTexto(texto, "Payment method: ");
		return texto;
	}

}
