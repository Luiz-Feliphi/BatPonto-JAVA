package criaRegistro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;

public class funcionario {
    private int id_unico;
    private String CPF;
    private String Nome;
    private String Nascimento;
    private String Cargo;
    private String root = "src/main/java/TxtDB/dblList.txt";

    /*
        Formato de cada linha do .txt:
        *Nome*!CPF!                                  (registros antigos)
        %id%*Nome*!CPF!@Nascimento@#Cargo#           (registros novos)
    */

    /*METODOS PRIVADOS DA CLASSE FUNCIONARIO*/

    // Pega o texto que esta entre o primeiro par do marcador
    // Ex: PegarEntre("*Luiz*!123!", '*') -> Luiz
    private String PegarEntre(String linha, char marcador) {
        int inicio = linha.indexOf(marcador);
        if (inicio == -1)
            return "";
        int fim = linha.indexOf(marcador, inicio + 1);
        if (fim == -1)
            return "";
        return linha.substring(inicio + 1, fim);
    }

    // Tira os caracteres que usamos como marcador pra nao quebrar o .txt
    private String Limpar(String texto) {
        String limpo = texto.replaceAll("[*!@#%]", "").trim();
        if (limpo.isEmpty())
            throw new IllegalArgumentException("Campo vazio");
        return limpo;
    }

    private String GetOrganizerCPF(String CPF) {
        // Para organizar o CPF
        // in 000.000.000-69
        String CPFLimpo = "";
        for (int i = 0; i < CPF.length(); i++) {
            if (Character.isDigit(CPF.charAt(i)))
                CPFLimpo += CPF.charAt(i);
        }
        // out 00000000069
        return CPFLimpo;
    }

    // 00000000069 -> 000.000.000-69 (como fica salvo no .txt)
    private String GetFormatCPF(String CPF) {
        return CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." + CPF.substring(6, 9) + "-" + CPF.substring(9);
    }

    private String GetOrganizerNascimento(String Nascimento) {
        //Organizar o Nascimento
        // in 04/11/2026 ou 04112026
        String limpo = Nascimento.replace("/", "");
        if (limpo.length() != 8)
            throw new IllegalArgumentException("Nascimento invalido: " + Nascimento);
        // out 04/11/2026
        return limpo.substring(0, 2) + "/" + limpo.substring(2, 4) + "/" + limpo.substring(4);
    }

    private String GetOrganizerName(String nome) {
        // deixa tudo minusculo e com um espaco so entre as palavras
        // pra comparar "Luiz  Feliphi" com "luiz feliphi"
        return nome.trim().toLowerCase().replaceAll("\\s+", " ");
    }

    // Pega o proximo id livre olhando o maior %id% que ja existe no .txt
    private int GetNextId() throws IOException {
        int maior = 0;
        Path DBpath = Path.of(root);
        if (!Files.exists(DBpath))
            return 1;
        List<String> linhas = Files.readAllLines(DBpath);
        for (String linha : linhas) {
            String id = PegarEntre(linha, '%');
            if (!id.isEmpty() && Integer.parseInt(id) > maior)
                maior = Integer.parseInt(id);
        }
        return maior + 1;
    }

    /*METODOS PUBLICOS DA CLASSE FUNCIONARIO*/

    //Setar a Idade da pessoa passando o parametro da data de nascimento
    /*
     * Exemplo-IN: 05/11/1999
     * Exemplo-PRO: 2026-1999 (e tira 1 se ainda nao fez aniversario)
     * Exemplo-OUT: 26 ou 27
     */
    public int SetIdade(String Nascimento) {
        String[] partes = GetOrganizerNascimento(Nascimento).split("/");
        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int ano = Integer.parseInt(partes[2]);

        LocalDate hoje = LocalDate.now();
        int idade = hoje.getYear() - ano;
        if (hoje.getMonthValue() < mes || (hoje.getMonthValue() == mes && hoje.getDayOfMonth() < dia))
            idade--;
        return idade;
    }

    // Procura o funcionario pelo CPF e devolve o nome dele ("" se nao achar)
    public String getNome(String CPFFuncionario) throws IOException {
        String CPFTratado = GetOrganizerCPF(CPFFuncionario);
        Path DBpath = Path.of(root);
        if (!Files.exists(DBpath))
            return "";
        List<String> linhas = Files.readAllLines(DBpath);
        for (String linha : linhas) {
            if (GetOrganizerCPF(PegarEntre(linha, '!')).equals(CPFTratado)) {
                this.Nome = PegarEntre(linha, '*');
                return this.Nome;
            }
        }
        return "";
    }

    //Procurar pelo funcionario dentro do .txt
    /*
        O Finder verifica linha por linha do .txt e retorna true se existe um registro igual
        olhando por:
            CPF
            Nome
        Se existir retorna TRUE se não retorna FALSE
     */
    public boolean GetFinder(String Nome, String CPF) throws IOException {
        String CPFTratado = GetOrganizerCPF(CPF);
        String NomeTratado = GetOrganizerName(Nome);

        Path DBpath = Path.of(root);
        if (!Files.exists(DBpath))
            return false;

        List<String> linhas = Files.readAllLines(DBpath);
        for (String linha : linhas) {
            String CPFArquivo = GetOrganizerCPF(PegarEntre(linha, '!'));
            String NomeArquivo = GetOrganizerName(PegarEntre(linha, '*'));
            // achou o CPF ou o nome dentro da lista
            if (CPFArquivo.equals(CPFTratado) || NomeArquivo.equals(NomeTratado))
                return true;
        }
        return false;
    }

    // O Register pega CPF, Nome, Nascimento e Cargo e salva no .txt
    // Retorna TRUE se salvou e FALSE se ja existia
    public boolean Register(String Nome, String CPF, String Nascimento, String Cargo) throws IOException {
        String CPFTratado = GetOrganizerCPF(CPF);
        if (CPFTratado.length() != 11)
            throw new IllegalArgumentException("CPF precisa ter 11 digitos");

        String NomeLimpo = Limpar(Nome);
        String CargoLimpo = Limpar(Cargo);
        String NascimentoTratado = GetOrganizerNascimento(Nascimento);

        if (GetFinder(NomeLimpo, CPFTratado))
            return false;

        this.id_unico = GetNextId();
        this.CPF = CPFTratado;
        this.Nome = NomeLimpo;
        this.Nascimento = NascimentoTratado;
        this.Cargo = CargoLimpo;

        String linha = "%" + id_unico + "%*" + this.Nome + "*!" + GetFormatCPF(this.CPF) + "!@" + this.Nascimento + "@#" + this.Cargo + "#";

        Path DBpath = Path.of(root);
        Files.createDirectories(DBpath.getParent());

        // se o ultimo registro nao terminou com quebra de linha, quebra antes
        String prefixo = "";
        if (Files.exists(DBpath)) {
            String atual = Files.readString(DBpath);
            if (!atual.isEmpty() && !atual.endsWith("\n"))
                prefixo = System.lineSeparator();
        }
        Files.writeString(DBpath, prefixo + linha + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        return true;
    }

    public int getId_unico() { return id_unico; }
    public String getCPF() { return CPF; }
    public String getNascimento() { return Nascimento; }
    public String getCargo() { return Cargo; }
}