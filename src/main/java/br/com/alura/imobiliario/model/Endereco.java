package br.com.alura.imobiliario.model;

import jakarta.persistence.*;

@Embeddable
public class Endereco {
    private String logradouro;
    private String numero;
    private String bairro;
    private String complemento;
    private String cep;

    public Endereco() {
    }

    public Endereco(String logradouro, String numero) {
        this.logradouro = logradouro;
        this.numero = numero;
    }

    @Override
    public String toString() {
        return  logradouro +
                " - n" + numero;
    }
}
