abstract class Pessoa implements Registros {

    String nome;
    String cpf;

    Pessoa(String nome, String cpf){
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
    public void consultar(){}
}


