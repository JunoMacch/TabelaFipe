package br.com.junior.TabelaFipe;

import br.com.junior.TabelaFipe.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TabelaFipeApplication implements CommandLineRunner {

	// Método principal para iniciar a aplicação Spring Boot
	public static void main(String[] args) {
		SpringApplication.run(TabelaFipeApplication.class, args);
	}

	// Método run é executado após a inicialização da aplicação
	@Override
	public void run(String... args) throws Exception {
		// Criação de uma instância da classe Principal e chama o método que exibe o menu e gerencia a lógica do programa
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
