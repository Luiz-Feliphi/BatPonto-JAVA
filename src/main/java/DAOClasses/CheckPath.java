package DAOClasses;

import java.io.File;
import java.io.IOException;

class CheckPath {
    private static final String root = "src/main/java/TxtDB/dblList.txt";
    public static String getPath(){ // Aqui é um constructo a chave de ignição aqui realmente precisamos declarar o nome dele como o nome da classe
        inicializer();
        return root;
    }
    private static void inicializer(){
        File File = new File(root);
        File pathMon = File.getParentFile();
        try {
            if(pathMon != null && !pathMon.exists()){
                pathMon.mkdirs();
                System.out.println("PASTAS"+File.getParent()+"CRIADAS");
            }
            if(!File.exists()){
                File.createNewFile();
            }
        }catch (IOException e) {
            System.err.println("Erro ao criar a pasta ou o arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
