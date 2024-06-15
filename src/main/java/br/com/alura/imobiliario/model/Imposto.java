package br.com.alura.imobiliario.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
@Entity
public class Imposto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int ano;
    private BigDecimal valorVenalTerreno;
    private BigDecimal valorVenalEdificio;
    private BigDecimal valorImposto;
    @ManyToOne
    @JoinColumn(name = "imovel_id")
    private Imovel imovel;

    public Imposto() {
    }

    public Imposto(int ano, BigDecimal valorVenalTerreno, BigDecimal valorVenalEdificio, BigDecimal valorImposto, Imovel imovel) {
        this.ano = ano;
        this.valorVenalTerreno = valorVenalTerreno;
        this.valorVenalEdificio = valorVenalEdificio;
        this.valorImposto = valorImposto;
        this.imovel = imovel;
    }

    @Override
    public String toString() {
        return "Imovel = " + imovel.getInscricao() +
                ", Ano = " + ano +
                ", Valor Venal Terreno = R$ " + valorVenalTerreno +
                ", Valor Venal Edificio = R$ " + valorVenalEdificio +
                ", Valor Imposto = R$ " + valorImposto +
                ", Aliquota = " + imovel.getAliquota();
    }
}
