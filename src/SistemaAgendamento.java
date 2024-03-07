import java.time.LocalDateTime;
import java.util.Scanner;


public class SistemaAgendamento {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SistemaAgendamento agendamento = new SistemaAgendamento();
        boolean continuar = true;

        Cores cor = new Cores();


        System.out.println
                ("\n\n     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n" +
                        "     |         :::::::::::SISTEMA DE AGENDAMENTO MÉDICO::::::::::          |\n" +
                        "     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");


        while (continuar) {
            System.out.println("\n     1." + Cores.YELLOW_BOLD_BRIGHT + " CADASTRAR" + Cores.RESET + " PACIENTE");
            System.out.println("     2." + Cores.YELLOW_BOLD_BRIGHT + " CADASTRAR" + Cores.RESET + " MÉDICO");
            System.out.println("     3." + Cores.YELLOW_BOLD_BRIGHT + " AGENDAR" + Cores.RESET + " CONSULTA");
            System.out.print(Cores.YELLOW_BOLD + "\n     Escolha uma das opção: " + Cores.RESET);
            int opcao = scanner.nextInt();


            switch (opcao) {

                //########## PARTE DO CADASTRO DO PACIENTE ##########
                case 1:
                System.out.print("\n     Digite o nome do PACIENTE: ");
                String nomePaciente = scanner.nextLine();
                scanner.nextLine();
                System.out.print("     Digite o CPF do paciente: ");
                String telefonePaciente = scanner.nextLine();
                Paciente paciente = new Paciente(nomePaciente, telefonePaciente);
                System.out.println("\n     >>>>PACIENTE CADASTRADO COM SUCESSO<<<<");
                SistemaAgendamento.visualizarConsultas();
                break;
            }
                //##################################################


                //########## PARTE DO CADASTRO DO MÉDICO ##########
            switch (opcao) {
                case 2:
                System.out.print("\n     Digite o nome do MÉDICO: ");
                String nomeMedico = scanner.nextLine();
                scanner.nextLine();
                System.out.print("     Digite o CPF do MÉDICO: ");
                String cpfMedico = scanner.nextLine();
                System.out.print("     Digite o CRM do MÉDICO: ");
                String crmMedico = scanner.nextLine();
                System.out.print("     Digite o SALÁRIO do MÉDICO: ");
                boolean salarioMedico = Boolean.parseBoolean(scanner.nextLine());
                System.out.print("     Digite a ESPECIALIDADE do MÉDICO: ");
                String especialidadeMedico = scanner.nextLine();
                Medico medico = new Medico(nomeMedico, cpfMedico, crmMedico, salarioMedico, especialidadeMedico);
//                System.out.println("Digite a data e hora da consulta no formato YYYY-MM-DD HH:MM:");
//                String dataHoraConsulta = scanner.nextLine();
//                LocalDateTime dataHora = LocalDateTime.parse(dataHoraConsulta);
//                agendamento.agendarConsulta(paciente, medico, dataHora);

                System.out.println("\n     >>>>MÉDICO CADASTRADO REALIZADO COM SUCESSO<<<<");
                SistemaAgendamento.visualizarConsultas();
                break;
                }
            }
        }

    private static void visualizarConsultas() {
    }
}
