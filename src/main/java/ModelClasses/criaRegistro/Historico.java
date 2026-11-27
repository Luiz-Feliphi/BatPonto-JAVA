package ModelClasses.criaRegistro;

public class Historico {
    // Um batimento de ponto. Guarda so os ids do funcionario e do local (e nao os objetos)
    // porque o historico e gravado no TXT, uma linha por batimento.
    private int id_unico;
    private int id_funcionario;
    private int id_local; // 0 quando o funcionario nao tinha local cadastrado
    private String data;  // dd/MM/yyyy
    private String hora;  // HH:mm
    //pojo
    public Historico(){}//Constructor
    public Historico(int id_unico, int id_funcionario, int id_local, String data, String hora){
        this.id_unico = id_unico;
        this.id_funcionario = id_funcionario;
        this.id_local = id_local;
        this.data = data;
        this.hora = hora;
    }

    public int getId_unico() {
        return id_unico;
    }
    public void setId_unico(int id_unico) {
        this.id_unico = id_unico;
    }

    public int getId_funcionario() {
        return id_funcionario;
    }
    public void setId_funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public int getId_local() {
        return id_local;
    }
    public void setId_local(int id_local) {
        this.id_local = id_local;
    }

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Historico [id_unico=" + id_unico + ", id_funcionario=" + id_funcionario
                + ", id_local=" + id_local + ", data=" + data + ", hora=" + hora + "]";
    }
}
