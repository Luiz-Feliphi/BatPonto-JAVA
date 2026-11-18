package ModelClasses.criaRegistro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class Funcionario {
    private String CPF;
    private String Nome;
    private String Nascimento;
    private String Cargo;
    private int id_unico;
    //pojo
    public Funcionario(){}
    public Funcionario( String CPF, String Nome, String Nascimento, String Cargo, int id_unico){
        this.CPF = CPF;
        this.Nome = Nome;
        this.Nascimento = Nascimento;
        this.Cargo = Cargo;
        this.id_unico = id_unico;
    }
    public int getId_unico() {
        return id_unico;
    }
    public void setId_unico(int id_unico) {
        this.id_unico = id_unico;
    }

    public String getCPF() {
        return CPF;
    }
    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return Nome;
    }
    public void setNome(String nome) {
        Nome = nome;
    }

    public String getNascimento() {
        return Nascimento;
    }
    public void setNascimento(String nascimento) {
        Nascimento = nascimento;
    }

    public String getCargo() {
        return Cargo;
    }
    public void setCargo(String cargo) {
        Cargo = cargo;
    }
}