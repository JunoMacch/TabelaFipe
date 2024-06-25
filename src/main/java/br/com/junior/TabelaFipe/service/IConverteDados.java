package br.com.junior.TabelaFipe.service;

import java.util.List;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);

    <T> List<T> recebeLista(String json, Class<T> classe);
}
