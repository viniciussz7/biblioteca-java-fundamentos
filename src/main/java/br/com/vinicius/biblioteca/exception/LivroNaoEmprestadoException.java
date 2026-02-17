package br.com.vinicius.biblioteca.exception;

public class LivroNaoEmprestadoException extends RuntimeException {

    public LivroNaoEmprestadoException(String mensagem) {
        super(mensagem);
    }
}
