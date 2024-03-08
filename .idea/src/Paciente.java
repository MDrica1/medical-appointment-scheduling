import java.util.ArrayList;
import java.util.List;

public class Paciente extends Pessoa{
    public static List<Paciente> cadastrados = new ArrayList<>();
    public static List<? extends Registros> Atendimentos = new ArrayList<>();

    private String SUS;

    public Paciente(String nome, String cpf, String SUS) {
        super(nome,cpf);
        this.SUS = SUS;
    }

    @Override
    public void consultar() {
        //Fazer método consultar pacientes cadastrados


    }

    @Override
    public String toString() {
        return "Paciente{" +
                "Nome='" + nome + '\n' +
                ", CPF ='" + cpf + '\n' +
                ", Nº do cartão do SUS ='" + SUS + '\n' +
                '}';
    }

    @Override
    public String getCpf() {
        return super.getCpf();
    }

    @Override
    public String getNome() {
        return super.getNome();
    }

    public String getSUS() {
        return SUS;
    }
}
