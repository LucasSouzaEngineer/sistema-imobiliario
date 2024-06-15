package br.com.alura.imobiliario.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    private String nome;
    @Embedded()
    private Endereco endereco;
    private String telefone;
    private String email;
    @OneToMany(mappedBy = "proprietario", fetch = FetchType.EAGER)
    private List<Imovel> imoveis = new ArrayList<>();

    public Pessoa() {
    }

    public Pessoa(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public Pessoa(String cpf, String nome, Endereco enderecoCorrespondencia, String telefone, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = enderecoCorrespondencia;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public List<Integer> listarImoveis(){
        return imoveis.stream()
                .mapToInt(Imovel::getInscricao)
                .boxed()
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return nome + ", CPF = " + cpf + ", Imoveis = " + listarImoveis();
    }


//    @Override
//    public String toString() {
//        return "Pessoa{" +
//                "cpf='" + cpf + '\'' +
//                ", nome='" + nome + '\'' +
//                ", endereco=" + endereco +
//                ", telefone='" + telefone + '\'' +
//                ", email='" + email + '\'' +
//                '}';
//    }

    public void setImovel(Imovel imovel) {
        this.imoveis.add(imovel);
    }

    public List<Imovel> getImoveis() {
        return this.imoveis;
    }
}
