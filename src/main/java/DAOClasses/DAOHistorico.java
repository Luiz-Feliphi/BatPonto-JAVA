package DAOClasses;

import ModelClasses.criaRegistro.Historico;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DAOHistorico {
    /*
        O historico fica no TXT (TxtDB/dblHistorico.txt), uma linha por batimento:
        id_unico;id_funcionario;id_local;dd/MM/yyyy;HH:mm
        exemplo
        1;3;2;23/11/2026;14:35
    */
    private static final String rootPath = CheckPath.getPath();

    // Create: acrescenta uma linha no final do TXT
    public static boolean createHistorico(Historico historico){
        try (BufferedWriter Escrever = new BufferedWriter(new FileWriter(rootPath, true))){
            Escrever.write(formateToFile(historico));
            Escrever.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar historico no arquivo: " + rootPath);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Read: le o TXT inteiro, na ordem em que os batimentos foram registrados
    public static List<Historico> readHistoricos(){
        List<Historico> historicos = new ArrayList<Historico>();
        if (!new File(rootPath).isFile()) {
            return historicos;
        }
        try (BufferedReader Ler = new BufferedReader(new FileReader(rootPath))){
            String linha;
            while ((linha = Ler.readLine()) != null) {
                Historico h = formateFromFile(linha);
                if (h != null) {
                    historicos.add(h);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler historico no arquivo: " + rootPath);
            e.printStackTrace();
        }
        return historicos;
    }

    // Read: so os batimentos de um funcionario
    public static List<Historico> readHistoricosPorFuncionario(int id_funcionario){
        List<Historico> doFuncionario = new ArrayList<Historico>();
        for (Historico h : readHistoricos()) {
            if (h.getId_funcionario() == id_funcionario) {
                doFuncionario.add(h);
            }
        }
        return doFuncionario;
    }

    private static String formateToFile(Historico h) {
        return  h.getId_unico()+";"+
                h.getId_funcionario()+";"+
                h.getId_local()+";"+
                h.getData()+";"+
                h.getHora();
    }

    // transforma uma linha do TXT em Historico. Linha vazia ou mal formada vira null (e e ignorada)
    private static Historico formateFromFile(String linha) {
        String[] campos = linha.trim().split(";");
        if (campos.length != 5) {
            return null;
        }
        try {
            return new Historico(Integer.parseInt(campos[0]), Integer.parseInt(campos[1]),
                    Integer.parseInt(campos[2]), campos[3], campos[4]);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
