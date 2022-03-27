# language: pt
Funcionalidade: Comprar produtos
  Como um usuario nao logado
  Eu quero visualizar produtos disponiveis
  Para poder escolher qual eu vou comprar

  @validacaoinicial
  Cenario: Deve mostrar uma lista de oito produtos na pagina incial
    Dado que estou na pagina inicial
    Quando nao estou logado
    Então visualizo 8 produtos disponiveis
    E carrinho esta zerado

  @fluxopadrao
  Esquema do Cenario: Deve mostrar produto escolhido confirmado
    Dado que estou na pagina inicial
    Quando estou logado
    E seleciono um produto na posicao <posicao>
    E nome do produto na tela inicial e na tela do produto eh <nomeProduto>
    E preco do produto na tela inicial e na tela do produto eh <precoProduto>
    E adiciono o produto ao carrinho com tamanho <tamanhoProduto> cor <corProduto> e quantidade <qtd>
    Então produto aparece na confirmacao com nome <nomeProduto> tamanho <tamanhoProduto> cor <corProduto> e quantidade <qtd>

    Exemplos: 
      | posicao | nomeProduto                   | precoProduto | tamanhoProduto | corProduto | qtd |
      |       0 | "Hummingbird printed t-shirt" | "$19.20"     | "M"            | "Black"    |   2 |
      |       1 | "Hummingbird printed Sweater" | "28.72"      | "XL"           | "N/A"      |   3 |
