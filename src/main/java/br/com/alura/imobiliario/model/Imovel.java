package br.com.alura.imobiliario.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Imovel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer inscricao;
    @Embedded
    private Endereco enderecoLocal;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "logradouro", column = @Column(name = "logradouro_entrega")),
            @AttributeOverride(name = "numero", column = @Column(name = "numero_entrega")),
            @AttributeOverride(name = "bairro", column = @Column(name = "bairro_entrega")),
            @AttributeOverride(name = "complemento", column = @Column(name = "complemento_entrega")),
            @AttributeOverride(name = "cep", column = @Column(name = "cep_entrega"))
    })
    private Endereco enderecoEntrega;
    @Embedded
    private Terreno terreno;
    @OneToMany(mappedBy = "imovel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Imposto> impostos = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "imovel_id")
    private List<Edificio> edificios;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa proprietario;
    @Transient
    private double aliquota = 0.02;

    public Imovel() {
    }

    public Imovel(Integer inscricao, Endereco enderecoLocal, Endereco enderecoEntrega, Terreno terreno) {
        this.inscricao = inscricao;
        this.enderecoLocal = enderecoLocal;
        this.enderecoEntrega = enderecoEntrega;
        this.terreno = terreno;
    }

    public BigDecimal getValorVenal(){
        BigDecimal valorVenalImovel = getValorVenalEdificio().add(getValorVenalTerreno());
        return valorVenalImovel;
    }

    public BigDecimal getValorVenalEdificio() {
        BigDecimal valorVenalEdificio = this.edificios.stream()
                .map(Edificio::getValorVenal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return valorVenalEdificio;
    }

    public BigDecimal getValorVenalTerreno(){
        BigDecimal valorVenalTerreno = this.terreno.getValorVenal();
        return valorVenalTerreno;
    }

    public void setProprietario(Pessoa proprietario) {
        this.proprietario = proprietario;
        proprietario.setImovel(this);
    }

    public void setEdificios(List<Edificio> edificios) {
        this.edificios = edificios;
    }


    public void calcularImposto(){
        BigDecimal valorImposto
                , valorVenalTerreno
                , valorVenalEdificio;
        valorVenalEdificio = this.getValorVenalEdificio();
        valorVenalTerreno = this.getValorVenalTerreno();
        valorImposto = this.getValorVenal().multiply(BigDecimal.valueOf(this.aliquota));
        int ano = LocalDate.now().getYear();

        this.impostos.add(new Imposto(ano, valorVenalTerreno, valorVenalEdificio, valorImposto, this));
    }

    @Override
    public String toString() {
        return "Inscricao = " + inscricao +
                ", Local = " + enderecoLocal +
                ", Entrega = " + enderecoEntrega +
                ", Terreno = " + terreno +
                ", Edificio = " + edificios +
                ", Proprietário = " + proprietario;
    }

    public Integer getInscricao() {
        return this.inscricao;
    }

    public Double getAliquota() {
        return this.aliquota;
    }
}
