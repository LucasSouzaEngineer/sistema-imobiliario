package br.com.alura.imobiliario;

import br.com.alura.imobiliario.principal.Principal;
import br.com.alura.imobiliario.repository.ImovelRepository;
import br.com.alura.imobiliario.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImobiliarioApplication implements CommandLineRunner {
	@Autowired
	PessoaRepository pessoaRepository;
	@Autowired
	ImovelRepository imovelRepository;

	public static void main(String[] args) {
		SpringApplication.run(ImobiliarioApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(pessoaRepository, imovelRepository);
		principal.exibirMenu();
		System.out.println("teste");
	}
}
