package teste;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("João", LocalDate.parse("01/01/1990", dateFormatter), new BigDecimal("3500.00"), "Analista"));
        funcionarios.add(new Funcionario("Maria", LocalDate.parse("15/05/1985", dateFormatter), new BigDecimal("2800.00"), "Técnico"));
        funcionarios.add(new Funcionario("Pedro", LocalDate.parse("10/12/1992", dateFormatter), new BigDecimal("4500.00"), "Gerente"));
        funcionarios.add(new Funcionario("Lucia", LocalDate.parse("20/03/1988", dateFormatter), new BigDecimal("3200.00"), "Analista"));

        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        System.out.println("Funcionários:");
        funcionarios.forEach(System.out::println);

        funcionarios.forEach(funcionario -> funcionario.aumentarSalario(10));

        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\nFuncionários agrupados por função:");
        funcionariosPorFuncao.forEach((funcao, listaFuncionarios) -> {
            System.out.println("Função: " + funcao);
            listaFuncionarios.forEach(System.out::println);
        });

        System.out.println("\nFuncionários que fazem aniversário em outubro e dezembro:");
        funcionarios.stream()
                .filter(funcionario -> {
                    int month = funcionario.getDataNascimento().getMonthValue();
                    return month == 10 || month == 12;
                })
                .forEach(System.out::println);

        System.out.println("\nFuncionário com a maior idade:");
        Funcionario funcionarioMaisVelho = Collections.max(funcionarios, Comparator.comparing(Funcionario::getDataNascimento));
        int idade = LocalDate.now().getYear() - funcionarioMaisVelho.getDataNascimento().getYear();
        System.out.println("Nome: " + funcionarioMaisVelho.getNome() + ", Idade: " + idade);

        System.out.println("\nFuncionários em ordem alfabética:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(System.out::println);

        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos salários dos funcionários: " + totalSalarios);

        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nSalários em salários mínimos:");
        funcionarios.forEach(funcionario -> {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_DOWN);
            System.out.println(funcionario.getNome() + ": " + salariosMinimos + " salários mínimos");
        });
    }
}

