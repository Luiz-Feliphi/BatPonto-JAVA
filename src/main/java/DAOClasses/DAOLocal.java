package DAOClasses;

import ModelClasses.criaRegistro.Local;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DAOLocal {

    // cada local vira um arquivo: TxtDB/locais/{id_unico}.dat
    private static String caminho(int id_unico){
        return CheckPath.rootLocais + id_unico + CheckPath.extensao;
    }

    // Create
    public static boolean createLocal(Local local){
        return Arquivos.gravarObjeto(caminho(local.getId_unico()), local);
    }

    // Read (um local pelo id). Retorna null se nao existir
    public static Local readLocal(int id_unico){
        return Arquivos.lerObjeto(caminho(id_unico), Local.class);
    }

    // Read (todos, ordenados pelo id)
    public static List<Local> readLocais(){
        List<Local> locais = new ArrayList<Local>();
        String[] arquivos = new File(CheckPath.rootLocais).list();
        if (arquivos == null) {
            return locais;
        }
        for (String arquivo : arquivos) {
            Local l = Arquivos.lerObjeto(CheckPath.rootLocais + arquivo, Local.class);
            if (l != null) {
                locais.add(l);
            }
        }
        locais.sort(Comparator.comparingInt(Local::getId_unico));
        return locais;
    }

    // Update (so atualiza quem ja existe)
    public static boolean updateLocal(Local local){
        if (!new File(caminho(local.getId_unico())).isFile()) {
            return false;
        }
        return Arquivos.gravarObjeto(caminho(local.getId_unico()), local);
    }

    // Delete
    public static boolean deleteLocal(int id_unico){
        File arquivo = new File(caminho(id_unico));
        return arquivo.isFile() && arquivo.delete();
    }
}
