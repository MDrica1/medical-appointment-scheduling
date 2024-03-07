public class Medico {
    private String nome;
    private String cpf;
    private  String crm;
    private boolean salario;
    private String especialidade;


    public Medico(String nome, String cpf, String crm, boolean salario, String especialidade) {
        this.nome = nome;
        this.cpf = cpf;
        this.crm = crm;
        this.salario = salario;
        this.especialidade = especialidade;

    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCrm() {
        return crm;
    }

    public boolean getSalario() {
        return salario;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "NOME: " + nome + '\n' +
                "CPF: " + nome + '\n' +
                "CRM: " + nome + '\n' +
                "SALARIO: " + nome + '\n' +
                "ESPECIALIDADE: " + nome + '\n';
                }
    }
