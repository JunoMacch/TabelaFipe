package br.com.junior.TabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// Define um modelo que contém uma lista de dados de veículos
@JsonIgnoreProperties(ignoreUnknown = true)
public record Modelos(List<DadosVeiculo> modelos) {
}
