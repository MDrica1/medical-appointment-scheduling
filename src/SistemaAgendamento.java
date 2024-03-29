//import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SistemaAgendamento {

    private static List<User> usuarios = new ArrayList<>(); //guardar usuários cadastrados
    private static final List<Consulta> consultas = new ArrayList<>(); // consultas agendadas
    private static String logado = null; //testar login

        //########## MÉTODO AGENDAR CONSULTA  ##########
    /*
     É preciso fazer um método que:

     1. Dada uma especialidade pedida pelo paciente, encontre um médico dessa especialidade.
     2. Encontrado este médico, busque em sua lista de atendimentos, o dia e horário disponível mais próximo
     (Dentro do horário comercial e que não haja choque de horários com outros atendimentos).
     3. Encontrado este dia e horário, marcar na agenda a consulta.
     */
        public static void agendarConsulta() {
            Scanner scanner = new Scanner(System.in);

            System.out.println(Cores.YELLOW_BOLD_BRIGHT+"    Digite a especialidade médica desejada:"+Cores.RESET);
            String especialidade = scanner.nextLine();
            Boolean p = false;
            String nomeP = null;
            System.out.println(Cores.YELLOW_BOLD_BRIGHT + "    CPF do paciente:" + Cores.RESET);
            String paciente = scanner.nextLine();
                
            for (Paciente pac : Paciente.cadastrados) {
                if (pac.getCpf().equals(paciente)) {
                    p = true;
                    nomeP = pac.getNome();
                    }
                }
            if (!p){
                System.out.println(Cores.RED_BOLD_BRIGHT+"Paciente não encontrado. \nCadastre-o ou tente novamente usando um CPF cadastrado"+Cores.RESET);
                return;
            }


            // Encontrar médicos com a especialidade desejada
            List<Medico> medicosDisponiveis = new ArrayList<>();
            for (Medico medico : Medico.cadastrados) {
                if (medico.getEspecialidade().equalsIgnoreCase(especialidade)) {
                    medicosDisponiveis.add(medico);
                }
            }

            if (medicosDisponiveis.isEmpty()) {
                System.out.println(Cores.RED_BOLD_BRIGHT+"Nenhum médico com a especialidade '" + especialidade + "' foi encontrado."+Cores.RESET);
                return;
            }

            // Mostrar médicos disponíveis e permitir ao usuário escolher um médico
            System.out.println(Cores.YELLOW_BOLD_BRIGHT+"Médicos disponíveis para a especialidade '" + especialidade + "':"+Cores.RESET);
            for (int i = 0; i < medicosDisponiveis.size(); i++) {
                Medico medico = medicosDisponiveis.get(i);
                System.out.println((i + 1) + ". " +Cores.YELLOW_BOLD_BRIGHT+ medico.getNome()+Cores.RESET);
            }
            System.out.println(Cores.YELLOW_BOLD_BRIGHT+"Selecione o médico digitando o número correspondente:"+Cores.RESET);

            int escolha = scanner.nextInt();
            if (escolha < 1 || escolha > medicosDisponiveis.size()) {
                System.out.println(Cores.RED_BOLD_BRIGHT+"Escolha inválida."+Cores.RESET);
                return;
            }

            // Escolher o médico selecionado
            Medico medicoSelecionado = medicosDisponiveis.get(escolha - 1);

            // Solicitar ao usuário o horário desejado para a consulta
            scanner.nextLine();
            System.out.println(Cores.YELLOW_BOLD_BRIGHT+"Digite a data e horário desejado para a consulta (formato: yyyy-MM-dd HH:mm):"+Cores.RESET);
            String horarioDesejadoStr = scanner.nextLine();
            LocalDateTime horarioDesejado = LocalDateTime.parse(horarioDesejadoStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            // Verificar se o horário está disponível
            if (medicoSelecionado.getAgendamentos().contains(horarioDesejado)) {
                System.out.println(Cores.RED_BOLD_BRIGHT+"O horário desejado não está disponível."+Cores.RESET);
                return;
            }

            // Agendar a consulta
            medicoSelecionado.getAgendamentos().add(horarioDesejado);
            Consulta consulta = new Consulta(nomeP,medicoSelecionado.getNome(),horarioDesejado,especialidade);
            consultas.add(consulta);
            System.out.println(Cores.GREEN_BOLD_BRIGHT+"Consulta agendada com sucesso para Sr(a). "+nomeP+" com Dr(a). " + medicoSelecionado.getNome() +" no horário: " + horarioDesejado +Cores.RESET);
        }


    //#################################################

    //######### MÉTODO PARA VISUALIZAR CONSULTAS AGENDADAS
    public static void visualizaConsulta(){
        Scanner scanner = new Scanner(System.in);

        System.out.println(Cores.YELLOW_BOLD_BRIGHT + "Selecione o médico digitando o número correspondente:" + Cores.RESET);
        for (int i = 0; i < Medico.cadastrados.size(); i++) {
            Medico medico = Medico.cadastrados.get(i);
            System.out.println((i + 1) + ". " + medico.getNome());
        }

        int escolha = scanner.nextInt();
        if (escolha < 1 || escolha > Medico.cadastrados.size()) {
            System.out.println(Cores.RED_BOLD_BRIGHT + "Escolha inválida." + Cores.RESET);
            return;
        }

        Medico medicoSelecionado = Medico.cadastrados.get(escolha - 1);
        boolean consultaEncontrada = false;

        for (Consulta consulta : consultas) {
            if (consulta.getMedico().equals(medicoSelecionado.getNome())) {
                consultaEncontrada = true;
                System.out.println(Cores.YELLOW_BOLD_BRIGHT + "\nPaciente: " + Cores.RESET + consulta.getPaciente() + Cores.YELLOW_BOLD_BRIGHT + "\nMédico: "+ Cores.RESET +"Dr(a)." + consulta.getMedico() + Cores.YELLOW_BOLD_BRIGHT + "\nData/Horário: " + Cores.RESET + consulta.getDataHora() + Cores.YELLOW_BOLD_BRIGHT + "\nEspecialidade: " + Cores.RESET + consulta.getEspecialidade());
            }
        }

        if (!consultaEncontrada) {
            System.out.println(Cores.RED_BOLD_BRIGHT + "Não há consultas agendadas para este médico." + Cores.RESET);
        }

    }

        //########## MÉTODO CADASTRAR USUÁRIO ##########
    public static void adicionarUsuario() {

        Scanner novoUsuario = new Scanner(System.in);
        System.out.printf("\n%s------ CADASTRAMENTO DE USUÁRIO ------%s\n%n", Cores.YELLOW_BOLD_BRIGHT,Cores.RESET);
        System.out.printf("     %sNome:%s ", Cores.YELLOW_BOLD_BRIGHT,Cores.RESET);
        String nome = novoUsuario.nextLine();
        String ncpf = validaDocNum("CPF",usuarios);

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
            
            %s      Crie sua senha:%s\s""", Cores.YELLOW_BOLD_BRIGHT, Cores.RESET,Cores.YELLOW_BOLD_BRIGHT, Cores.RESET,Cores.YELLOW_BOLD_BRIGHT, Cores.RESET,Cores.YELLOW_BOLD_BRIGHT, Cores.RESET,Cores.YELLOW_BOLD_BRIGHT, Cores.RESET,Cores.YELLOW_BOLD_BRIGHT, Cores.RESET,Cores.YELLOW_BOLD_BRIGHT, Cores.RESET,Cores.YELLOW_BOLD_BRIGHT, Cores.RESET);

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

            User u1 = new User(nome, ncpf, senha);
            usuarios.add(u1);
            System.out.printf("\n      >>> " + Cores.GREEN_BOLD_BRIGHT + "%sUsuário cadastrado com sucesso!" + Cores.RESET + " <<<%s%n", Cores.GREEN_BOLD_BRIGHT,Cores.RESET);
            System.out.println("%s\n      Seu login no sistema é: %s".formatted(Cores.YELLOW_BOLD_BRIGHT,Cores.RESET) + Cores.GREEN_BOLD_BRIGHT + u1.getCodigo()+Cores.RESET
                    /*Cores.YELLOW_BOLD_BRIGHT+"\nUse-o para fazer login"+Cores.RESET*/);

        }
    }

    //#############################################

    //########## MÉTODO LOGIN DO USUÁRIO ##########
    public static void login () {
        Scanner scanner = new Scanner(System.in);
        //boolean encontrado = false;

        System.out.printf("\n%s------ LOGIN ------%s\n%n", Cores.YELLOW_BOLD_BRIGHT,Cores.RESET);
        System.out.printf("     %sLogin:%s ", Cores.YELLOW_BOLD_BRIGHT,Cores.RESET);
        String cod = scanner.nextLine();
        System.out.printf("     %sSenha:%s ", Cores.YELLOW_BOLD_BRIGHT,Cores.RESET);
        String sen = scanner.nextLine();

        for (User usuario : usuarios) {
            if (usuario.getCodigo().equals(cod) && usuario.getSenha().equals(sen)) {
                System.out.println(Cores.GREEN_BOLD_BRIGHT+"\n     Bem-vindo(a), "+usuario.getNome()+"!"+Cores.RESET);
                //encontrado = true;
                logado = usuario.getNome();
                // break;
            }
        }
    }
    //#############################################

    //########## MÉTODO PARA VALIDAR DOCUMENTOS NUMÉRICOS ##########
    /*

    */
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
                System.out.println(Cores.GREEN_BOLD_BRIGHT+"\n     >>>>Paciente "+ nomePaciente +" cadastrado(a) com sucesso<<<<\n"+Cores.RESET);


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
                System.out.println(Cores.GREEN_BOLD_BRIGHT + "\n     >>>>Dr(a). "+nomeMedico+" cadastrado(a) com sucesso<<<<\n" + Cores.RESET);

            }
            //SistemaAgendamento.visualizarConsultas();
        }

    //#########################################################

    //########## MÉTODO PARA VISUALIZAR MÉDICOS ###########
   /* for (Medico medico : Medico.cadastrados) {
        System.out.println(medico.toString());
    }*/
    public static void consultarMedico(){
        Set<String> especialidades = new HashSet<>();
        for (Medico medico : Medico.cadastrados) {
            especialidades.add(medico.getEspecialidade());
        }


        for (String especialidade : especialidades) {

            int cont = 0;
            ArrayList<Medico> m = new ArrayList<>();

            for (Medico medico : Medico.cadastrados) {
                if (medico.getEspecialidade().equals(especialidade)) {
                    m.add(medico);
                    cont += 1;
                }

            }

            if (cont > 0) {
                System.out.println(Cores.YELLOW_BOLD_BRIGHT+"Especialidade: " +Cores.RESET+ especialidade +Cores.YELLOW_BOLD_BRIGHT + "\nNº de médicos: " +Cores.RESET+ cont + "\n");
                for (Medico medico : m) {
                    System.out.println("Nome: " + medico.nome + "\nCPF: " + medico.cpf + "\nCRM/AL: " + medico.getCrm() +"\nSalario: R$ " + medico.getSalario() + "\n");
                }
            }

        }
    }



    //#####################################################
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
                                    4 - %sVISUALIZAR médicos%s \s
                                    5 - %sVISUALIZAR pacientes%s
                                    6 - %sVISUALIZAR atendimentos agendados%s
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
                                    //System.out.println("Método de agendar consultas");
                                    agendarConsulta();

                                    break;

                                case 4:
                                    //System.out.println("Consultar médicos da clínica");
                                    if (Medico.cadastrados.isEmpty()) {
                                        System.out.println(Cores.RED_BOLD_BRIGHT+"Não há médicos cadastrados no sistema."+Cores.RESET);
                                        break;
                                    }
                                    consultarMedico();

                                break;

                                case 5:
                                    if (Paciente.cadastrados.isEmpty()) {
                                        System.out.println(Cores.RED_BOLD_BRIGHT+"Não há pacientes cadastrados no sistema."+Cores.RESET);
                                        break;
                                    }
                                    System.out.println(Cores.YELLOW_BOLD_BRIGHT+"\nLista de Pacientes:\n"+Cores.RESET);
                                    for (Paciente paciente : Paciente.cadastrados){
                                        paciente.consultar();
                                    }

                                    break;

                                case 6:
                                    //System.out.println("Método de consultar consultas marcadas");
                                    visualizaConsulta();
                                    break;

                                case 7:
                                    System.out.println(Cores.GREEN_BOLD_BRIGHT + "\nBom descanso, " + logado + "!\nAté amanhã! :)\n" + Cores.RESET);
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



