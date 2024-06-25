package br.com.junior.TabelaFipe.service;

import java.util.List;

// Interface para conversão de dados de JSON para objetos Java
public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);

    <T> List<T> recebeLista(String json, Class<T> classe);
}
