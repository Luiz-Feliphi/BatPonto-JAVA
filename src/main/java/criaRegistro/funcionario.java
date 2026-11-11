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
    private String root = PathRoot("src/main/java/TxtDB/dblList.txt");
    private String PathRoot(String rootPath) {
        try {
            Path path = Path.of(rootPath);
            Path pasta = path.getParent();
            //cria a pasta TxtDB caso ela não exista
            if (pasta != null && !Files.exists(pasta)) {
                Files.createDirectories(pasta);
                System.out.println("Pasta"+pasta+" criada com sucesso: ");
            }
            //cria o arquivo dbList.txt caso ele não exista
            if (!Files.exists(path)) {
                Files.createFile(path);
                System.out.println("Arquivo "+path.getFileName()+" criado com sucesso: ");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro crítico ao tentar criar a estrutura de arquivos", e);
        }

        return rootPath;
    }

    /*METODOS PRIVADOS DA CLASSE FUNCIONARIO*/

    // Pega o texto entre o primeiro par do marcador
    private String PegarEntre(String linha, char marcador) {
        int inicio = linha.indexOf(marcador);
        if (inicio == -1)
            return "";
        int fim = linha.indexOf(marcador, inicio + 1);
        if (fim == -1)
            return "";
        return linha.substring(inicio + 1, fim);
    }

    // Tira os marcadores do texto
    private String Limpar(String texto) {
        String limpo = "";
        for (int i = 0; i < texto.length(); i++) {
            char letra = texto.charAt(i);
            if ("*!@#%".indexOf(letra) == -1)
                limpo += letra;
        }
        limpo = limpo.trim();
        if (limpo.isEmpty())
            throw new IllegalArgumentException("Campo vazio");
        return limpo;
    }

    // 000.000.000-69 -> 00000000069
    private String GetOrganizerCPF(String CPF) {
        String CPFLimpo = "";
        for (int i = 0; i < CPF.length(); i++) {
            if (Character.isDigit(CPF.charAt(i)))
                CPFLimpo += CPF.charAt(i);
        }
        return CPFLimpo;
    }

    // 00000000069 -> 000.000.000-69
    private String GetFormatCPF(String CPF) {
        return CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." + CPF.substring(6, 9) + "-" + CPF.substring(9);
    }

    // 04112026 -> 04/11/2026
    private String GetOrganizerNascimento(String Nascimento) {
        String limpo = Nascimento.replace("/", "");
        if (limpo.length() != 8)
            throw new IllegalArgumentException("Nascimento invalido");
        return limpo.substring(0, 2) + "/" + limpo.substring(2, 4) + "/" + limpo.substring(4);
    }

    private String GetOrganizerName(String nome) {
        return nome.trim().toLowerCase();
    }

    // Maior id do .txt + 1
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

    /*
     * Exemplo-IN: 05/11/1999
     * Exemplo-OUT: 27
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

    // Procura pelo CPF e devolve o nome
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

    // Retorna TRUE se o CPF ou o Nome ja existem no .txt
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
            if (CPFArquivo.equals(CPFTratado) || NomeArquivo.equals(NomeTratado))
                return true;
        }
        return false;
    }

    // Salva no .txt. Retorna TRUE se salvou e FALSE se ja existia
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