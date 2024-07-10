package org.example;

import org.example.model.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        ArrayList<Funcionario> funcionarios = new ArrayList<>(List.
                of(
                    buildFuncionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
                    buildFuncionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
                    buildFuncionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
                    buildFuncionario("Miguel", LocalDate.of(1988, 10,14), new BigDecimal("19119.88"), "Diretor"),
                    buildFuncionario("Alice", LocalDate.of(1995, 1,5), new BigDecimal("2234.68"), "Recepcionista"),
                    buildFuncionario("Heitor", LocalDate.of(1999, 11,19), new BigDecimal("1582.72"), "Operador"),
                    buildFuncionario("Arthur", LocalDate.of(1993, 3,31), new BigDecimal("4071.84"), "Contador"),
                    buildFuncionario("Laura", LocalDate.of(1994, 7,8), new BigDecimal("3017.45"), "Gerente"),
                    buildFuncionario("Heloisa", LocalDate.of(2003, 5,24), new BigDecimal("1606.85"), "Eletricista"),
                    buildFuncionario("Helena", LocalDate.of(1996, 9,2), new BigDecimal("2799.93"), "Gerente")
                )
        );


        removeFuncionarioPeloName("João", funcionarios);

        imprimirFuncionarios(funcionarios);

        aumentarSalario(funcionarios, new BigDecimal("1.10"));

        Map<String, List<Funcionario>> funcionariosMap = agruparPorCargo(funcionarios);

        imprimirFuncionariosPorCargo(funcionariosMap);

        imprimirFuncionariosAniversarioDoMes(funcionarios, List.of(10, 12));

        imprimirFuncionarioMaisVelho(funcionarios);

        imprimirFuncionariosOrdenados(funcionarios);

        imprimirTotalSalarios(funcionarios);

        imprimirSalariosMinimos(funcionarios, new BigDecimal("1212.00"));

    }

    private static Funcionario buildFuncionario(String nome, LocalDate dataNascimento, BigDecimal salario, String cargo) {
        return new Funcionario(nome, dataNascimento, salario, cargo);
    }

    private static void removeFuncionarioPeloName(String nome, List<Funcionario> funcionarios) {
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getNome().equals(nome)) {
                funcionarios.remove(funcionario);
                break;
            }
        }
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        System.out.println("Todos os funcionários: ");
        funcionarios.forEach(System.out::println);
        System.out.println("------------------------------------------------------------");
    }

    private static void aumentarSalario(List<Funcionario> funcionarios, BigDecimal porcentagem) {
        for (Funcionario funcionario : funcionarios) {
            BigDecimal novoSalario = funcionario.getSalario().multiply(porcentagem).setScale(2, RoundingMode.HALF_UP);
            funcionario.setSalario(novoSalario);
        }
    }

    private static Map<String, List<Funcionario>> agruparPorCargo(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getCargo));
    }

    private static void imprimirFuncionariosPorCargo(Map<String, List<Funcionario>> funcionariosMap) {
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosMap.entrySet()) {
            System.out.println("------------------------------------------------------------");
            System.out.println(STR."Cargo: \{entry.getKey()}");
            for (Funcionario funcionario : entry.getValue()) {
                System.out.println(funcionario);
            }
            System.out.println("------------------------------------------------------------");
        }
    }

    private static void imprimirFuncionariosAniversarioDoMes(List<Funcionario> funcionarios, List<Integer> meses) {
        System.out.println("------------------------------------------------------------");
        System.out.println(STR."Funcionários que fazem aniversário nos meses \{meses}:");
        for (Funcionario funcionario : funcionarios) {
            if (meses.contains(funcionario.getDataNascimento().getMonthValue())) {
                System.out.println(funcionario);
            }
        }
        System.out.println("------------------------------------------------------------");
    }

    private static void imprimirFuncionarioMaisVelho(List<Funcionario> funcionarios) {
        Funcionario maisVelho = Collections.max(funcionarios, Comparator.comparingInt(f -> Period.between(f.getDataNascimento(), LocalDate.now()).getYears()));
        int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
        System.out.println("------------------------------------------------------------");
        System.out.println("Funcionário com a maior idade:");
        System.out.println(STR."Nome: \{maisVelho.getNome()}");
        System.out.println(STR."Idade: \{idade}");
        System.out.println("------------------------------------------------------------");
    }

    private static void imprimirFuncionariosOrdenados(List<Funcionario> funcionarios) {
        List<Funcionario> funcionariosOrdenados = new ArrayList<>(funcionarios);
        funcionariosOrdenados.sort(Comparator.comparing(Funcionario::getNome));

        System.out.println("------------------------------------------------------------");
        System.out.println("Funcionários em ordem alfabética:");
        for (Funcionario funcionario : funcionariosOrdenados) {
            System.out.println(funcionario);
        }
        System.out.println("------------------------------------------------------------");
    }

    private static void imprimirTotalSalarios(List<Funcionario> funcionarios) {
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        NumberFormat salaryFormatter = NumberFormat.getInstance(new Locale("pt", "BR"));
        salaryFormatter.setMinimumFractionDigits(2);
        salaryFormatter.setMaximumFractionDigits(2);

        System.out.println("------------------------------------------------------------");
        System.out.println("Total dos salários dos funcionários:");
        System.out.println(salaryFormatter.format(totalSalarios));
        System.out.println("------------------------------------------------------------");
    }

    private static void imprimirSalariosMinimos(List<Funcionario> funcionarios, BigDecimal salarioMinimo) {
        NumberFormat salaryFormatter = NumberFormat.getInstance(new Locale("pt", "BR"));
        salaryFormatter.setMinimumFractionDigits(2);
        salaryFormatter.setMaximumFractionDigits(2);

        System.out.println("------------------------------------------------------------");
        System.out.println("Quantos salários mínimos ganha cada funcionário:");
        for (Funcionario funcionario : funcionarios) {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(STR."Nome: \{funcionario.getNome()} - salarios minimos: \{salaryFormatter.format(salariosMinimos)}");
        }
        System.out.println("------------------------------------------------------------");
    }
}