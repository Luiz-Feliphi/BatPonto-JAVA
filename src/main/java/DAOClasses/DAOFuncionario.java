package DAOClasses;

import ModelClasses.criaRegistro.Funcionario;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class DAOFuncionario {
    /*
        Até onde eu entendi
        DAO é uma classe onde ele ira realizar a ação de salvar, não sei se é assim mas
        o DAO puxa outras classes usadas para salvar que será as classes de processamento
        exemplo
        DAOFunncionario
        DAO é crud
        public 
    */
    private static final String rootPath = CheckPath.getPath();

//    public static void main(String[] args) throws IOException {
//        FileWriter file = new FileWriter(rootPath);
//        file.write("oi");
//        file.close();
//    }

    public boolean readFuncionario(String nomeFuncionario, int CPFfuncionario){

    //a parte de processamento aqui no dao pporem organizado e separado
        return false;
   }
   public static void salvarFuncionario(Funcionario funcionario){
        //Manda para outro arquivo para ele organizar
       //novamalmente ficaria assim

       /*
       * ! inicio de bloco
       * *000.000.000.00*
       * #Luiz Feliphi Lemos de Souza Lima#
       * &Cargo do funcionario&
       * %Nascimento%
       * !fim de bloco
       * ! inicio de bloco
       * $id$
       * *000.000.000.00*
       * #Luiz Feliphi Lemos de Souza Lima#
       * &Cargo do funcionario&
       * %Nascimento%
       * !fim de bloco
       * */

       String linha = formateToFileTest(funcionario);
       try (BufferedWriter Escrever = new BufferedWriter(new FileWriter(rootPath, true))){
           Escrever.write(linha);
           System.out.println(linha);
           Escrever.newLine();
       } catch (IOException e) {
           throw new RuntimeException("Erro ao salvar funcionário no arquivo: " + rootPath, e);
       }
   }

    private String formateToFile(Funcionario fun) {
        return  "$"+fun.getId_unico()+"$"+
                "*"+fun.getCPF()+"*"+
                "#"+fun.getNome()+"#"+
                "&"+fun.getCargo()+"&"+
                "%"+fun.getNascimento()+"%";
    }

    public static void main(String[] args) {
        salvarFuncionario(new Funcionario("000.000.000.00","Luiz Feliphi Lemos de Souza Lima","08/11/2006", "Programador",1));
    }
    //Apenas um metodo test
    private static String formateToFileTest(Funcionario fun) {
        return  "$"+1+"$"+
                "*"+"091.204.361.06"+"*"+
                "#"+"Luiz Feliphi Lemos de SOuza Lima"+"#"+
                "&"+"Programador(escravo)"+"&"+
                "%"+"08/05/2006"+"%";
    }

    public boolean createFuncionario(){
        return false;
   }

    public String getRootPath() {
        return rootPath;
    }
}
