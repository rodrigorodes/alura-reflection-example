package br.com.alura.alurator.conversor;

import java.lang.reflect.Field;
import java.util.Collection;

import br.com.alura.alurator.anotacao.TagXml;

public class ConversorXML {

	public static String converter(Object objeto) {
		
		StringBuilder xmlBuilder = new StringBuilder();
		
		try {

			Class<?> objetoManipulado = objeto.getClass();

			if (objeto instanceof Collection) {
				
				xmlBuilder.append("\n");
				xmlBuilder.append("<lista>");
				
				Collection<?> colecao = (Collection<?>) objeto;

				for (Object object : colecao) {
					String xml = converter(object);
					xmlBuilder.append(xml);
				}
				
				xmlBuilder.append("</lista>");

			} else {
				
				TagXml annotationClazz = objetoManipulado.getDeclaredAnnotation(TagXml.class);
				
				String value = annotationClazz != null ? annotationClazz.value() : objetoManipulado.getSimpleName() ;
				
				xmlBuilder.append("\n");
				xmlBuilder.append("<" + value + ">");
				xmlBuilder.append("\n");

				for (Field atributos : objetoManipulado.getDeclaredFields()) {
					
					atributos.setAccessible(true);
					
					TagXml annotationField = atributos.getDeclaredAnnotation(TagXml.class);
					
					String valueField = annotationField != null ? annotationField.value() : atributos.getName();

					xmlBuilder.append("<" + valueField + ">");

					xmlBuilder.append(atributos.get(objeto));

					xmlBuilder.append("</" + valueField + ">");
					xmlBuilder.append("\n");


				}
				xmlBuilder.append("</" + value + ">");

			}

		} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
			throw new RuntimeException(" Erro ao convrerter para XML !");
		} 

		return xmlBuilder.toString();

	}

}
