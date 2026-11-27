package DAOClasses;

import java.io.File;
import java.io.IOException;

public class CheckPath {
    // Aqui ficam todos os caminhos do "banco" em TXT
    //   TxtDB/
    //     funcionarios/   -> 1.dat, 2.dat ...
    //     locais/         -> 1.dat, 2.dat ...
    //     ids/            -> idfuncionarios.txt, idlocais.txt, idhistorico.txt (ultimo id usado)
    //     dblHistorico.txt -> uma linha por batimento de ponto
    public static final String root = "src/main/java/TxtDB/";
    public static final String rootFuncionarios = root + "funcionarios/";
    public static final String rootLocais = root + "locais/";
    public static final String rootIds = root + "ids/";
    public static final String extensao = ".dat";
    private static final String rootHistorico = root + "dblHistorico.txt";

    public static String getPath(){ // Aqui é um constructo a chave de ignição aqui realmente precisamos declarar o nome dele como o nome da classe
        inicializer();
        return rootHistorico;
    }

    // cria as pastas e arquivos que ainda nao existem, sem apagar nada
    public static void inicializer(){
        try {
            String[] pastas = {rootFuncionarios, rootLocais, rootIds};
            for (String pasta : pastas) {
                File dir = new File(pasta);
                if (!dir.exists()) {
                    dir.mkdirs();
                    System.out.println("PASTA " + pasta + " CRIADA");
                }
            }
            File historico = new File(rootHistorico);
            if (!historico.exists()) {
                historico.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar a pasta ou o arquivo: " + e.getMessage());
            e.printStackTrace();
        }
        Arquivos.criaIdSeNecessario("funcionarios");
        Arquivos.criaIdSeNecessario("locais");
        Arquivos.criaIdSeNecessario("historico");
    }

    // apaga TUDO que esta em TxtDB e recria do zero com os ids zerados
    public static void resetar(){
        File dir = new File(root);
        if (dir.isDirectory()) {
            if (excluirDiretorio(dir)) {
                System.out.println(dir.getName() + " - Excluido com sucesso!");
            } else {
                System.out.println("Falha na exclusao de " + dir.getName());
            }
        }
        inicializer();
    }

    private static boolean excluirDiretorio(File dir) {
        if (dir.isDirectory()) {
            String[] arquivos = dir.list();
            if (arquivos != null) {
                for (String nome : arquivos) {
                    if (!excluirDiretorio(new File(dir, nome))) {
                        System.out.println("Nao pode ser excluido: " + nome);
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }
}
