package org.example.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Funcionario extends Pessoa{
    private BigDecimal salario;
    private String cargo;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String cargo) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }
    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat salaryFormatter = NumberFormat.getInstance(new Locale("pt", "BR"));
        salaryFormatter.setMinimumFractionDigits(2);
        salaryFormatter.setMaximumFractionDigits(2);

        return STR."Funcionario: nome = \{getNome()}, dataNascimento = \{timeFormatter.format(getDataNascimento())}, salario = \{salaryFormatter.format(salario)}, cargo = \{cargo}";
    }
}
