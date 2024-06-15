package br.com.alura.imobiliario.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Edificio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float area;
    private BigDecimal valorMetro;

    public Edificio() {
    }

    public Edificio(float area, BigDecimal valorMetro) {
        this.area = area;
        this.valorMetro = valorMetro;
    }

    public BigDecimal getValorVenal(){
        return this.valorMetro.multiply(BigDecimal.valueOf(this.area));
    }

    @Override
    public String toString() {
        return area + "M²";
    }
}
