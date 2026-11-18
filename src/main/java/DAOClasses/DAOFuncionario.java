package DAOClasses;

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
    public static void main(String[] args) throws IOException {
        FileWriter file = new FileWriter(rootPath);
        file.write("oi");
        file.close();
    }

    public boolean readFuncionario(String nomeFuncionario, int CPFfuncionario){

    //a parte de processamento aqui no dao pporem organizado e separado
        return false;
   }
   public boolean createFuncionario(){
        return false;
   }

    public String getRootPath() {
        return rootPath;
    }
}
