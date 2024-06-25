package br.com.junior.TabelaFipe.principal;

import br.com.junior.TabelaFipe.model.DadosVeiculo;
import br.com.junior.TabelaFipe.model.Modelos;
import br.com.junior.TabelaFipe.model.Veiculo;
import br.com.junior.TabelaFipe.service.ConsumoApi;
import br.com.junior.TabelaFipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    // Scanner para leitura de entradas do usuário
    private Scanner leitura = new Scanner(System.in);

    // Instâncias das classes de serviço para consumo da API e conversão de dados
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    // URL base da API Tabela Fipe
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    // Método que exibe o menu e gerencia as interações com o usuário
    public void exibeMenu() {
        String resposta;
        do {
            var menu = """
                    *** OPÇÕES ***
                    Carros
                    Motos
                    Caminhões
                                        
                    Digite uma das opções para consultar: """;

            System.out.println(menu);
            var opcao = leitura.nextLine();
            String endereco;

            // Determina o endpoint da API com base na escolha do usuário
            if (opcao.toLowerCase().contains("carr")) {
                endereco = URL_BASE + "carros/marcas";
            } else if ((opcao.toLowerCase().contains("mot"))) {
                endereco = URL_BASE + "motos/marcas";
            } else {
                endereco = URL_BASE + "caminhoes/marcas";
            }

            // Consome a API para obter as marcas e exibe as marcas ordenadas
            var json = consumo.obterDados(endereco);
            System.out.println(json);

            var marcas = conversor.recebeLista(json, DadosVeiculo.class);
            marcas.stream()
                    .sorted(Comparator.comparing(DadosVeiculo::codigo))
                    .forEach(System.out::println);

            System.out.println("Informe o código da marca para consultar modelos e anos");
            var codigoMarca = leitura.nextLine();

            // Obtém os modelos de veículos da marca escolhida
            endereco = endereco + "/" + codigoMarca + "/modelos";
            json = consumo.obterDados(endereco);
            var listModelo = conversor.obterDados(json, Modelos.class);

            System.out.println("\nModelos dessa marca: ");
            listModelo.modelos().stream()
                    .sorted(Comparator.comparing(DadosVeiculo::codigo))
                    .forEach(System.out::println);

            System.out.println("\nDigite um trecho do nome do carro a ser buscado");
            var modeloVeiculo = leitura.nextLine();

            // Filtra modelos de veículos pelo nome fornecido
            List<DadosVeiculo> filtroModelos = listModelo.modelos().stream()
                    .filter(m -> m.nome().toLowerCase().contains(modeloVeiculo.toLowerCase()))
                    .collect(Collectors.toList());

            System.out.println("\nModelos Filtrados");
            filtroModelos.forEach(System.out::println);

            System.out.println("Digite o código do modelo para consultar informações");
            var codigoModelo = leitura.nextLine();

            // Obtém os anos de fabricação do modelo escolhido
            endereco = endereco + "/" + codigoModelo + "/anos";
            json = consumo.obterDados(endereco);
            List<DadosVeiculo> anos = conversor.recebeLista(json, DadosVeiculo.class);
            List<Veiculo> veiculos = new ArrayList<>();

            // Para cada ano, obtém as informações detalhadas do veículo
            for (int i = 0; i < anos.size(); i++) {
                var enderecoAnos = endereco + "/" + anos.get(i).codigo();
                json = consumo.obterDados(enderecoAnos);
                Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
                veiculos.add(veiculo);
            }

            System.out.println("Todos os veículos encontrados: ");
            veiculos.forEach(System.out::println);

            System.out.println("Gostaria de verificar outro veículo? (S/N)");
            resposta = leitura.nextLine();

        } while (!resposta.equals("N".toLowerCase()));
    }

}
