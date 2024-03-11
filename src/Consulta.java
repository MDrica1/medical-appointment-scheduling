import java.time.LocalDateTime;

public class Consulta implements Registros{
    private String paciente;
    private String medico;
    private LocalDateTime dataHora;
    private String especialidade;

    public Consulta(String paciente, String medico, LocalDateTime dataHora, String especialidade) {
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.especialidade = especialidade;
    }



    public String getPaciente() {
        return paciente;
    }

    public String getMedico() {
        return medico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    //@Override
    //public void consultar() {
        //Fazer método CONSULTAR atendimentos agendados
    //}

    @Override
    public String toString() {
        return "Consulta{" +
                "Paciente=" + paciente +
                ", Médico=" + medico +
                ", Dia/horário= " + dataHora +
                ", Especialidade= " + especialidade +
                '}';
    }

    //@Override
    //public void consultar() {}
}
