package ModelClasses.criaRegistro;

import java.io.Serializable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Funcionario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String CPF;
    private String Nome;
    private String Nascimento;
    private String Cargo;
    private int id_unico;
    private Local local;
    //pojo
    public Funcionario(){}//Constructor
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

    public Local getLocal() {
        return local;
    }
    public void setLocal(Local local) {
        this.local = local;
    }

    @Override
    public String toString() {
        // so mostra o id e o setor do local, porque o Local tambem guarda um Funcionario (o gerente)
        // e imprimir o objeto inteiro entraria em recursao infinita
        String infoLocal = (local == null) ? "SEM ID" : local.getId_unico() + " (" + local.getSetor() + ")";
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        return
                "----Funcionario--- " +"\n"+
                "id:" + id_unico +"\n"+
                "Nome:" + Nome +"\n"+
                "CPF:" + CPF+"\n"+
                "Nascimento:" + Nascimento +"\n"+
                "Cargo:" + Cargo +"\n"+
                "Local:" + infoLocal;
    }
}
