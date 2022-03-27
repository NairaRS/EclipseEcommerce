package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ProdutoPage {
	private WebDriver driver;
	private By nome = By.className("h1");
	private By preco = By.cssSelector(".current-price span:nth-child(1)");
	private By tamanho = By.id("group_1");
	private By inputCorPreta = By.xpath("//ul[@id='group_2']//input[@value='11']");
	private By qtd = By.id("quantity_wanted");
	private By addCarrinho = By.className("add-to-cart");
		
	public ProdutoPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String obterNomeProduto() {
		return driver.findElement(nome).getText();
	}
	
	public String obterPrecoProduto() {
		return driver.findElement(preco).getText();
	}
	
	public void selecionarOpcaoDropdown(String opcao) {
		encontrarDropdownSize().selectByVisibleText(opcao);
	}
	
	public List<String> ObterOpcoesSelecionadas(){
		List<WebElement> elementosSelecionados = encontrarDropdownSize().getAllSelectedOptions();
		List<String> listaOpcoesSelecionadas = new ArrayList();
		for (WebElement elemento : elementosSelecionados) {
			listaOpcoesSelecionadas.add(elemento.getText());
		}
		return listaOpcoesSelecionadas;
	}
	
	public Select encontrarDropdownSize() {
		return new Select(driver.findElement(tamanho));
	}
	
	public void selecionarCorPreta() {
		driver.findElement(inputCorPreta).click();
	}
	
	public void alterarQtd(int quant) {
		driver.findElement(qtd).clear();
		driver.findElement(qtd).sendKeys(Integer.toString(quant));
	}
	
	public ModalProductPage clicarBotaoAdd() {
		driver.findElement(addCarrinho).click();
		return new ModalProductPage(driver);
	}
	
}