package br.com.alura.alurator.reflexao;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Stream;

public class ManipuladorObjeto {

	private Object instancia;

	public ManipuladorObjeto(Object instancia) {
		this.instancia = instancia;
	}

	public ManipuladorMetodo getMetodo(String nomeMetodo, Map<String, Object> params) {
		// 1) Pegar todos os métodos da classe.
	    // 2) Filtrar todos os métodos de modo que:
	    //  2.1) Tenham o mesmo nome informado pelo usuário;
	    //  2.2) Tenham a mesma quantidade de parâmetros passados na URL;
	    //  2.3) E que cada um dos parâmetros tenham os mesmos nomes e tipos iguais 
	    //  aos passados na URL.
	    // 3) Lançar uma RuntimeException caso nenhum método seja encontrado.
		
		// /filtra?nome=produto => filtra(String nome)
	    // /produto/filtra?nome=produto&marca=marca 1 => filtra(String nome, String marca)
	    // /filtra?marca=marca 1&nome=produto => filtra(String nome, String marca)
		// /filtra?batman=marca 1&nome=produto => filtra(String nome, String batman)
		
		Stream<Method> metodos = Stream.of(instancia.getClass().getDeclaredMethods());
		Method metodoSelecionado = metodos.filter(metodo -> 
												metodo.getName().equals(nomeMetodo)
												&& metodo.getParameterCount() == params.values().size()
												&& Stream.of(metodo.getParameters())
													.allMatch(arg -> 
														params.keySet().contains(arg.getName())
														&& params.get(arg.getName()).getClass().equals(arg.getType())
													)
											)
											.findFirst()
											.orElseThrow(() -> new RuntimeException("Método não encontrado!"));
		
		return new ManipuladorMetodo(instancia, metodoSelecionado, params);
	}

}
