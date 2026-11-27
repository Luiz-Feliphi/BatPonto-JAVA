package DAOClasses;

import ModelClasses.criaRegistro.Funcionario;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    // cada funcionario vira um arquivo: TxtDB/funcionarios/{id_unico}.dat
    private static String caminho(int id_unico){
        return CheckPath.rootFuncionarios + id_unico + CheckPath.extensao;
    }

    // Create
    public static boolean createFuncionario(Funcionario funcionario){
        return Arquivos.gravarObjeto(caminho(funcionario.getId_unico()), funcionario);
    }

    // Read (um funcionario pelo id). Retorna null se nao existir
    public static Funcionario readFuncionario(int id_unico){
        return Arquivos.lerObjeto(caminho(id_unico), Funcionario.class);
    }

    // Read (todos, ordenados pelo id)
    public static List<Funcionario> readFuncionarios(){
        List<Funcionario> funcionarios = new ArrayList<Funcionario>();
        String[] arquivos = new File(CheckPath.rootFuncionarios).list();
        if (arquivos == null) {
            return funcionarios;
        }
        for (String arquivo : arquivos) {
            Funcionario f = Arquivos.lerObjeto(CheckPath.rootFuncionarios + arquivo, Funcionario.class);
            if (f != null) {
                funcionarios.add(f);
            }
        }
        funcionarios.sort(Comparator.comparingInt(Funcionario::getId_unico));
        return funcionarios;
    }

    // Update (so atualiza quem ja existe)
    public static boolean updateFuncionario(Funcionario funcionario){
        if (!new File(caminho(funcionario.getId_unico())).isFile()) {
            return false;
        }
        return Arquivos.gravarObjeto(caminho(funcionario.getId_unico()), funcionario);
    }

    // Delete
    public static boolean deleteFuncionario(int id_unico){
        File arquivo = new File(caminho(id_unico));
        return arquivo.isFile() && arquivo.delete();
    }
}
