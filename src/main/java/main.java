import DAOClasses.Arquivos;
import DAOClasses.CheckPath;
import DAOClasses.DAOFuncionario;
import DAOClasses.DAOHistorico;
import DAOClasses.DAOLocal;
import ModelClasses.criaRegistro.Funcionario;
import ModelClasses.criaRegistro.Historico;
import ModelClasses.criaRegistro.Local;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Scanner;

public class main {
    //registro do funcionario
    //registra o local que ele pertence puxando o ID unico do funcionario

    //-BATIMENTO DE PONTO-//
    //registra o historico de batimento de quando ele insere o ID unico dele
    //registra o historico de batimeto de ponto

    //-RESGATE DE HISTORICO-//
    //adiquiri o historico baseado em outro arquivo que puxa o ID unico de batimento de ponto do usuario
    //DAO
    /*
     * Data
     * Acesses
     * Operations
     * fica responsavel pra acessar o objeto ou um banco dados
     * para deixa de forma fixa ali sem alterar nada
     * */
    //Apresentar o projeto até dia 2 de Dezembro
    /*
     * ---CRUD---
     * Create
     * Read
     * Update
     * Delete
     * */

    // Um unico Scanner para o programa inteiro (varios Scanners em System.in perdem entrada)
    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        CheckPath.inicializer();
        int opcao;
        System.out.print("Escolha a oção de inteface UI(swing) ou CMD (1 ou 2): ");
        opcao = lerInt("Opção: ");
       // if (opcao == 1) {
       //   UI.main(args);
       // } else 
        //if (opcao != 2) {        
            do {
                menuPrincipal();
                opcao = lerInt("Opção: ");
                switch (opcao) {
                    case 1: // funcionarios
                        controllerFuncionario();
                        break;
                    case 2: // locais
                        controllerLocal();
                        break;
                    case 3: // batimento de ponto
                        controllerPonto();
                        break;
                    case 4: // resetar ou inicializar a base
                        if (confirma("Isso apaga TODOS os dados. Continuar? (s/n): ")) {
                            CheckPath.resetar();
                        }
                        break;
                    case 5: // saida
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } while (opcao != 5);
        //}
    }

    // ================================================================ FUNCIONARIOS

    private static void controllerFuncionario() {
        int opcao;
        do {
            System.out.println("FUNCIONÁRIOS");
            menuSecundario();
            opcao = lerInt("Opção: ");
            switch (opcao) {
                case 1: // cadastrar
                    cadastrarFuncionario();
                    break;
                case 2: // listar
                    imprimeLista(DAOFuncionario.readFuncionarios());
                    break;
                case 3: // buscar
                    buscarFuncionario();
                    break;
                case 4: // atualizar
                    atualizarFuncionario();
                    break;
                case 5: // deletar
                    deletarFuncionario();
                    break;
                case 6: // voltar
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 6);
    }

    private static void cadastrarFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId_unico(Arquivos.lerId("funcionarios") + 1); // puxa automatico
        preencheFuncionario(funcionario);
        if (DAOFuncionario.createFuncionario(funcionario)) {
            Arquivos.incrementaId("funcionarios");
            System.out.println("Funcionário salvo com sucesso!");
        } else {
            System.out.println("Falha ao salvar o funcionário!");
        }
    }

    private static void buscarFuncionario() {
        int id = lerInt("ID do funcionário: ");
        Funcionario funcionario = DAOFuncionario.readFuncionario(id);
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado!");
        } else {
            System.out.println(funcionario);
        }
    }

    private static void atualizarFuncionario() {
        int id = lerInt("ID do funcionário: ");
        Funcionario funcionario = DAOFuncionario.readFuncionario(id);
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado!");
            return;
        }
        System.out.println("Atual: " + funcionario);
        System.out.println("Digite os novos dados:");
        preencheFuncionario(funcionario);
        if (DAOFuncionario.updateFuncionario(funcionario)) {
            System.out.println("Funcionário atualizado com sucesso!");
        } else {
            System.out.println("Falha ao atualizar o funcionário!");
        }
    }

    private static void deletarFuncionario() {
        int id = lerInt("ID do funcionário: ");
        if (DAOFuncionario.deleteFuncionario(id)) {
            System.out.println("Funcionário deletado com sucesso!");
        } else {
            System.out.println("Funcionário não encontrado!");
        }
    }

    // pede os dados pelo teclado e preenche o funcionario recebido (o id_unico nao e alterado)
    private static void preencheFuncionario(Funcionario funcionario) {
        funcionario.setNome(lerTexto("Nome: "));
        funcionario.setCPF(lerTexto("CPF: "));
        funcionario.setNascimento(lerData("Nascimento (dd/mm/aaaa): "));
        funcionario.setCargo(lerTexto("Cargo: "));
        funcionario.setLocal(escolheLocal());
    }

    private static Local escolheLocal() {
        while (true) {
            int id = lerInt("ID do local (0 para nenhum): ");
            if (id == 0) {
                return null;
            }
            Local local = DAOLocal.readLocal(id);
            if (local != null) {
                return local;
            }
            System.out.println("Local não encontrado!");
        }
    }

    // ===================================================================== LOCAIS

    private static void controllerLocal() {
        int opcao;
        do {
            System.out.println("LOCAIS");
            menuSecundario();
            opcao = lerInt("Opção: ");
            switch (opcao) {
                case 1: // cadastrar
                    cadastrarLocal();
                    break;
                case 2: // listar
                    imprimeLista(DAOLocal.readLocais());
                    break;
                case 3: // buscar
                    buscarLocal();
                    break;
                case 4: // atualizar
                    atualizarLocal();
                    break;
                case 5: // deletar
                    deletarLocal();
                    break;
                case 6: // voltar
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 6);
    }

    private static void cadastrarLocal() {
        Local local = new Local();
        local.setId_unico(Arquivos.lerId("locais") + 1); // puxa automatico
        preencheLocal(local);
        if (DAOLocal.createLocal(local)) {
            Arquivos.incrementaId("locais");
            System.out.println("Local salvo com sucesso!");
        } else {
            System.out.println("Falha ao salvar o local!");
        }
    }

    private static void buscarLocal() {
        int id = lerInt("ID do local: ");
        Local local = DAOLocal.readLocal(id);
        if (local == null) {
            System.out.println("Local não encontrado!");
        } else {
            System.out.println(local);
        }
    }

    private static void atualizarLocal() {
        int id = lerInt("ID do local: ");
        Local local = DAOLocal.readLocal(id);
        if (local == null) {
            System.out.println("Local não encontrado!");
            return;
        }
        System.out.println("Atual: " + local);
        System.out.println("Digite os novos dados:");
        preencheLocal(local);
        if (DAOLocal.updateLocal(local)) {
            System.out.println("Local atualizado com sucesso!");
        } else {
            System.out.println("Falha ao atualizar o local!");
        }
    }

    private static void deletarLocal() {
        int id = lerInt("ID do local: ");
        if (DAOLocal.deleteLocal(id)) {
            System.out.println("Local deletado com sucesso!");
        } else {
            System.out.println("Local não encontrado!");
        }
    }

    // pede os dados pelo teclado e preenche o local recebido (o id_unico nao e alterado)
    private static void preencheLocal(Local local) {
        local.setSetor(lerTexto("Setor: "));
        local.setEndereco(lerTexto("Endereço: "));
        local.setFuncionario(escolheGerente());
    }

    private static Funcionario escolheGerente() {
        while (true) {
            int id = lerInt("ID do funcionário gerente (0 para nenhum): ");
            if (id == 0) {
                return null;
            }
            Funcionario gerente = DAOFuncionario.readFuncionario(id);
            if (gerente != null) {
                return gerente;
            }
            System.out.println("Funcionário não encontrado!");
        }
    }

    // ========================================================== BATIMENTO DE PONTO

    private static void controllerPonto() {
        int opcao;
        do {
            System.out.println("BATIMENTO DE PONTO");
            menuPonto();
            opcao = lerInt("Opção: ");
            switch (opcao) {
                case 1: // bater ponto
                    baterPonto();
                    break;
                case 2: // historico completo (lido do TXT)
                    imprimeHistorico(DAOHistorico.readHistoricos());
                    break;
                case 3: // historico de um funcionario
                    historicoDoFuncionario();
                    break;
                case 4: // voltar
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 4);
    }

    private static void baterPonto() {
        int id = lerInt("ID do funcionário: ");
        Funcionario funcionario = DAOFuncionario.readFuncionario(id);
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado!");
            return;
        }

        Historico historico = new Historico();
        historico.setId_unico(Arquivos.lerId("historico") + 1); // puxa automatico
        historico.setId_funcionario(funcionario.getId_unico());
        historico.setId_local(funcionario.getLocal() == null ? 0 : funcionario.getLocal().getId_unico());
        historico.setData(LocalDate.now().format(formatoData));
        historico.setHora(LocalTime.now().format(formatoHora));

        if (DAOHistorico.createHistorico(historico)) {
            Arquivos.incrementaId("historico");
            System.out.println("Ponto registrado: " + funcionario.getNome()
                    + " - " + historico.getData() + " " + historico.getHora());
        } else {
            System.out.println("Falha ao registrar o ponto!");
        }
    }

    private static void historicoDoFuncionario() {
        int id = lerInt("ID do funcionário: ");
        imprimeHistorico(DAOHistorico.readHistoricosPorFuncionario(id));
    }

    // o TXT guarda so ids; aqui buscamos o nome e o setor para mostrar de forma legivel
    private static void imprimeHistorico(List<Historico> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhum batimento encontrado.");
            return;
        }
        for (Historico h : lista) {
            Funcionario funcionario = DAOFuncionario.readFuncionario(h.getId_funcionario());
            String nome = (funcionario == null) ? "(funcionário removido)" : funcionario.getNome();

            String setor = "-";
            if (h.getId_local() != 0) {
                Local local = DAOLocal.readLocal(h.getId_local());
                setor = (local == null) ? "(local removido)" : local.getSetor();
            }
            System.out.println("#" + h.getId_unico() + " | " + nome + " (id " + h.getId_funcionario() + ")"
                    + " | Local: " + setor + " | " + h.getData() + " " + h.getHora());
        }
    }

    // ====================================================================== MENUS

    public static void menuPrincipal() {
        System.out.println("------------\n"
                + "1 - Funcionários\n"
                + "2 - Locais\n"
                + "3 - Batimento de ponto\n"
                + "4 - Resetar\n"
                + "5 - Sair");
    }

    public static void menuSecundario() {
        System.out.println("-------------\n"
                + "1 - Cadastrar\n"
                + "2 - Listar\n"
                + "3 - Buscar\n"
                + "4 - Atualizar\n"
                + "5 - Deletar\n"
                + "6 - Voltar");
    }

    public static void menuPonto() {
        System.out.println("-------------\n"
                + "1 - Bater ponto\n"
                + "2 - Ver histórico completo\n"
                + "3 - Ver histórico de um funcionário\n"
                + "4 - Voltar");
    }

    // ================================================================== UTILITARIOS

    private static void imprimeLista(List<?> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
            return;
        }
        for (Object o : lista) {
            System.out.println(o);
        }
    }

    // le uma linha inteira (evita o problema de nextInt() deixar o Enter no buffer)
    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return sc.nextLine().trim();
    }

    // repete a pergunta ate o usuario digitar um numero inteiro valido
    private static int lerInt(String mensagem) {
        while (true) {
            try {
                return Integer.parseInt(lerTexto(mensagem));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro!");
            }
        }
    }

    // repete a pergunta ate o usuario digitar uma data valida (dd/mm/aaaa)
    private static String lerData(String mensagem) {
        while (true) {
            try {
                return LocalDate.parse(lerTexto(mensagem), formatoData).format(formatoData);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida! Use o formato dd/mm/aaaa.");
            }
        }
    }

    private static boolean confirma(String mensagem) {
        return lerTexto(mensagem).equalsIgnoreCase("s");
    }
}
