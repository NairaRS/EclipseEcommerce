package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BaseTests;
import pages.CarrinhoPage;
import pages.CheckOutPage;
import pages.LoginPage;
import pages.ModalProductPage;
import pages.PedidoPage;
import pages.ProdutoPage;
import util.Funcoes;

public class HomePageTests extends BaseTests{
	@Test
	public void testcontarProdutos_oitoProdutosDiferentes() {
		carregarPaginaInicial();
		assertThat(homePage.contarProdutos(), is(3));
	}
	
	@Test
	public void testeValidarCarrinhoZerado_ZeroItensNoCarrinho() {
		int produtosNoCarrinho = homePage.obterQuantidadeProdutosCarrinho();
		assertThat(homePage.obterQuantidadeProdutosCarrinho(), is(0));
	}
	
	ProdutoPage produtoPage;
	String nomeProduto_PP, precoProduto_PP;
	@Test
	public void testValidarDetalhesProduto_DescricaoValorIguais() {
		int indice = 0;
		String nomeProduto_HP = homePage.obterNomeProduto(indice);
		String precoProduto_HP = homePage.obterPrecoProduto(indice);
		//System.out.println(nomeProduto_HP);
		//System.out.println(precoProduto_HP);
		produtoPage = homePage.clicarProduto(indice);
		nomeProduto_PP = produtoPage.obterNomeProduto();
		precoProduto_PP = produtoPage.obterPrecoProduto();
		//System.out.println(nomeProduto_PP);
		//System.out.println(precoProduto_PP);
		assertThat(nomeProduto_HP.toUpperCase(), is(nomeProduto_PP.toUpperCase()));
		assertThat(precoProduto_HP, is(precoProduto_PP));
	}
	
	LoginPage loginPage;
	@Test
	public void testeLoginComSucesso_UsuarioLogado() {
		//Clicar bot�o sing in na homepage
		loginPage = homePage.clicarBotaoSingIn();
		//Preencher usu�rio e senha
		loginPage.preencherEmail("marcelo@teste.com");
		loginPage.preencherSenha("marcelo");
		//Clicar no bot�o sing in para logar
		loginPage.clicarBotaoSingIn();
		//Validar se est� logado
		assertThat(homePage.estaLogado("Marcelo Bittencourt"), is(true));
		carregarPaginaInicial();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/massaTeste_Login.csv", numLinesToSkip = 1, delimiter = ';')
	public void testeLogin_LoginValido(String nomeTeste, String email, String password, String nomeUsuario,String resultado){
		//Clicar bot�o sing in na homepage
		loginPage = homePage.clicarBotaoSingIn();
		
		//Preencher usu�rio e senha
		loginPage.preencherEmail(email);
		loginPage.preencherSenha(password);
		
		//Clicar no bot�o sing in para logar
		loginPage.clicarBotaoSingIn();
		boolean esperado_LoginOk;
		if (resultado.equals("positivo")) {
			esperado_LoginOk = true;
			}
		else {
			esperado_LoginOk = false;
		}
		
		//Validar se est� logado
		assertThat(homePage.estaLogado(nomeUsuario), is(esperado_LoginOk));
		capturarTela(nomeTeste, resultado);
		if(esperado_LoginOk) {
			homePage.clicarBotaoSignOut();
		}
		carregarPaginaInicial();
	}
	
	ModalProductPage modalProductPage;
	@Test
	public void testeIncluirProdutoCarrinho_ProdutoIncluidoComSucesso() {
		String tamanhoProduto = "M";
		String corProduto = "Black";
		int qtdProduto = 2;
		
		//Pr�-condi��o:usu�rio logado
		if(!homePage.estaLogado("Marcelo Bittencourt")) {
			testeLoginComSucesso_UsuarioLogado();
		}
		
		//Selecionando produtos
		testValidarDetalhesProduto_DescricaoValorIguais();
		
		//Selecionar tamanho
		List<String> listaOpcoes = produtoPage.ObterOpcoesSelecionadas();
		//System.out.println(listaOpcoes.get(0));
		//System.out.println("Tamanho da lista: " + listaOpcoes.size());
		produtoPage.selecionarOpcaoDropdown(tamanhoProduto);
		listaOpcoes = produtoPage.ObterOpcoesSelecionadas();
		//System.out.println(listaOpcoes.get(0));
		//System.out.println("Tamanho da lista: " + listaOpcoes.size());
		
		//Selecionar cor
		produtoPage.selecionarCorPreta();
		
		//Selecionar quantidade
		produtoPage.alterarQtd(qtdProduto);
		
		//Add ao carrinho
		modalProductPage = produtoPage.clicarBotaoAdd();
		
		//assertThat(modalProductPage.obterConfirmacao(), is("Product successfully added to your shopping cart"));
		assertTrue(modalProductPage.obterConfirmacao().endsWith("Product successfully added to your shopping cart"));
		assertThat(modalProductPage.obterTamanhoProduto(), is(tamanhoProduto));
		assertThat(modalProductPage.obterCorProduto(), is(corProduto));
		assertThat(modalProductPage.obterQtdProduto(), is(Integer.toString(qtdProduto)));
		//System.out.println(modalProductPage.obterDescricaoProduto());
		//System.out.println(modalProductPage.obterDescricaoPreco());
		//System.out.println(modalProductPage.obterSubTotal());
		
		//Tirar o $ e validar o total
		String precoProdutoString = modalProductPage.obterDescricaoPreco();
		precoProdutoString = precoProdutoString.replace("$", "");
		Double precoProduto = Double.parseDouble(precoProdutoString);
		String subTotalString = modalProductPage.obterSubTotal();
		subTotalString = subTotalString.replace("$", "");
		Double subTotal = Double.parseDouble(subTotalString);
		Double subTotalCalculado = qtdProduto * precoProduto;
		assertThat(subTotal, is (subTotalCalculado));
		
		//Validar nome e pre�o do produto
		//System.out.println(modalProductPage.obterDescricaoProduto());
		assertThat(modalProductPage.obterDescricaoProduto().toUpperCase(), is(nomeProduto_PP.toUpperCase()));
		assertThat(modalProductPage.obterDescricaoPreco(), is(precoProduto_PP));
		//System.out.println(modalProductPage.obterDescricaoProduto());
		//System.out.println(modalProductPage.obterDescricaoPreco());
		//System.out.println(nomeProduto_PP);
		//System.out.println(precoProduto_PP);
	}
	
	//Valores esperados
	String esperado_nome = "Hummingbird printed t-shirt";
	Double esperado_preco = 19.12;
	String esperado_tamanho = "M";
	String esperado_cor = "Black";
	int esperado_qtd = 2;
	Double esperado_subtotal = esperado_preco * esperado_qtd;
	Double esperado_subtotalPainel = esperado_subtotal;
	Double esperado_shipping = 7.00;
	int esperado_itens = esperado_qtd;
	Double esperado_totalPainel = esperado_subtotal + esperado_shipping;
	Double esperado_taxaTotal = 0.00;
	Double esperado_totalTotal = esperado_totalPainel + esperado_taxaTotal;
	String esperado_nomeCliente = "Marcelo Bittencourt";
	String email = "marcelo@teste.com";
	String esperado_pagamento = "Payments by check";
		
	CarrinhoPage carrinhoPage;
	@Test
	public void testeIrParaCarrinho_InformacoesPersistidas() {
		//Pr�-condi��es: produto no modal
		testeIncluirProdutoCarrinho_ProdutoIncluidoComSucesso();
		
		//Clicar Checkout
		carrinhoPage = modalProductPage.clicarBotaoProceedToCheckout();
		
		//~~Teste~~
		//Validar todos os elementos
		//System.out.println("Tela do Carrinho");
		//System.out.println(carrinhoPage.obter_nomeProduto());
		//System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()));
		//System.out.println(carrinhoPage.obter_tamanhoProduto());
		//System.out.println(carrinhoPage.obter_corProduto());
		//System.out.println(carrinhoPage.obter_qtdProduto());
		//System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotal()));
		//System.out.println("Painel de Totais");
		//System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subTotalPainel()));
		//System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingPainel()));
		//System.out.println(Funcoes.removeTextoDevolveInt(carrinhoPage.obter_itensTotal()));
		//System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalPainel()));
		//System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTotal()));
		//System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxaTotal()));
		assertThat(carrinhoPage.obter_nomeProduto(), is(esperado_nome));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()), is(esperado_preco));
		assertThat(carrinhoPage.obter_tamanhoProduto(), is(esperado_tamanho));
		assertThat(carrinhoPage.obter_corProduto(), is(esperado_cor));
		assertThat(Integer.parseInt(carrinhoPage.obter_qtdProduto()), is(esperado_qtd));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotal()), is(esperado_subtotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subTotalPainel()), is(esperado_subtotalPainel));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingPainel()), is(esperado_shipping));
		assertThat(Funcoes.removeTextoDevolveInt(carrinhoPage.obter_itensTotal()), is(esperado_itens));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalPainel()), is(esperado_totalPainel));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTotal()), is(esperado_totalTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxaTotal()), is(esperado_taxaTotal));
		
		//assertEquals(carrinhoPage.obter_nomeProduto(), is(esperado_nome));
		//assertEquals(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()), is(esperado_preco));
		//assertEquals(carrinhoPage.obter_tamanhoProduto(), is(esperado_tamanho));
		//assertEquals(carrinhoPage.obter_corProduto(), is(esperado_cor));
		//assertEquals(carrinhoPage.obter_qtdProduto(), is(esperado_qtd));
		//assertEquals(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotal()), is(esperado_subtotal));
		//assertEquals(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subTotalPainel()), is(esperado_subtotalPainel));
		//assertEquals(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingPainel()), is(esperado_shipping));
		//assertEquals(Funcoes.removeTextoDevolveInt(carrinhoPage.obter_itensTotal()), is(esperado_itens));
		//assertEquals(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalPainel()), is(esperado_totalPainel));
		//assertEquals(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTotal()), is(esperado_totalTotal));
		//assertEquals(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxaTotal()), is(esperado_taxaTotal));
	}
	
	CheckOutPage checkoutPage;
	@Test
	public void testeIrParaCheckOut_FreteEnderecoPagamento() {
		//Pr�-teste: produto no carrinho
		testeIrParaCarrinho_InformacoesPersistidas();
		
		//~~Teste~~
		//Clicar botao
		checkoutPage = carrinhoPage.botaoCheckOut();
		
		//Validar
		assertThat(Funcoes.removeCifraoDevolveDouble(checkoutPage.obter_valorTotal()), is(esperado_totalTotal));
		assertTrue(checkoutPage.obter_nome().startsWith(esperado_nomeCliente));
		checkoutPage.botao_confirmar();
		String encontrou_shipping = checkoutPage.obter_shipping();
		encontrou_shipping = Funcoes.removeTexto(encontrou_shipping, " tax excl.");	
		Double encontrou_shipping_Double = Funcoes.removeCifraoDevolveDouble(encontrou_shipping);
		assertThat(encontrou_shipping_Double, is(esperado_shipping));
		checkoutPage.botao_confirmarDelivery();
		
		//Pagamento
		checkoutPage.selecionar_pagamento();
		String valorFinal = checkoutPage.obter_valorFinal();
		valorFinal = Funcoes.removeTexto(valorFinal, "(tax incl.)");	
		Double valorFinal_Double = Funcoes.removeCifraoDevolveDouble(valorFinal);
		assertThat(valorFinal_Double, is(esperado_totalTotal));
		checkoutPage.obter_concordar();
		assertTrue(checkoutPage.esta_Concordado());
	}
	
	PedidoPage pedidoPage;
	@Test
	public void testeFinalizarPedido_ConfirmarDetalhes() {
		//Pr� condi��es: checkout conclu�do
		testeIrParaCheckOut_FreteEnderecoPagamento();
		
		//Confirmar pedido
		pedidoPage = checkoutPage.botaoFinalizar();
		
		//Validar
		assertTrue(pedidoPage.obter_confirmacao().toUpperCase().endsWith("YOUR ORDER IS CONFIRMED"));
		assertThat(pedidoPage.validarEmail(), is(email));
		assertThat(pedidoPage.validarSoma(), is(esperado_subtotal));
		assertThat(pedidoPage.validarTotal(), is(esperado_totalTotal));
		assertThat(pedidoPage.validarFormaPagamento(), is(esperado_pagamento));
	}
}
