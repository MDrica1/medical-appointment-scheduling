import java.util.ArrayList;
import java.util.List;

public class Medico extends Pessoa{
    private final String crm;
    private final double salario;
    private final String especialidade;


    public static List<? extends Registros> Atendimentos = new ArrayList<>();
    public static List<Medico> cadastrados = new ArrayList<>();
    public static ArrayList<String> especialidades = new ArrayList<>();

    public Medico(String nome, String cpf, String crm, double salario, String especialidade) {
        super(nome, cpf);
        this.crm = crm;
        this.salario = salario;
        this.especialidade = especialidade;
    }



    @Override
    public String getNome() {
        return super.getNome();
    }

    @Override
    public String getCpf() {
        return super.getCpf();
    }


    public String getCrm() {
        return crm;
    }

    public double getSalario() {
        return salario;
    }

    public String getEspecialidade() {
        return especialidade;
    }


        //Fazer método consultar médicos cadastrados
        // Ter opções 1. Todas as especialidades
        //            2. Filtrar por especialidades



    @Override
    public String toString() {
        return "Médico{" +
                "Nome: " + nome + '\n' +
                "CPF: " + cpf + '\n' +
                "CRM: " + crm + '\n' +
                "Salário: " + salario + '\n' +
                "Especialidade: " + especialidade + '\n';
                }


    }
