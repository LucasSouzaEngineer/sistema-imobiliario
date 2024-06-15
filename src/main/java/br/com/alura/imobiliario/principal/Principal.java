package br.com.alura.imobiliario.principal;

import br.com.alura.imobiliario.model.*;
import br.com.alura.imobiliario.repository.ImovelRepository;
import br.com.alura.imobiliario.repository.PessoaRepository;

import java.math.BigDecimal;
import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private PessoaRepository pessoaRepository;
    private ImovelRepository imovelRepository;

    public Principal(PessoaRepository pessoaRepository, ImovelRepository imovelRepository) {
        this.pessoaRepository = pessoaRepository;
        this.imovelRepository = imovelRepository;
    }

    public void exibirMenu(){
        var opcao = -1;
        var menu = """
                *** ESCOLHA UMA DAS OPÇÕES ABAIXO ***
                
                1 - Cadastrar Pessoa
                2 - Cadastrar Imóvel
                3 - Consultar Pessoas
                4 - Consultar Imóvel
                5 - Calcular Imposto
                6 - Relatório Imoveis
                7 - Relatório Pessoas
                8 - Relatório Imposto por Ano
                
                0 - Sair
                """;

        while(opcao != 0){
            System.out.println(menu);
            opcao = teclado.nextInt();
            teclado.nextLine();

            switch (opcao){
                case 1:
                    cadastrarPessoa();
                    break;
                case 2:
                    cadastrarImovel();
                    break;
                case 3:
                    consultarPessoas();
                    break;
                case 4:
                    consultarImovel();
                    break;
                case 5:
                    calcularImposto();
                    break;
                case 6:
                    emitirRelatorioImoveis();
                    break;
                case 7:
                    emitirRelatorioPessoas();
                    break;
                case 8:
                    emitirRelatorioImpostoAno();
                case 0:
                    System.out.println("Saindo do menu...");
                    break;
                default:
                    System.out.println("Opcão inválida");
            }
        }
    }

    private void cadastrarPessoa() {
        System.out.println("Digite o nome: ");
        var nome = teclado.nextLine();
        System.out.println("Digite o cpf: ");
        var cpf = teclado.nextLine();
        System.out.println("Digite o telefone de contato: ");
        var telefone = teclado.nextLine();
        System.out.println("Digite o e-mail de contato: ");
        var email = teclado.nextLine();
        System.out.println("Cadastrar endereço de correpondência: ");
        var enderecoCorrespondencia = cadastrarEndereco();

        pessoaRepository.save(new Pessoa(cpf, nome, enderecoCorrespondencia, telefone, email));
    }

    private void cadastrarImovel() {
        System.out.println("Selecionar proprietário: ");
        Pessoa proprietario = consultarPessoas();

        System.out.println("Digite a inscrição do imóvel: ");
        var inscricao = teclado.nextInt();
        teclado.nextLine();

        System.out.println("Endereço do imóvel");
        Endereco enderecoLocal = cadastrarEndereco();

        System.out.println("Endereço de correspondencia");
        Endereco enderecoEntrega = cadastrarEndereco();

        Terreno terreno = cadastrarTerreno();

        List<Edificio> edificios = new ArrayList<>();

        while (true){
            System.out.println("Digite 1 para cadastrar edificio e 0 encerrar o cadastro de edificio");
            var opcao = teclado.nextInt();
            if(opcao == 0){
                break;
            }
            teclado.nextLine();
            edificios.add(cadastrarEdificio());
        }

        Imovel imovel = new Imovel(inscricao, enderecoLocal, enderecoEntrega, terreno);
        imovel.setProprietario(proprietario);
        imovel.setEdificios(edificios);
        imovelRepository.save(imovel);
    }

    private Pessoa consultarPessoas() {
        Pessoa pessoa;
        System.out.println("Digite o CPF da Pessoa: ");
        var cpf = teclado.nextLine();
        Optional<Pessoa> pessoaOptional = pessoaRepository.consultarPessoaPorCpf(cpf);
        if(pessoaOptional.isPresent()){
            pessoa = pessoaOptional.get();
        }else {
            throw new RuntimeException("Pessoa não cadastrada!!");
        }
        System.out.println(pessoa);
        return pessoa;
    }

    private Imovel consultarImovel() {
        System.out.println("Digite a inscrição para consulta:");
        var inscricao = teclado.nextInt();
        teclado.nextLine();
        Optional<Imovel> imovelOptional = imovelRepository.consultarImovelPorInscricao(inscricao);
        Imovel imovel;
        if(imovelOptional.isPresent()){
           imovel = imovelOptional.get();
        }else {
            throw new RuntimeException("Pessoa não cadastrada!!");
        }

        System.out.println(imovel);
        return imovel;
    }

    private void calcularImposto() {
        Imovel imovel = consultarImovel();
        imovel.calcularImposto();
        imovelRepository.save(imovel);
    }

    private void emitirRelatorioImoveis() {
        List<Imovel> imoveis = imovelRepository.findAll();
        imoveis.stream()
                .sorted(Comparator.comparing(Imovel::getInscricao))
                .forEach(System.out::println);
    }

    private Endereco cadastrarEndereco(){
        System.out.println("Digite o logradouro:");
        var logradouro = teclado.nextLine();
        System.out.println("Digite o numero:");
        var numero = teclado.nextLine();
        return new Endereco(logradouro, numero);
    }

    private Terreno cadastrarTerreno(){
        System.out.println("Digite a área de terreno do imóvel: ");
        var areaTerreno = teclado.nextFloat();
        BigDecimal valorMetroTerreno = BigDecimal.ZERO;
        if(areaTerreno > 0.0){
            System.out.println("Digite o valor de M² do terreno: ");
            valorMetroTerreno = teclado.nextBigDecimal();
        }
        return new Terreno(areaTerreno, valorMetroTerreno);
    }

    private Edificio cadastrarEdificio(){
        System.out.println("Digite a área da edificação: ");
        var areaEdificacao = teclado.nextFloat();
        System.out.println("Digite o valor de M² da edificação: ");
        var valorMetroEdificacao = teclado.nextBigDecimal();

        return new Edificio(areaEdificacao, valorMetroEdificacao);
    }

    private void emitirRelatorioPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        pessoas.stream()
                .sorted(Comparator.comparing(Pessoa::getNome))
                .forEach(System.out::println);
    }
    private void emitirRelatorioImpostoAno() {
        System.out.println("Digite o ano em que deseja consultar os impostos: ");
        var ano = teclado.nextInt();
        teclado.nextLine();
        List<Imposto> impostos = imovelRepository.consultarImpostoPorAno(ano);
        impostos.forEach(System.out::println);
    }
}
