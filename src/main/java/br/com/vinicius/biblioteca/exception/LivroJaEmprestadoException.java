package br.com.vinicius.biblioteca.exception;

public class LivroJaEmprestadoException extends RuntimeException{

    public LivroJaEmprestadoException(String mensagem) {
        super(mensagem);
    }
}
