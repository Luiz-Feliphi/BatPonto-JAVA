package ModelClasses.criaRegistro;

import java.io.Serializable;

public class Local implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id_unico;
    private String endereco;
    private String setor;
    private Funcionario gerente;
    // Aqui é o gerente do local cada local tem o seu gerente responsavel por ele
    // com isso é possivel puxar o responsavel do local
    // e fazer um menu para puxar o responsavel por cada local

    public int getId_unico() {
        return id_unico;
    }
    public String getEndereco() {
        return endereco;
    }
    public String getSetor() {
        return setor;
    }
    public Funcionario getFuncionario() {
        return gerente;
    }

    public void setFuncionario(Funcionario gerente) {
        this.gerente = gerente;
    }
    public void setSetor(String setor) {
        this.setor = setor;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public void setId_unico(int id_unico) {
        this.id_unico = id_unico;
    }
    //pojo

    @Override
    public String toString() {
        // so mostra o nome do gerente para nao entrar em recursao com o Funcionario
        String infoGerente = (gerente == null) ? "-" : gerente.getNome();
        return "Local [id_unico=" + id_unico + ", setor=" + setor + ", endereco=" + endereco
                + ", gerente=" + infoGerente + "]";
    }
}
