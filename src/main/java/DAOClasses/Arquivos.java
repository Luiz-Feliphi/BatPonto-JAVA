package DAOClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;

// Parte "mecanica" dos DAOs: controle de ids e leitura/gravacao de objetos em arquivo.
// Os DAOs (DAOFuncionario, DAOLocal...) usam esta classe para nao repetir codigo.
public class Arquivos {

    // ---------------------------------------------------------------- ids

    private static String arquivoId(String nome) {
        return CheckPath.rootIds + "id" + nome + ".txt";
    }

    // so cria o arquivo de id (com 0) se ele ainda nao existir
    static void criaIdSeNecessario(String nome) {
        if (!new File(arquivoId(nome)).exists()) {
            gravarId(nome, 0);
        }
    }

    public static void incrementaId(String nome) {
        int id = lerId(nome);
        id++;
        gravarId(nome, id);
    }

    public static void gravarId(String nome, int i) {
        try (PrintWriter print = new PrintWriter(new FileWriter(arquivoId(nome)))) {
            print.print(i);
        } catch (IOException e) {
            System.out.println("Falha ao gravar o id de " + nome);
            e.printStackTrace();
        }
    }

    // retorna o ultimo id usado (0 se ainda nao tem registros) ou -1 em caso de falha
    public static int lerId(String nome) {
        try (Scanner sc = new Scanner(new File(arquivoId(nome)))) {
            return sc.nextInt();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de id nao encontrado: " + arquivoId(nome));
            e.printStackTrace();
        }
        return -1;
    }

    // ---------------------------------------------------------- objetos

    public static boolean gravarObjeto(String caminho, Serializable objeto) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminho))) {
            out.writeObject(objeto);
        } catch (IOException e) {
            System.out.println("Falha ao gravar o arquivo " + caminho);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // le um objeto do arquivo. Retorna null se o arquivo nao existir ou der erro
    public static <T> T lerObjeto(String caminho, Class<T> tipo) {
        File arquivo = new File(caminho);
        if (!arquivo.isFile()) {
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return tipo.cast(in.readObject());
        } catch (ClassNotFoundException e) {
            System.out.println("Classe nao encontrada!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Falha ao acessar o arquivo " + caminho);
            e.printStackTrace();
        }
        return null;
    }
}
