package br.com.vinicius.biblioteca.service;

import br.com.vinicius.biblioteca.model.Livro;

import java.util.List;

public interface LivroService {
    void cadastrarLivro(Livro livro);
    Livro buscarPorIsbn(String isbn);
    List<Livro> listarTodos();
    void removerLivro(String isbn);
    void alterarTitulo(String isbn, String novoTitulo);
    void alterarAutor(String isbn, String novoAutor);
    void alterarAnoPublicacao(String isbn, int novoAno);
    void devolver(String isbn);
    void emprestar(String isbn);
}
