//import java.text.DecimalFormat;
//mport java.util.Scanner;

public class User{
    private static int proximoCodigo = 1;
    private final String nome;
    private final String senha;
    private final String codigo;

    // Construtor
    public User(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
        this.codigo = "2024"+proximoCodigo;
        proximoCodigo++;
    }
    /*public void cadastrar() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nome: ");
        nome = scanner.nextLine();
        System.out.println("Senha: ");
        senha = scanner.nextLine();
        scanner.close();

        System.out.println("Usuário cadastrado com sucesso!");
        System.out.println("Seu código de acesso ao sistema é: " + this.codigo); // Usa corretamente a variável codigo
    public boolean login(String codigo, String senha) {
        return this.codigo.equals(codigo) && this.senha.equals(senha); // Compara o código formatado
    }

    }*/



    public String getCodigo() {
        return codigo;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }
}
