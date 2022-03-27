package steps;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.io.Files;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import pages.HomePage;
import pages.LoginPage;
import pages.ModalProductPage;
import pages.ProdutoPage;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;

public class CompraProdutosSteps {
	private static WebDriver driver;
	private HomePage homePage = new HomePage(driver);
	
	@Before
	public static void inicializar() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\naira\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver(); 	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//ESPERA IMPL�CITA
	}

	@Dado("que estou na pagina inicial")
	public void que_estou_na_pagina_inicial() {
	   homePage.carregarPaginaInicial();
	   //assertThat(homePage.obterTituloPagina(), is("Loja de Teste"));
	}

	@Quando("nao estou logado")
	public void nao_estou_logado() {
	   homePage.estaLogado();
	    //assertThat(homePage.estaLogado(), is(false));
	}

	@Então("visualizo {int} produtos disponiveis")
	public void visualizo_produtos_disponiveis(Integer int1) {
		//assertThat(homePage.contarProdutos(), is(int1));
	}

	@Então("carrinho esta zerado")
	public void carrinho_esta_zerado() {
	    //assertThat(homePage.obterQuantidadeProdutosCarrinho(), is(0));
	}
	
	LoginPage loginPage;
	@Quando("estou logado")
	public void estou_logado() {
		//Clicar bot�o sing in na homepage
		loginPage = homePage.clicarBotaoSingIn();
		//Preencher usu�rio e senha
		loginPage.preencherEmail("marcelo@teste.com");
		loginPage.preencherSenha("marcelo");
		//Clicar no bot�o sing in para logar
		loginPage.clicarBotaoSingIn();
		//Validar se est� logado
		//assertThat(homePage.estaLogado("Marcelo Bittencourt"), is(true));
		homePage.carregarPaginaInicial();
	}

	ProdutoPage produtoPage;
	String nomeProduto_HP, precoProduto_HP, nomeProduto_PP, precoProduto_PP;

	@Quando("seleciono um produto na posicao {int}")
	public void seleciono_um_produto_na_posicao(Integer int1) {
		int indice = 0;
		nomeProduto_HP = homePage.obterNomeProduto(indice);
		precoProduto_HP = homePage.obterPrecoProduto(indice);
		//System.out.println(nomeProduto_HP);
		//System.out.println(precoProduto_HP);
		produtoPage = homePage.clicarProduto(indice);
		nomeProduto_PP = produtoPage.obterNomeProduto();
		precoProduto_PP = produtoPage.obterPrecoProduto();
	}

	@Quando("nome do produto na tela inicial e na tela do produto eh {string}")
	public void nome_do_produto_na_tela_inicial_e_na_tela_do_produto_eh(String nomeProduto) {
		//assertThat(nomeProduto_HP.toUpperCase(), is(nomeProduto.toUpperCase()));
		//assertThat(nomeProduto_PP.toUpperCase(), is(nomeProduto.toUpperCase()));
	}

	@Quando("preco do produto na tela inicial e na tela do produto eh {string}")
	public void preco_do_produto_na_tela_inicial_e_na_tela_do_produto_eh(String precoProduto) {
		//assertThat(precoProduto_HP.toUpperCase(), is(precoProduto.toUpperCase()));
		//assertThat(precoProduto_PP.toUpperCase(), is(precoProduto.toUpperCase()));
	}

	ModalProductPage modalProductPage;
	@Quando("adiciono o produto ao carrinho com tamanho {string} cor {string} e quantidade {int}")
	public void adiciono_o_produto_ao_carrinho_com_tamanho_cor_e_quantidade(String tamanhoProduto, String corProduto, Integer qtd) {
		List<String> listaOpcoes = produtoPage.ObterOpcoesSelecionadas();
		
		produtoPage.selecionarOpcaoDropdown(tamanhoProduto);
		listaOpcoes = produtoPage.ObterOpcoesSelecionadas();
	
		if(!corProduto.equals("N/A"))
		produtoPage.selecionarCorPreta();
	
		produtoPage.alterarQtd(qtd);
	
		modalProductPage = produtoPage.clicarBotaoAdd();
		
		//assertTrue(modalProductPage.obterConfirmacao().endsWith("Product successfully added to your shopping cart"));
	}

	@Então("produto aparece na confirmacao com nome {string} tamanho {string} cor {string} e quantidade {int}")
	public void produto_aparece_na_confirmacao_com_nome_tamanho_cor_e_quantidade(String nomeProduto, String tamanhoProduto, String corProduto, Integer qtd) {
		//assertThat(modalProductPage.obterDescricaoProduto().toUpperCase(), is(nomeProduto_PP.toUpperCase()));
		Double precoProdutoDoubleEncontrado = Double.parseDouble(modalProductPage.obterDescricaoPreco().replace("$", ""));
		Double precoProdutoDoubleEsperado = Double.parseDouble(precoProduto_PP.replace("$", ""));
		//assertThat(modalProductPage.obterTamanhoProduto(), is(tamanhoProduto));
		//if(!corProduto.equals("N/A"){
		//assertThat(modalProductPage.obterCorProduto(), is(corProduto));
		//}
		//assertThat(modalProductPage.obterQtdProduto(), is(Integer.toString(qtdProduto)));
		String subTotalString = modalProductPage.obterSubTotal();
		subTotalString = subTotalString.replace("$", "");
		Double subTotalEncontrado = Double.parseDouble(subTotalString);
		Double subTotalCalculadoEsperado = qtd * precoProdutoDoubleEncontrado;
		//assertThat(subTotalEncotrado, is (subTotalCalculadoEsperado));
	}
	
	@After(order=1)
	public void capturarTela(Scenario scenario) {
		var camera = (TakesScreenshot) driver;
		File capturaDeTela = camera.getScreenshotAs(OutputType.FILE);
		String scenarioID = scenario.getId().substring(scenario.getId().lastIndexOf(".feature:")+ 9 );
		try {
			Files.move(capturaDeTela, new File("resources/screenshots/" + scenario.getName() + "_" + scenarioID + "_" + scenario.getStatus() + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@After(order=0)
	public static void finalizar() {
		driver.quit();
	}


	
}
