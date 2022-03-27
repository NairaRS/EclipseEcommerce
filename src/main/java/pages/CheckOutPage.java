package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckOutPage {
	private WebDriver driver;
	private By valorTotal = By.cssSelector("div.cart-total span.value");
	private By nome = By.cssSelector("div.address");
	private By confirmar = By.name("confirm-addresses");
	private By shipping = By.cssSelector("span.carrier-price ");
	private By confirmarShipping = By.name("confirmDeliveryOption");
	private By pagamento= By.id("payment-option-1");
	private By valorFinal = By.cssSelector("#payment-option-1-additional-information > section> dl> dd:nth-child(2)");
	private By concordar = By.id("conditions_to_approve[terms-and-conditions]");
	private By confirmarPedido = By.cssSelector("#payment-confirmation button");
	
	public CheckOutPage (WebDriver driver){
		this.driver = driver;
	}
	
	public String obter_valorTotal(){
		return driver.findElement(valorTotal).getText();
	}
	
	public String obter_nome() {
		return driver.findElement(nome).getText();
	}
	
	public void botao_confirmar() {
		driver.findElement(confirmar).click();
	}
	
	public String obter_shipping() {
		return driver.findElement(shipping).getText();
	}
	
	public void botao_confirmarDelivery() {
		driver.findElement(confirmarShipping).click();
	}
	
	public void selecionar_pagamento() {
		driver.findElement(pagamento).click();
	}
	
	public String obter_valorFinal() {
		return driver.findElement(valorFinal).getText();
	}
	
	public void obter_concordar() {
		driver.findElement(concordar).click();
	}
	
	public boolean esta_Concordado(){
		return driver.findElement(concordar).isSelected();
	}
	
	public PedidoPage botaoFinalizar(){
		driver.findElement(confirmarPedido).click();
		return new PedidoPage(driver);
	}

}
