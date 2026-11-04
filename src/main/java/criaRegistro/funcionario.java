package criaRegistro;

import java.time.Year;

public class funcionario {
    private int id_unico;
    private int CPF;
    private int Nascimento;
    private String Nome;
    private String Cargo;
    // criar um Finder
    /*
         O Finder devera verificar por todo o .txt e assim retornar true se existe um registro igual a ele
         olhando por:
            CPF
            Nome
         Se existir retorna TRUE se não retorna FALSE
     */

    // criar um Register
    /*
        o Register devera pegar
        CPF
        Nome
        Nascimento
        Cargo

     */
    // cria um Organizer
    /*
        O Organizer ele ira organizar cria fazes de organização removendo . ou / em nomes e CPFs
        ele é um metodo privado para organizar qualquer coisa
     */
    public int SetIdade(String Nascimento){
        int idade;
        int preNascimento = 0;
        try{
            preNascimento = Integer.parseInt(Nascimento);
        } catch (NumberFormatException e) {
            preNascimento = GetOrganizerNascimento(Nascimento);
        }finally {
            int anoNascimento;
            anoNascimento = Integer.parseInt(Integer.toString(preNascimento).substring(4));
            idade = Year.now().getValue() - anoNascimento;
        }
        return idade;
    }
    private int GetOrganizerNascimento(String Nascimento){
        //Organizar o Nascimento
        // 04/11/2026
        for (int i = 0; i < Nascimento.length(); i++) {
            if(Nascimento.equals("/"))
                Nascimento = Nascimento.replace("/","");
        }
        //04112026
        this.Nascimento = Integer.parseInt(Nascimento);
        return this.Nascimento;
    }
    private int GetOrganizerCPF(String CPF){
        int CPF;
        // Para organizar o CPF
        // in 000.000.000-69
        // out 00000000069
        return CPF;
    }
    public boolean GetFinder(String Nome,int CPF){
        //Aqui devera apenas retornar sim ou não ou seja ele ira procurar
        //linha por linha dentro do .txt e retornar true se o nome ou o CPF já existem
    }
}
