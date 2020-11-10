package br.com.alura.alurator;

import java.util.Map;

import br.com.alura.alurator.conversor.ConversorXML;
import br.com.alura.alurator.ioc.ContainerIoC;
import br.com.alura.alurator.protocolo.Request;
import br.com.alura.alurator.reflexao.ManipuladorObjeto;
import br.com.alura.alurator.reflexao.Reflexao;

public class Alurator {
	
	private String pacoteBase;
	private ContainerIoC container;
	
	public Alurator(String pacoteBase) {
		this.pacoteBase = pacoteBase;
		this.container = new ContainerIoC();
	}
	
	public Object executa(String url) {
		
		// TODO - processa a requisicao executando o metodo
		// da classe em questao
		
		// Produto lista
		
		// produto -> roduto
		
		Request request = new Request(url);
		
		String nomeControle = request.getNomeControle();
		String nomeMetodo = request.getNomeMetodo();
		Map<String, Object> params = request.getQueryParams();
		
	    Class<?> classeControle = new Reflexao().getClasse( pacoteBase + nomeControle );
	    
	    Object instanciaController = this.container.getInstancia(classeControle);
		
		Object retorno = new ManipuladorObjeto(instanciaController)
			                .getMetodo(nomeMetodo, params)
			                .comTratamentoDeExcecao((metodo, ex) -> {
				                	System.out.println("Erro no método " + metodo.getName() + " da classe " 
				                            + metodo.getDeclaringClass().getName() + ".\n\n");
				                	throw new RuntimeException("Erro no método!");
			                })
			                .invoca();
			
		return ConversorXML.converter(retorno);
	}

	public <T, K extends T> void registra(Class<T> tipoFonte, Class<K> tipoDestino) {
		container.registra(tipoFonte, tipoDestino);
	}
}
