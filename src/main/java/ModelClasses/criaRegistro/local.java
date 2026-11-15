package ModelClasses.criaRegistro;

public class Local {
    private int id_unico;
    private String endereco;
    private String setor;
    private Funcionario funcionario;
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
        return funcionario;
    }
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
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


}
