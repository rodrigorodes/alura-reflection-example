package br.com.alura.alurator.playground.reflexao;

import java.lang.reflect.Field;

import br.com.alura.alurator.playground.model.Produto;

public class TesteManipulandoAtributos {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {

		Object produto = new Produto("Produto 1", 10, "Marca");
		
		Class<? extends Object> clazzProduto = produto.getClass();
		
		System.out.println(clazzProduto.getField("id"));
		System.out.println(clazzProduto.getField("id").get(produto));
		
		for (Field field : clazzProduto.getDeclaredFields()) {
			field.setAccessible(true);
			System.out.println(field.getName() + " : " + field.get(produto));
		}

	}

}
