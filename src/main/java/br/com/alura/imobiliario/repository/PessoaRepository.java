package br.com.alura.imobiliario.repository;

import br.com.alura.imobiliario.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    @Query("SELECT p FROM Pessoa p WHERE p.cpf = :cpf")
    Optional<Pessoa> consultarPessoaPorCpf(String cpf);

}
