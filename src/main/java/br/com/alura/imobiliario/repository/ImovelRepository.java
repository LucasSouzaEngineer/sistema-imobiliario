package br.com.alura.imobiliario.repository;

import br.com.alura.imobiliario.model.Imovel;
import br.com.alura.imobiliario.model.Imposto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ImovelRepository extends JpaRepository<Imovel, Long> {
    @Query("SELECT i FROM Imovel i WHERE i.inscricao = :inscricao")
    Optional<Imovel> consultarImovelPorInscricao(Integer inscricao);
    @Query("SELECT imposto FROM Imovel imovel JOIN imovel.impostos imposto WHERE imposto.ano = :ano ")
    List<Imposto> consultarImpostoPorAno(int ano);
}
