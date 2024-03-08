//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaAgendamento {

    private static final List<User> usuarios = new ArrayList<>(); //guardar usuários cadastrados
    private static String logado = null; //testar login

    //########## MÉTODO AGENDAR CONSULTA  ##########
    /*
     É preciso fazer um método que:

     1. Dada uma especialidade pedida pelo paciente, encontre um médico dessa especialidade.
     2. Encontrado este médico, busque em sua lista de atendimentos, o dia e horário disponível mais próximo
     (Dentro do horário comercial e que não haja choque de horários com outros atendimentos).
     3. Encontrado este dia e horário, marcar na agenda a consulta.
     */

    public static void agendarConsulta(){

    }

    //#################################################

        //########## MÉTODO CADASTRAR USUÁRIO ##########
    public static void adicionarUsuario() {

        Scanner novoUsuario = new Scanner(System.in);
        System.out.printf("\n%s------ CADASTRAMENTO DE USUÁRIO ------%s\n%n", Cores.YELLOW_BOLD_BRIGHT,Cores.RESET);
        System.out.printf("%sNome:%s ", Cores.YELLOW_BOLD_BRIGHT,Cores.RESET);
        String nome = novoUsuario.nextLine();

        String senha;
        boolean senhaValida = false;

        while (!senhaValida) {
            System.out.printf("""
            \n%sSua senha deve seguir os seguintes critérios:%s
            \s
            1. Conter %s8 dígitos%s, no mínimo;
            2. Conter letras %smaiúsculas%s e %sminúsculas%s;
            3. Conter, ao menos, um %snúmero%s;
            4. Conter, ao menos, um dos %scaracteres especiais%s: %s!@#$&*%s
            
            %sCrie sua senha:%s\s""", Cores.YELLOW_BOLD_BRIGHT, Cores.RESET,Cores.YELLOW_BOLD_BRIGHT, Cores.RESET,Cores.YELLOW_BOLD_BRIGHT, Cores.RESET,Cores.YELLOW_BOLD_BRIGHT, Cores.RESET,Cores.YELLOW_BOLD_BRIGHT, Cores.RESET,Cores.YELLOW_BOLD_BRIGHT, Cores.RESET,Cores.YELLOW_BOLD_BRIGHT, Cores.RESET,Cores.YELLOW_BOLD_BRIGHT, Cores.RESET);

            /*Console console = System.console();
            if (console == null) {
                System.out.println("%sConsole não disponível. Não é possível ocultar a senha.%s".formatted(Cores.RED_BOLD_BRIGHT,Cores.RESET));
                return;
            }
            char[] senhaChars = console.readPassword("%sCrie sua senha:%s ".formatted(Cores.YELLOW_BOLD_BRIGHT,Cores.RESET));
            senha = new String(senhaChars);             */
            senha = novoUsuario.nextLine();

            if (senha.length() >= 8 && senha.matches(".*[A-Z].*") && senha.matches(".*[a-z].*")
                    && senha.matches(".*\\d.*") && senha.matches(".*[!@#$&*].*")) {
                senhaValida = true;
            } else {
                System.out.printf("%sSenha inválida. Tente novamente.%s%n", Cores.RED_BOLD_BRIGHT,Cores.RESET);
                continue;
            }

            User u1 = new User(nome, senha);
            usuarios.add(u1);
            System.out.printf("%sUsuário cadastrado com sucesso!%s%n", Cores.GREEN_BOLD_BRIGHT,Cores.RESET);
            System.out.println("%s\nSeu código de acesso ao sistema é: %s".formatted(Cores.YELLOW_BOLD_BRIGHT,Cores.RESET) + Cores.GREEN_BOLD_BRIGHT + u1.getCodigo()+Cores.RESET +
                    Cores.YELLOW_BOLD_BRIGHT+"\nUse-o para fazer login"+Cores.RESET);

        }
    }

    //#############################################

    //########## MÉTODO LOGIN DO USUÁRIO ##########
    public static void login () {
        Scanner scanner = new Scanner(System.in);
        //boolean encontrado = false;

        System.out.printf("\n%s------ LOGIN ------%s\n%n", Cores.YELLOW_BOLD_BRIGHT,Cores.RESET);
        System.out.printf("%sCódigo:%s ", Cores.YELLOW_BOLD_BRIGHT,Cores.RESET);
        String cod = scanner.nextLine();
        System.out.printf("%sSenha:%s ", Cores.YELLOW_BOLD_BRIGHT,Cores.RESET);
        String sen = scanner.nextLine();

        for (User usuario : usuarios) {
            if (usuario.getCodigo().equals(cod) && usuario.getSenha().equals(sen)) {
                System.out.println(Cores.GREEN_BOLD_BRIGHT+"\nBem-vindo(a), "+usuario.getNome()+"!"+Cores.RESET);
                //encontrado = true;
                logado = usuario.getNome();
                // break;
            }
        }
    }
    //#############################################

    //########## MÉTODO PARA VALIDAR DOCUMENTOS NUMÉRICOS ##########
    public static String validaDocNum(String doc, List<? extends Registros> registrados) {
        Scanner sc = new Scanner(System.in);
        boolean docValido = false;

        int nDigitos;
        String numeroDoc = null;
        //numeroDoc

        while (!docValido) {
            boolean docOK = false;
            boolean docNovo = true;

            if (doc.equals("CPF")) {
                System.out.print(Cores.YELLOW_BOLD_BRIGHT+"     Digite o CPF: "+Cores.RESET);
                numeroDoc = sc.nextLine();
                nDigitos = 11;
            } else if (doc.equals("SUS")) {
                System.out.print(Cores.YELLOW_BOLD_BRIGHT+"     Digite o Nº do cartão do SUS do paciente: "+Cores.RESET);
                numeroDoc = sc.nextLine();
                nDigitos = 15;
            } else {
                System.out.print(Cores.YELLOW_BOLD_BRIGHT+"     Digite o CRM do médico: "+Cores.RESET);
                numeroDoc = sc.nextLine();
                nDigitos = 4;
            }

            if (numeroDoc.length() == nDigitos && numeroDoc.matches("[0-9]+")) {
                docOK = true;
            } else {
                System.out.println(Cores.RED_BOLD_BRIGHT+"Nº do "+doc+" inválido. O nº do "+doc+" deve conter " + nDigitos + " números.\nPor favor, tente novamente."+Cores.RESET);
                continue;
                //break;
            }

            if (!registrados.isEmpty()) {
                for (Registros registro : registrados) {
                    if ((registro instanceof Pessoa && ((Pessoa) registro).getCpf().equals(numeroDoc))||(registro instanceof Paciente && ((Paciente) registro).getSUS().equals(numeroDoc)) ||
                            (registro instanceof Medico && ((Medico) registro).getCrm().equals(numeroDoc))) {
                        System.out.println(Cores.RED_BOLD_BRIGHT+"Este " + doc + " já está cadastrado no sistema.\nPor favor, tente novamente."+Cores.RESET);
                        docNovo = false;
                        break;
                    }
                }
            }


            if (docNovo) {
                docValido = true;
            }
        }

        return numeroDoc;
    }
     //############################################


//########## MÉTODO CADASTRAR PESSOA ##########
        public static void cadastrarPessoa(Scanner scanner, int pessoa) {
        /* int pessoa: 0 - paciente;
                       1 - médico.
         */
            if (pessoa == 0) {

                System.out.print(Cores.YELLOW_BOLD_BRIGHT+"\n     Digite o nome do PACIENTE: "+Cores.RESET);
                String nomePaciente = scanner.nextLine();

                String nCPF = validaDocNum("CPF", Paciente.cadastrados);
                String nSUS = validaDocNum("SUS", Paciente.cadastrados);

                Paciente paciente = new Paciente(nomePaciente, nCPF,nSUS);
                Paciente.cadastrados.add(paciente);
                System.out.println(Cores.GREEN_BOLD_BRIGHT+"    >>>>Paciente "+ nomePaciente +" cadastrado(a) com sucesso<<<<\n"+Cores.RESET);


            } else if (pessoa == 1) {
                System.out.print(Cores.YELLOW_BOLD_BRIGHT+"\n     Digite o nome do MÉDICO: "+Cores.RESET);
                String nomeMedico = scanner.nextLine();


                String nCPF = validaDocNum("CPF", Medico.cadastrados);
                String nCRM = validaDocNum("CRM", Medico.cadastrados);

                System.out.print(Cores.YELLOW_BOLD_BRIGHT+"     Salário: "+Cores.RESET);
                double salario = scanner.nextDouble();
                scanner.nextLine();
                System.out.print(Cores.YELLOW_BOLD_BRIGHT+"     Especialidade: "+Cores.RESET);
                String especialidade = scanner.nextLine();

                Medico medico = new Medico(nomeMedico, nCPF, nCRM, salario, especialidade);
                Medico.cadastrados.add(medico);
                System.out.println(Cores.GREEN_BOLD_BRIGHT + "     >>>>Dr(a). "+nomeMedico+" cadastrado(a) com sucesso<<<<\n" + Cores.RESET);

            }
            //SistemaAgendamento.visualizarConsultas();
        }

    //#########################################################
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SistemaAgendamento agendamento = new SistemaAgendamento();

        Cores cor = new Cores();

                //Scanner sc = new Scanner(System.in);
        String s1;
        int opcao;
        int n2;
        boolean continuar = true;
        boolean mensagemErro = true;
        do {
            //boolean cadastrado = false;
            s1 = """
                    \n1 - %sCADASTRAR novo usuário%s
                    2 - %sFazer LOGIN%s
                    3 - %sSAIR da aplicação%s
                    
                    %sEscolha uma opção:%s\s""".formatted(Cores.YELLOW_BOLD_BRIGHT, Cores.RESET,Cores.YELLOW_BOLD_BRIGHT, Cores.RESET, Cores.YELLOW_BOLD_BRIGHT, Cores.RESET, Cores.YELLOW_BOLD_BRIGHT, Cores.RESET);

            System.out.println
                    (Cores.YELLOW_BOLD_BRIGHT + "\n\n     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n" +
                            "     |         :::::::::::SISTEMA DE AGENDAMENTO MÉDICO::::::::::          |\n" +
                            "     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+" + Cores.RESET);

            System.out.println(s1);
            n2 = scanner.nextInt();
            scanner.nextLine();

            switch (n2) {
                case 1:
                    adicionarUsuario();
                    break;

                case 2:

                    login();
                    if (logado != null) {
                        //System.out.println("\nBem-vindo, " + logado + "!\n");
                        String s2;
                        do {
                            s2 = """
                                    \n%sO que deseja fazer?%s
                                    
                                    1 - %sCADASTRAR médicos%s
                                    2 - %sCADASTRAR pacientes%s
                                    3 - %sAGENDAR atendimentos%s
                                    4 - %sCONSULTAR médicos%s \s
                                    5 - %sCONSULTAR pacientes%s
                                    6 - %sCONSULTAR atendimentos agendados%s
                                    7 - %sLOGOUT%s
                                    """.formatted(Cores.YELLOW_BOLD_BRIGHT, Cores.RESET, Cores.YELLOW_BOLD_BRIGHT, Cores.RESET, Cores.YELLOW_BOLD_BRIGHT, Cores.RESET, Cores.YELLOW_BOLD_BRIGHT, Cores.RESET, Cores.YELLOW_BOLD_BRIGHT, Cores.RESET, Cores.YELLOW_BOLD_BRIGHT, Cores.RESET, Cores.YELLOW_BOLD_BRIGHT, Cores.RESET, Cores.YELLOW_BOLD_BRIGHT, Cores.RESET);

                            System.out.println
                                    (Cores.YELLOW_BOLD_BRIGHT + "\n\n     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n" +
                                            "     |         :::::::::::SISTEMA DE AGENDAMENTO MÉDICO::::::::::          |\n" +
                                            "     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+" + Cores.RESET);
                            System.out.println(s2);
                            opcao = scanner.nextInt();
                            scanner.nextLine();

                            switch (opcao) {
                                case 1:
                                    while (true) {
                                        cadastrarPessoa(scanner, 1);
                                        System.out.print("Deseja adicionar outro médico ao sistema? (s/n): ");
                                        String resposta = scanner.next();
                                        if (!resposta.equalsIgnoreCase("s")) {
                                            break;
                                        }

                                        scanner.nextLine();
                                    }
                                    break;

                                case 2:
                                    while (true) {
                                        cadastrarPessoa(scanner, 0);
                                        System.out.print("Deseja adicionar outro paciente ao sistema? (s/n): ");
                                        String resposta = scanner.next();
                                        if (!resposta.equalsIgnoreCase("s")) {
                                            break;
                                        }

                                        scanner.nextLine();
                                    }
                                    break;
                                case 3:
                                    System.out.println("Método de agendar consultas");
                                    //agendarConsulta();

                                    break;

                                case 4:
                                    System.out.println("Consultar médicos da clínica");

                                    break;

                                case 5:
                                    System.out.println("Consultar pacientes cadastrados");
                                    break;

                                case 6:
                                    System.out.println("Método de consultar consultas marcadas");
                                    break;

                                case 7:
                                    System.out.println(Cores.GREEN_BOLD_BRIGHT + "\nBom descanço, " + logado + "!\nAté amanhã! :)\n" + Cores.RESET);
                                    mensagemErro = false;
                                    break;
                                default:
                                    System.out.printf("%sOpção inválida. Por favor, digite outro número.%s%n", Cores.RED_BOLD_BRIGHT, Cores.RESET);

                            }
                        } while (opcao != 7);
                    }
                    if(mensagemErro) {
                        System.out.printf("""
                                %sUsuário não cadastrado no sistema ou código/senha incorretos.
                                Cadastre-se ou tente novamente.%s%n""", Cores.RED_BOLD_BRIGHT, Cores.RESET);
                    }
                    break;

                case 3:
                    System.out.printf("%sAté mais! :)%s%n", Cores.GREEN_BOLD_BRIGHT, Cores.RESET);
                    continuar = false;
                    break;

                default:
                    System.out.printf("%sOpção inválida. Por favor, digite outro número.%S%n", Cores.RED_BOLD_BRIGHT, Cores.RESET);
                    break;

            }
        } while (continuar);
    }

   // private static void visualizarConsultas() {
    }



