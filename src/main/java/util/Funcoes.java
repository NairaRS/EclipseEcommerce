package util;

public class Funcoes {
	public static Double removeCifraoDevolveDouble(String texto) {
		texto = texto.replace("$", "");
		return Double.parseDouble(texto);
	}
	
	public static int removeTextoDevolveInt(String texto) {
		texto = texto.replace(" items", "");
		return Integer.parseInt(texto);
	}
	
	public static String removeTexto(String texto, String textoRemovido) {
		texto = texto.replace(textoRemovido, "");
		return texto;
	}
}
