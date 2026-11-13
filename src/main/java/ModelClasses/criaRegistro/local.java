package ModelClasses.criaRegistro;

public class Local {
    private int id_unico;
    private String endereco;
    private String setor;
    private Funcionario funcionario;
    //pojo

    public String getEndereco(){ return this.endereco;}
    public boolean setEndereco(String endereco){ 
        if(endereco != null){
            this.endereco = endereco;
            return true; //encerra 
        }
        return false;// endererra caso nulo
    }



}
