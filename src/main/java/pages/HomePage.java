package pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
	private WebDriver driver;
	
	List<WebElement> ListaProdutos = new ArrayList();
	
	private By produtos = By.className("product-description");
	public By textoProdutosCarrinho = By.className("cart-products-count");
	private By detalhes = By.cssSelector(".product-description a");
	private By preco = By.className("price");
	private By botaoSingIn = By.cssSelector("#_desktop_user_info .hidden-sm-down");
	private By usuarioLogado = By.cssSelector("#_desktop_user_info span.hidden-sm-down");
	private By botaoSignOut = By.cssSelector("a.logout");
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	public int contarProdutos() {
		carregarListaProdutos();
		return ListaProdutos.size();
	}
	private void carregarListaProdutos() {
		ListaProdutos = driver.findElements(produtos);
	}
	public int obterQuantidadeProdutosCarrinho() {
		String quantidadeProdutosCarrinho = driver.findElement(textoProdutosCarrinho).getText();
		quantidadeProdutosCarrinho = quantidadeProdutosCarrinho.replace("(", "");
		quantidadeProdutosCarrinho = quantidadeProdutosCarrinho.replace(")", "");
		int qtdProdutosCarrinho = Integer.parseInt(quantidadeProdutosCarrinho);
		return qtdProdutosCarrinho;
	}
	
	public String obterNomeProduto(int indice) {
		return driver.findElements(detalhes).get(indice).getText();
	}
	
	public String obterPrecoProduto(int indice) {
		return driver.findElements(preco).get(indice).getText();
	}
	
	public ProdutoPage clicarProduto(int indice) {
		driver.findElements(detalhes).get(indice).click();
		return new ProdutoPage(driver);
	}
	
	public LoginPage clicarBotaoSingIn() {
		driver.findElement(botaoSingIn).click();
		return new LoginPage(driver);
	}
	
	public boolean estaLogado(String texto) {
		return texto.contentEquals(driver.findElement(usuarioLogado).getText());
	}
	
	public void clicarBotaoSignOut() {
		driver.findElement(botaoSignOut).click();
	}
	public String obterTituloPagina() {
		return driver.getTitle();
	}
	public void carregarPaginaInicial() {
		driver.get("https://marcelodebittencourt.com/demoprestashop/");
	}
	public boolean estaLogado() {
		return !"Sign In".contentEquals(driver.findElement(usuarioLogado).getText());		
	}
}
