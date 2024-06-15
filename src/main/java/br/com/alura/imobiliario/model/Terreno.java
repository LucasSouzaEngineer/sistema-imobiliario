package br.com.alura.imobiliario.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Embeddable
public class Terreno {
    private float area;
    private BigDecimal valorMetro;

    public Terreno() {}

    public Terreno(float area, BigDecimal valorMetro) {
        this.area = area;
        this.valorMetro = valorMetro;
    }

    public BigDecimal getValorVenal(){
        return this.valorMetro.multiply(BigDecimal.valueOf(this.area));
    }

    @Override
    public String toString() {
        return  area + "M²";
    }
}
